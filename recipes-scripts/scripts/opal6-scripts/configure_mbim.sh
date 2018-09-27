#!/bin/bash

HAS_MBIM=$(lsusb | grep 1bc7:0032)

if [[ -z $HAS_MBIM ]]
then
	logger "configuring MBIM interface..."

	/etc/init.d/networkmanager stop
	/usr/bin/resetmodem
	
	while [ ! -c /dev/ttyACM0 ]
	do
		sleep 1
	done

	printf "AT#USBCFG=3\r" > /dev/ttyACM0
	printf "AT#REBOOT\r" > /dev/ttyACM0
fi
		
