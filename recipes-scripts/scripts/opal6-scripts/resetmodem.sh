#!/bin/bash

gpio_config 107 out
gpio_value 107 0
usleep 250000
gpio_value 107 1
