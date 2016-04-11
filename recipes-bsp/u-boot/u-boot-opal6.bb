require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"
COMPATIBLE_MACHINE = "(opal6dl|opal6q)"

PROVIDES = "u-boot"

PV = "v2015.01"

# Bitbucket
SRCREV = "${AUTOREV}"
SRCBRANCH = "master"
SRC_URI = "git://bitbucket.org/devicesolutionslinux/opal6-u-boot-2015.01.git;branch=${SRCBRANCH};protocol=http"
SRC_URI += "file://0001-uboot-support-gcc5.patch "
S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
