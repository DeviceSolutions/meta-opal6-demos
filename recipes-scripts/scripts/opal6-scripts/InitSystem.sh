#!/bin/bash

# configure local timezone
rm -f /etc/localtime
ln -sf /usr/share/zoneinfo/NZ /etc/localtime

# workaround YOCTO refusing to create libpcap.so on filesystem
if [ ! -f /usr/lib/libpcap.so ]
then
	ln -s libpcap.so.1 /usr/lib/libpcap.so
	chmod 0755 /usr/lib/libpcap.so
fi

# remove dnsmasq init - network manager now takes care of this
rm -f /etc/rc5.d/S20dnsmasq
/etc/init.d/dnsmasq stop

rm -f /etc/rc5.d/S22ofono
/etc/init.d/ofono stop

# run this script once only so remove when done
rm -f /etc/rc5.d/S80InitSystem
