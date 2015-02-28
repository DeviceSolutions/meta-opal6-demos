#include "../include/libemqtt.h"

#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <linux/tcp.h>	/* For Linux applications */
#include <netdb.h>
#include <signal.h>
#include <pthread.h>
#include <stdlib.h>
#include <fcntl.h>

#include <gtk/gtk.h>

// values below can be found by logging into the thingfabric.com portal (http://app.thingfabric.com)
// Username:  your email address you used to create the account
// Password:  MD5 hash of your password (32 character string)
// Domain:  Randomly generated.  Can be found on Accounts tab in portal.
// Device Type:  Can remain "things".  If this device is a commonly supported
//               device on the thingfabric.com portal there may be a type to choose.
//               For example, Arduino.
// Device ID: A string uniquely identifying this device from your other
//            devices.  Only needs to be unique to you, not across all users.
//            Common examples include device's MAC address or serial number.
//            Device-1, Device-2 are fine too.

#define  THINGFABRIC_USERNAME   	"6c10ce55-115c-44af-b9c7-59b5fa995ab6"
#define  THINGFABRIC_PASSWORD   	"f0a64b776c595c247c1f55e495641906"	/* pre-hashed (MD5) token */
#define  THINGFABRIC_DOMAIN     	"yefsec0rk7uhatp"			/* domain */
#define  THINGFABRIC_DEVICE_TYPE	"boards"		/* topic */
#define  THINGFABRIC_DEVICE_ID  	"opal6"		/* device */
#define  THINGFABRIC_BROKER_HOSTNAME "q.thingfabric.com"
#define	 THINGFABRIC_BROKER_PORT	1883	/* Use port 8883 for SSL */

#define RCVBUFSIZE 1024
uint8_t packet_buffer[RCVBUFSIZE];

int socket_id;
char broker_ip[100];  // holds return ip address from DNS lookup
mqtt_broker_handle_t broker;
char pubTopic[100];
char pubMsgStr[250];

volatile int serverConnected;
volatile int temp = 25;
int keepalive = 30;

GtkWidget *tempLabel;

// json helper routines
void initJsonMsg(char *buffer)
{
    sprintf(buffer, "%s", "{");
}

void finishJsonMsg(char *buffer)
{
    sprintf(buffer+strlen(buffer), "%s", "}");
}

void addStringValToMsg(const char *n, const char *v, char *buffer)
{
    if (strlen(buffer) > 1)
    {
        sprintf(buffer+strlen(buffer), ",");
    }
    sprintf(buffer+strlen(buffer), "\"%s\":", n);
    sprintf(buffer+strlen(buffer), "\"%s\"", v);
}

void addIntValToMsg(const char *n, const long l, char *buffer)
{
    if (strlen(buffer) > 1)
    {
        sprintf(buffer+strlen(buffer), ",");
    }
    sprintf(buffer+strlen(buffer), "\"%s\":", n);
    sprintf(buffer+strlen(buffer), "%ld", l);
}

void addDoubleValToMsg(const char *n, const double d, char *buffer)
{
    if (strlen(buffer) > 1)
    {
        sprintf(buffer+strlen(buffer), ",");
    }
    sprintf(buffer+strlen(buffer), "\"%s\":", n);
    sprintf(buffer+strlen(buffer), "%g", d);
}

int send_packet(void* socket_info, const void* buf, unsigned int count)
{
    int fd = *((int*)socket_info);
    return send(fd, buf, count, 0);
}

int init_socket(mqtt_broker_handle_t* broker, const char* hostname, short port)
{
    int flag = 1;

    // Create the socket
    if((socket_id = socket(PF_INET, SOCK_STREAM, 0)) < 0)
        return -1;

    // Disable Nagle Algorithm
    if (setsockopt(socket_id, IPPROTO_TCP, TCP_NODELAY, (char*)&flag, sizeof(flag)) < 0)
        return -2;

    struct sockaddr_in socket_address;
    // Create the stuff we need to connect
    socket_address.sin_family = AF_INET;
    socket_address.sin_port = htons(port);
    socket_address.sin_addr.s_addr = inet_addr(hostname);

    // Connect the socket
    if((connect(socket_id, (struct sockaddr*)&socket_address, sizeof(socket_address))) < 0)
        return -1;

    // MQTT stuffs
    mqtt_set_alive(broker, keepalive);
    broker->socket_info = (void*)&socket_id;
    broker->send = send_packet;

    return 0;
}

int close_socket(mqtt_broker_handle_t* broker)
{
    int fd = *((int*)broker->socket_info);
    return close(fd);
}

