require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"
COMPATIBLE_MACHINE = "(opal6dl)"

PROVIDES = "u-boot"

PV = "v2015.01"

# Bitbucket
SRCREV = "${AUTOREV}"
SRCBRANCH = "master"
SRC_URI = "git://bitbucket.org/devicesolutionslinux/opal6-u-boot-2015.01.git;branch=${SRCBRANCH};protocol=http"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
