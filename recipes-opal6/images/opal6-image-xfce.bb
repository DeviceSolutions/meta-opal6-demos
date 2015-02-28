# This image extends fsl-image-multimedia with the following:
#   XFCE desktop
#   Disk formatting tools
#   Modem and wireless support

IMAGE_FEATURES += "\
    ${@base_contains('DISTRO_FEATURES', 'x11', 'x11-base', '', d)} \
"

LICENSE = "MIT"

inherit core-image

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-fsl-gstreamer \
    packagegroup-fsl-tools-gpu \
    ${@base_contains('DISTRO_FEATURES', 'directfb', 'packagegroup-core-directfb', '', d)} \
    ${@base_contains('DISTRO_FEATURES', 'x11', '', \
                      base_contains('DISTRO_FEATURES', 'wayland', \
                                    'weston weston-init weston-examples \
                                         gtk+3-demo clutter-1.0-examples', '', d), d)} \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-fsl-gstreamer-full \
"

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " packagegroup-xfce-extended midori"
IMAGE_INSTALL_append = " ppp wvdial linux-firmware"
