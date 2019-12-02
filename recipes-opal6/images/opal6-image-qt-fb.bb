# QT5 Demo Image for Opal6 Boards
LICENSE = "MIT"

inherit core-image

IMAGE_FEATURES += "package-management ssh-server-dropbear hwcodecs"

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " canutils v4l-utils"
IMAGE_INSTALL_append = " nano ntp rsync tzdata iptables"
IMAGE_INSTALL_append = " networkmanager modemmanager usb-modeswitch glibc-gconvs glibc-utils"
IMAGE_INSTALL_append = " opal6-scripts"

# QT needs some fonts...
IMAGE_INSTALL_append = " ttf-dejavu-sans \
                         ttf-dejavu-sans-mono \
                         ttf-dejavu-sans-condensed \
                         ttf-dejavu-serif \
                         ttf-dejavu-serif-condensed \
                         ttf-dejavu-common"

# QT components...
IMAGE_INSTALL_append = " qtbase \
                         qttools \
                         qtsvg"

# QT demos...
IMAGE_INSTALL_append = " qtsmarthome \
                         cinematicexperience \
                         qt5everywheredemo"
