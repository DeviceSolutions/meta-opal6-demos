require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"
COMPATIBLE_MACHINE = "(opal6dl|opal6q)"

PROVIDES = "u-boot"

PV = "v2015.01"

# Bitbucket
SRCREV = "${AUTOREV}"
SRCBRANCH = "opal6_2015.01"
SRC_URI = "git://github.com/DeviceSolutions/u-boot-opal6.git;branch=${SRCBRANCH}"
S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
