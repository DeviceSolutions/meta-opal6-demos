#!/bin/bash

PATH="/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin"

export QT_QPA_FONTDIR=/usr/share/fonts/ttf
export FB_MULTI_BUFFER=3
export DISPLAY=:0

cd /usr/share/qt5everywheredemo-1.0
./QtDemo -platform eglfs
