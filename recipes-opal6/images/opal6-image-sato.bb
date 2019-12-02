# This image extends core-image-sato with the following:
#   GStreamer IMX support
#   Disk formatting tools
#   Modem and wireless support

require recipes-sato/images/core-image-sato.bb

IMAGE_FEATURES += "package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL_append = " packagegroup-fsl-gstreamer1.0-full"
IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " canutils v4l-utils"
IMAGE_INSTALL_append = " linux-firmware"
IMAGE_INSTALL_append = " nano mono ntp rsync tzdata iptables"
IMAGE_INSTALL_append = " nodejs icu libunwind"
#IMAGE_INSTALL_append = " networkmanager modemmanager usb-modeswitch glibc-gconvs glibc-utils"
IMAGE_INSTALL_append = " opal6-scripts"
