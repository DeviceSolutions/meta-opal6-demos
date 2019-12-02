#!/bin/bash

INTERFACES_PATH=/etc/network/interfaces
WPA_CONF_PATH=/etc/wpa_supplicant.conf

WPA_CONF_HDR="ctrl_interface=/var/run/wpa_supplicant
ctrl_interface_group=0
update_config=1"

echo Deleting all wireless networks
echo "$WPA_CONF_HDR" > $WPA_CONF_PATH
grep -vE 'wlan|wpa_supplicant' $INTERFACES_PATH > $INTERFACES_PATH.new
mv $INTERFACES_PATH $INTERFACES_PATH.old
mv $INTERFACES_PATH.new $INTERFACES_PATH
sync
echo done
