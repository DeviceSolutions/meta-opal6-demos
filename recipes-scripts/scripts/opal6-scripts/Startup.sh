#!/bin/bash

APP_DIR=/home/root/ATDM_Scripts

if [ -f ${APP_DIR}/configure_pmic.exe ]
then
    ${APP_DIR}/configure_pmic.exe w 0x9f 0x01 &
fi

# enable modem
/usr/bin/gpio_config 106 out
/usr/bin/gpio_value 106 1
