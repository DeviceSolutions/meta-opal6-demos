#!/bin/bash

INTERFACES_PATH=/etc/network/interfaces
WPA_CONF_PATH=/etc/wpa_supplicant.conf

unset SSID
unset PASSPHRASE
AUTO=true

function configure_interface
{
	if [ -z "$(grep 'auto wlan0' $INTERFACES_PATH)" ]
	then
		printf "\n# wlan0 interface\nauto wlan0\niface wlan0 inet dhcp\n" >> $INTERFACES_PATH
		if [ "$AUTO" = "true" ] && [ -z "$(grep 'wpa_supplicant' $INTERFACES_PATH)" ]
		then
			printf "pre-up wpa_supplicant -iwlan0 -c/etc/wpa_supplicant.conf -B\n" >> $INTERFACES_PATH
			printf "post-down killall -q wpa_supplicant\n" >> $INTERFACES_PATH
		fi
	else
		echo "wlan0 interface already configured!"
	fi
}

function add_wifi_network
{
	if [ -z "$(grep "ssid=\"$SSID\"" $WPA_CONF_PATH)" ]
	then
		PSK=$(wpa_passphrase $SSID $PASSPHRASE | grep psk | grep -v '#psk' | awk -F "=" '{ print $2 }')
		printf "\nnetwork={\n\tssid=\"%s\"\n\tpsk=%s\n}\n" $SSID $PSK >> $WPA_CONF_PATH
	else
		echo "\"$SSID\" already configured!"
	fi
}

function show_help
{
	echo "Usage: sh add_wifi.sh SSID PASSPHRASE"
}

SSID=$1
PASSPHRASE=$2

if [ -z "$SSID" ] || [ -z "$PASSPHRASE" ]
then
	echo Missing parameters
	show_help
	exit 1
fi

if [ "$3" = "manual" ]
then
	AUTO=false
fi

echo "Adding network ssid=$SSID, passphrase=$PASSPHRASE"
add_wifi_network $SSID $PASSPHRASE
configure_interface
sync