int read_packet(int timeout)
{
    if(timeout > 0)
    {
        fd_set readfds;
        struct timeval tmv;

        // Initialize the file descriptor set
        FD_ZERO (&readfds);
        FD_SET (socket_id, &readfds);

        // Initialize the timeout data structure
        tmv.tv_sec = timeout;
        tmv.tv_usec = 0;

        // select returns 0 if timeout, 1 if input available, -1 if error
        if(select(1, &readfds, NULL, NULL, &tmv))
            return -2;
    }

    int total_bytes = 0, bytes_rcvd, packet_length;
    memset(packet_buffer, 0, sizeof(packet_buffer));

    // Reading fixed header
    while(total_bytes < 2)
    {
        if((bytes_rcvd = recv(socket_id, (packet_buffer+total_bytes), RCVBUFSIZE - total_bytes, 0)) <= 0)
            return -1;
        total_bytes += bytes_rcvd; // Keep tally of total bytes
    }

    packet_length = packet_buffer[1] + 2; // Remaining length + fixed header length

    // Reading the packet
    while(total_bytes < packet_length)
    {
        if((bytes_rcvd = recv(socket_id, (packet_buffer+total_bytes), RCVBUFSIZE - total_bytes, 0)) <= 0)
            return -1;
        total_bytes += bytes_rcvd; // Keep tally of total bytes
    }

    return packet_length;
}

int hostname_to_ip(char* hostname , char* ip)
{
    struct hostent *he;
    struct in_addr **addr_list;
    int i;

    if ( (he = gethostbyname( hostname ) ) == NULL)
    {
        // get the host info
        herror("gethostbyname");
        return 1;
    }

    addr_list = (struct in_addr **) he->h_addr_list;

    for(i = 0; addr_list[i] != NULL; i++)
    {
        //Return the first one;
        strcpy(ip , inet_ntoa(*addr_list[i]) );
        return 0;
    }

    return 1;
}

void led(char *led, int state)
{
    int tmp;
    char path[64];

    sprintf(path, "/sys/class/leds/user-led-%s/brightness", led);

    tmp = open(path, O_RDWR);

    if (state == 0)
    {
        write(tmp, "0", 2);
    }
    else
    {
        write(tmp, "1", 2);
    }

    close(tmp);
}

void alive(int sig)
{
	printf("Timeout! Sending ping...\n");
	mqtt_ping(&broker);

	alarm(keepalive);
}

int server_connect(int doConnect)
{
    int16_t packet_length;
    char host[30];  // temp space for hostname string

    serverConnected = 0;

    if (doConnect)
    {
        sprintf(host, THINGFABRIC_BROKER_HOSTNAME);
        char *hostname = host;
        hostname_to_ip(hostname , broker_ip);

        if ((broker_ip == NULL) || (broker_ip[0] == 0))
        {
            return 0;
        }

        printf("\n%s resolved to %s\n" , hostname , broker_ip);

        sleep(5);

        // now connect using user/password, publish sensor values on
        // appropriate topic (<domain>/<device type>/<device id>
        char clientIDStr[100];
        sprintf(clientIDStr, "%s/%s", THINGFABRIC_DEVICE_TYPE, THINGFABRIC_DEVICE_ID);
        mqtt_init(&broker, clientIDStr);
        mqtt_init_auth(&broker, THINGFABRIC_USERNAME, THINGFABRIC_PASSWORD);
        init_socket(&broker, broker_ip, THINGFABRIC_BROKER_PORT);

        serverConnected = 1;

        mqtt_connect(&broker);
        // wait for CONNACK
        packet_length = read_packet(1);
        if(packet_length < 0)
        {
            fprintf(stderr, "Error(%d) on read packet!\n", packet_length);
            serverConnected = 0;
        }

        if(MQTTParseMessageType(packet_buffer) != MQTT_MSG_CONNACK)
        {
            fprintf(stderr, "CONNACK expected!\n");
            serverConnected = 0;
        }

        if(packet_buffer[3] != 0x00)
        {
            fprintf(stderr, "CONNACK failed!\n");
            serverConnected = 0;
        }

        if (serverConnected)
        {
            sprintf(pubTopic, "%s/%s/%s", THINGFABRIC_DOMAIN, THINGFABRIC_DEVICE_TYPE, THINGFABRIC_DEVICE_ID);
            printf("%s\n", pubTopic);

            // configure the ping timer
            signal(SIGALRM, alive);
            alarm(keepalive);
        }
        else
        {
            fprintf(stderr, "Error connecting to MQTT server\n");
        }
    }
    else
    {
        printf("Disconnecting from server\n");
        mqtt_disconnect(&broker);
        close_socket(&broker);
        serverConnected = 0;
    }

    return serverConnected;
}

void closeApp(GtkWidget *window, gpointer data)
{
    printf("Destroy\n");
    mqtt_disconnect(&broker);
	close_socket(&broker);
    gtk_main_quit();
}

void publish_temp(int temp)
{
    int retCode = 0;

    if (serverConnected == 0)
    {
        server_connect(1);
    }

    // construct message
    initJsonMsg(pubMsgStr);
    addIntValToMsg("t", temp, pubMsgStr);
    finishJsonMsg(pubMsgStr);

    // publish message
    printf("Publish: %s\n", pubMsgStr);

    retCode = mqtt_publish(&broker, pubTopic, pubMsgStr, 0);
    if (retCode < 0)
    {
        printf("Failed to publish data!\n");
    }
}

