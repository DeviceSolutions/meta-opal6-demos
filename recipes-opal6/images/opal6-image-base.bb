SUMMARY = "Development Image for Opal6 Boards"

inherit core-image

IMAGE_INSTALL = "packagegroup-base-extended ${ROOTFS_PKGMANAGE_BOOTSTRAP} ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_FEATURES += "package-management ssh-server-dropbear"

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " ppp wvdial linux-firmware canutils"
IMAGE_INSTALL_append = " opencv opencv-dev opencv-samples boost boost-dev"
IMAGE_INSTALL_append = " nano mono mono-helloworld"

LICENSE = "MIT"
