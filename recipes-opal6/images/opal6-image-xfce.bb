# This image extends fsl-image-multimedia_full with the following:
#   XFCE desktop
#   Disk formatting tools
#   Modem and wireless support

require recipes-fsl/images/fsl-image-multimedia-full.bb

IMAGE_FEATURES += "package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " packagegroup-core-x11 packagegroup-xfce-base"
IMAGE_INSTALL_append = " ppp linux-firmware canutils"
IMAGE_INSTALL_append = " opal6-scripts"
IMAGE_INSTALL_append = " mesa-demos nano"
IMAGE_INSTALL_append = " mono"
IMAGE_INSTALL_append = " v4l-utils"
IMAGE_INSTALL_append = " chromium"