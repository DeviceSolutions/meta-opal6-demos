# This image extends fsl-image-multimedia_full with the following:
#   CRANK UI Support
#   Disk formatting tools
#   Modem and wireless support

require recipes-fsl/images/fsl-image-multimedia-full.bb

IMAGE_FEATURES += "package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " linux-firmware canutils v4l-utils"
IMAGE_INSTALL_append = " nano mono ntp rsync tzdata iptables"
IMAGE_INSTALL_append = " nodejs icu libunwind"
IMAGE_INSTALL_append = " networkmanager modemmanager usb-modeswitch glibc-gconvs glibc-utils"
IMAGE_INSTALL_append = " opal6-scripts psplash"

IMAGE_INSTALL_append = " tslib tslib-conf tslib-tests tslib-calibrate"
IMAGE_INSTALL_append = " opal6-crank"

IMAGE_INSTALL_remove += "ofono"