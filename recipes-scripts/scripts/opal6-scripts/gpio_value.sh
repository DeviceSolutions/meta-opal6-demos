#!/bin/bash

GPIO_PIN=$1
GPIO_VALUE=$2

if [ -z $GPIO_PIN ]
then
	echo "gpio pin is missing"
	exit
fi

if [ -z $GPIO_VALUE ]
then
	echo "gpio value is missing"
	exit
fi

GPIO_DIR="/sys/class/gpio/gpio$GPIO_PIN"

if [ ! -d $GPIO_DIR ]
then
	echo "gpio not exported, run gpio_config"
	exit
fi

echo $GPIO_VALUE > $GPIO_DIR/value
