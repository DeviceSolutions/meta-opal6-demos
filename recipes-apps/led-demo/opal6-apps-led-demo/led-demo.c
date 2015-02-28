#include <gtk/gtk.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include <pthread.h>

void closeApp(GtkWidget *window, gpointer data)
{
    printf("Destroy\n");
    gtk_main_quit();
}

void led_clicked(GtkWidget *button, gpointer data)
{
    int tmp;
    char state;
    char *led = (char *)data;
    char path[64];

    sprintf(path, "/sys/class/leds/user-led-%s/brightness", led);

    tmp = open(path, O_RDWR);
    read(tmp, &state, 1);

    if (state =='0')
    {
        write(tmp, "1", 2);
    }
    else
    {
        write(tmp, "0", 2);
    }

    close(tmp);
}

int main(int argc, char *argv[])
{
    GtkWidget *window;
    GtkWidget *g1_button, *g2_button, *r1_button, *r2_button;
    GtkWidget *hbox1;

    gtk_init(&argc, &argv);

    window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
    gtk_window_set_title(GTK_WINDOW(window), "Opal6 LED Demo");
    gtk_window_set_position(GTK_WINDOW(window), GTK_WIN_POS_CENTER);
    gtk_window_set_default_size(GTK_WINDOW(window), 200, 50);

    g_signal_connect(G_OBJECT(window), "destroy", G_CALLBACK(closeApp), NULL);

    g1_button = gtk_button_new_with_label("LED G1");
    g_signal_connect(G_OBJECT(g1_button), "clicked", G_CALLBACK(led_clicked), "g1");
    g2_button = gtk_button_new_with_label("LED G2");
    g_signal_connect(G_OBJECT(g2_button), "clicked", G_CALLBACK(led_clicked), "g2");
    r1_button = gtk_button_new_with_label("LED R1");
    g_signal_connect(G_OBJECT(r1_button), "clicked", G_CALLBACK(led_clicked), "r1");
    r2_button = gtk_button_new_with_label("LED R2");
    g_signal_connect(G_OBJECT(r2_button), "clicked", G_CALLBACK(led_clicked), "r2");

    hbox1 = gtk_hbox_new(TRUE, 5);
    gtk_box_pack_start(GTK_BOX(hbox1), g1_button, TRUE, FALSE, 2);
    gtk_box_pack_start(GTK_BOX(hbox1), g2_button, TRUE, FALSE, 2);
    gtk_box_pack_start(GTK_BOX(hbox1), r1_button, TRUE, FALSE, 2);
    gtk_box_pack_start(GTK_BOX(hbox1), r2_button, TRUE, FALSE, 2);

    gtk_container_add(GTK_CONTAINER(window), hbox1);

    gtk_widget_show_all(window);
    gtk_main();

    return 0;
}
