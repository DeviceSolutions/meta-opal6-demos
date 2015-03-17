# This image extends fsl-image-multimedia_full with the following:
#   XFCE desktop
#   Disk formatting tools
#   Modem and wireless support

require recipes-fsl/images/fsl-image-multimedia-full.bb

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " packagegroup-xfce-extended midori"
IMAGE_INSTALL_append = " ppp wvdial linux-firmware canutils"
IMAGE_INSTALL_append = " opal6-apps-led-demo opal6-apps-rs485-test"
