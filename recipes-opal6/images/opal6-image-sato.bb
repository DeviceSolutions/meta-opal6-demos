# This image extends core-image-sato with the following:
#   GStreamer IMX support
#   Disk formatting tools
#   Modem and wireless support

require recipes-sato/images/core-image-sato.bb

IMAGE_FEATURES += "package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL_append = " packagegroup-fsl-gstreamer1.0-full"
IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " ppp linux-firmware canutils"
IMAGE_INSTALL_append = " opal6-scripts"
IMAGE_INSTALL_append = " mesa-demos nano"
IMAGE_INSTALL_append = " mono"
IMAGE_INSTALL_append = " v4l-utils"
IMAGE_INSTALL_append = " chromium"