void temp_button_clicked(GtkWidget *button, gpointer data)
{
    char tempStr[32];
    char dir = *(char*)data;

    if (dir == '+')
    {
        temp += 5;
    }
    else if (dir == '-')
    {
        temp -= 5;
    }

    sprintf(tempStr, "%dC", temp);
    gtk_label_set_text(GTK_LABEL(tempLabel), tempStr);
    printf("temp=%s\n", tempStr);

    // temperature has changed, publish it
    publish_temp(temp);
}

void *listener_thread(void *threadId)
{
    int packet_length;
	uint16_t msg_id, msg_id_rcv;
	int error = 0;

	printf("Starting listener thread...\n");

    // >>>>> SUBSCRIBE
	mqtt_subscribe(&broker, "yefsec0rk7uhatp/HighTemp", &msg_id);
	// <<<<< SUBACK
	packet_length = read_packet(1);
	if(packet_length < 0)
	{
		fprintf(stderr, "Error(%d) on read packet!\n", packet_length);
		error = 1;
	}

	//printf("%s", packet_buffer);

	if(MQTTParseMessageType(packet_buffer) != MQTT_MSG_SUBACK)
	{
		fprintf(stderr, "SUBACK expected!\n");
		error = 1;
	}

	//printf("%s", packet_buffer);

	msg_id_rcv = mqtt_parse_msg_id(packet_buffer);
	if(msg_id != msg_id_rcv)
	{
		fprintf(stderr, "%d message id was expected, but %d message id was found!\n", msg_id, msg_id_rcv);
		error = 1;
	}

	//printf("%s", packet_buffer);

	if (error)
    {
        printf("error setting up listener socket\n");
    }

	while(1)
	{
	    printf("Waiting for message...\n");

		packet_length = read_packet(0);
		if(packet_length == -1)
		{
			fprintf(stderr, "Error(%d) on read packet!\n", packet_length);
			break;
		}
		else if(packet_length > 0)
		{
			printf("Packet Header: 0x%x...\n", packet_buffer[0]);
			if(MQTTParseMessageType(packet_buffer) == MQTT_MSG_PUBLISH)
			{
				uint8_t topic[255], msg[1000];
				uint16_t len;

				led("g1", 1);

				len = mqtt_parse_pub_topic(packet_buffer, topic);
				topic[len] = '\0'; // for printf
				len = mqtt_parse_publish_msg(packet_buffer, msg);
				msg[len] = '\0'; // for printf
				printf("%s %s\n", topic, msg);

				sleep(1);

				led("g1", 0);
			}
			else if (MQTTParseMessageType(packet_buffer) == MQTT_MSG_PINGRESP)
            {
                printf("Ping response received\n");
            }
		}

	}

	pthread_exit(NULL);
}

int main(int argc, char* argv[])
{
    GtkWidget *window;
    GtkWidget *inc_button, *dec_button;
    GtkWidget *hbox1;
    GtkWidget *vbox1;
    GtkWidget *label1;

    pthread_t thread;
    long t = 0;

    serverConnected = 0;

    if (server_connect(1) == 1)
    {
        pthread_create(&thread, NULL, listener_thread, (void*)t);

        gtk_init(&argc, &argv);

        window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
        gtk_window_set_title(GTK_WINDOW(window), "Opal6 ThingFabric Demo");
        gtk_window_set_position(GTK_WINDOW(window), GTK_WIN_POS_CENTER);
        gtk_window_set_default_size(GTK_WINDOW(window), 250, 50);

        g_signal_connect(G_OBJECT(window), "destroy", G_CALLBACK(closeApp), NULL);

        dec_button = gtk_button_new_with_label("  DOWN  ");
        g_signal_connect(G_OBJECT(dec_button), "clicked", G_CALLBACK(temp_button_clicked), "-");

        inc_button = gtk_button_new_with_label("    UP    ");
        g_signal_connect(G_OBJECT(inc_button), "clicked", G_CALLBACK(temp_button_clicked), "+");

        label1 = gtk_label_new("Temperature");
        tempLabel = gtk_label_new("25C");
        publish_temp(temp);  // send the initial value

        vbox1 = gtk_vbox_new(TRUE, 1);
        gtk_box_pack_start(GTK_BOX(vbox1), label1, TRUE, FALSE, 1);
        gtk_box_pack_start(GTK_BOX(vbox1), tempLabel, TRUE, FALSE, 1);

        hbox1 = gtk_hbox_new(TRUE, 5);
        gtk_box_pack_start(GTK_BOX(hbox1), dec_button, TRUE, FALSE, 5);
        gtk_box_pack_start(GTK_BOX(hbox1), vbox1, TRUE, FALSE, 5);
        gtk_box_pack_start(GTK_BOX(hbox1), inc_button, TRUE, FALSE, 5);

        gtk_container_add(GTK_CONTAINER(window), hbox1);

        gtk_widget_show_all(window);
        gtk_main();
    }
    else
    {
        printf("Error: Unable to connect to ThingFabric server\n");
    }

    return 0;
}
