SUMMARY = "Development Image for Opal6 Boards"

inherit core-image

IMAGE_INSTALL = "packagegroup-base-extended ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_FEATURES += "package-management ssh-server-dropbear"

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " linux-firmware canutils v4l-utils"
IMAGE_INSTALL_append = " nano mono ntp rsync tzdata iptables"
IMAGE_INSTALL_append = " nodejs icu libunwind"
IMAGE_INSTALL_append = " networkmanager modemmanager usb-modeswitch glibc-gconvs glibc-utils"
IMAGE_INSTALL_append = " opal6-scripts psplash"

IMAGE_INSTALL_remove += "ofono"

LICENSE = "MIT"
