#include <linux/serial.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <stdio.h>
#include <termios.h>
#include <unistd.h>
#include <poll.h>

#define eprintf(...) fprintf (stdout, __VA_ARGS__)

int main(int argc, char *argv[])
{
    char ch;
    struct termios options, in_options;
    char buf[64];
    struct pollfd pfd[2];

    int fd = open ("/dev/ttymxc1", O_RDWR | O_NOCTTY | O_NONBLOCK);
    if (fd < 0) {
        eprintf("Cannot open port, error %d\n", fd);
        return fd;
    }

    tcgetattr(fd, &options);
    cfmakeraw(&options);
    options.c_iflag |= ICRNL;
    tcsetattr(fd, TCSAFLUSH, &options);

    // set stdin to raw mode
    if (isatty(STDIN_FILENO))
    {
        tcgetattr(STDIN_FILENO, &in_options);
        options = in_options;
        cfmakeraw(&options);
        //options.c_iflag |= ICRNL;
        tcsetattr(STDIN_FILENO, TCSAFLUSH, &options);
    }

    pfd[0].fd = fd;
	pfd[0].events = POLLIN;
	pfd[1].fd = STDIN_FILENO;
	pfd[1].events = POLLIN;

    while (poll(pfd, 2, -1) > 0)
    {
        if (pfd[1].revents)
        {
            if (read(STDIN_FILENO, &ch ,1) > 0)
            {
                if (ch == 24)  // ctrl-x
                {
                    break;
                }

                write(fd, &ch, 1);
            }
        }

        if (pfd[2].revents)
        {
            ssize_t len = read(fd, buf, sizeof(buf));
            if (len > 0)
                write(STDOUT_FILENO, buf, len);
        }
    }

    if (close (fd) < 0) {
        eprintf("Cannot close port, error %d\n", fd);
        return fd;
    }

    // revert stdin to normal usage
    tcsetattr(STDIN_FILENO, TCSAFLUSH, &in_options);

    eprintf("Goodbye\n");
    return 0;
}
