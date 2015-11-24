SUMMARY = "Development Image for Opal6 Boards"

inherit core-image

IMAGE_INSTALL = "packagegroup-base-extended ${ROOTFS_PKGMANAGE_BOOTSTRAP} ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_FEATURES += "package-management ssh-server-dropbear"

IMAGE_INSTALL_append = " e2fsprogs-mke2fs dosfstools"
IMAGE_INSTALL_append = " ppp wvdial linux-firmware canutils"
IMAGE_INSTALL_append = " opal6-apps-rs485-test"
IMAGE_INSTALL_append = " imx-gpu-viv imx-gpu-viv-dev"
IMAGE_INSTALL_append = " libopencv-core-dev libopencv-highgui-dev \
			 libopencv-imgproc-dev libopencv-objdetect-dev libopencv-ml-dev"

LICENSE = "MIT"
