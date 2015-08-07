SUMMARY = "Development Image for Opal6 Boards"

inherit core-image

IMAGE_INSTALL = "packagegroup-base-extended ${ROOTFS_PKGMANAGE_BOOTSTRAP} ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_FEATURES += "package-management ssh-server-dropbear"

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"

LICENSE = "MIT"
