#!/bin/bash

PATH="/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin"

# configure pmic
/usr/bin/configure_pmic.exe w 0x9f 0x01 &

# configure can interfaces
#canconfig can0 bitrate 250000 start
#canconfig can1 bitrate 250000 start

# enable modem
/usr/bin/modem_power
