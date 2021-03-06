# This image extends fsl-image-multimedia_full with the following:
#   XFCE desktop
#   Disk formatting tools
#   Modem and wireless support

require recipes-fsl/images/fsl-image-multimedia-full.bb

IMAGE_FEATURES += "package-management ssh-server-dropbear"

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " packagegroup-xfce-base"
IMAGE_INSTALL_append = " ppp wvdial linux-firmware canutils"
IMAGE_INSTALL_append = " opal6-apps-led-demo opal6-scripts"
IMAGE_INSTALL_append = " mesa-demos nano mousepad"
IMAGE_INSTALL_append = " opencv opencv-dev opencv-samples boost boost-dev"
IMAGE_INSTALL_append = " mono mono-helloworld"
IMAGE_INSTALL_append = " v4l-utils libav"