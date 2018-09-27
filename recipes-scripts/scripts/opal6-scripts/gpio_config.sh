#!/bin/bash

GPIO_SYSDIR="/sys/class/gpio"
GPIO_PIN=$1
GPIO_DIRECTION=$2

if [ -z $GPIO_PIN ]
then
	echo "gpio pin is missing"
	exit
fi

if [ -z $GPIO_DIRECTION ]
then
	echo "Missing direction, assuming output"
	GPIO_DIRECTION=out
fi

GPIO_DIR=$GPIO_SYSDIR/gpio$GPIO_PIN

if [ ! -d $GPIO_DIR ]
then
	echo $GPIO_PIN > $GPIO_SYSDIR/export
fi

if [ -d $GPIO_DIR ]
then
	echo $GPIO_DIRECTION > $GPIO_DIR/direction
fi

