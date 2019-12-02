# This image extends fsl-image-multimedia_full with the following:
#   Wayland/Weston desktop
#   Disk formatting tools
#   Network manager / modem manager

require recipes-fsl/images/fsl-image-multimedia-full.bb

LICENSE = "MIT"

IMAGE_FEATURES += "package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " linux-firmware canutils v4l-utils"
IMAGE_INSTALL_append = " nano mono ntp rsync tzdata iptables"
IMAGE_INSTALL_append = " nodejs icu libunwind"
IMAGE_INSTALL_append = " networkmanager modemmanager usb-modeswitch glibc-gconvs glibc-utils"
IMAGE_INSTALL_append = " opal6-scripts"
