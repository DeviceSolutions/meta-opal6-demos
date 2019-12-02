#!/bin/bash

echo 170 > /sys/class/gpio/export
echo 174 > /sys/class/gpio/export
echo 106 > /sys/class/gpio/export
echo 107 > /sys/class/gpio/export

echo out > /sys/class/gpio/gpio170/direction
echo out > /sys/class/gpio/gpio174/direction
echo out > /sys/class/gpio/gpio106/direction
echo out > /sys/class/gpio/gpio107/direction

echo 0 > /sys/class/gpio/gpio170/value
echo 0 > /sys/class/gpio/gpio174/value
echo 0 > /sys/class/gpio/gpio106/value
echo 0 > /sys/class/gpio/gpio107/value

sleep 1

echo 1 > /sys/class/gpio/gpio170/value
echo 1 > /sys/class/gpio/gpio174/value
echo 1 > /sys/class/gpio/gpio106/value
echo 1 > /sys/class/gpio/gpio107/value
