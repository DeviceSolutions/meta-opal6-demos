require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=c7383a594871c03da76b3707929d2919"
COMPATIBLE_MACHINE = "(opal6dl)"

PROVIDES = "u-boot"

PV = "v2015.01+git${SRCPV}"

SRCREV = "${AUTOREV}"
SRCBRANCH = "master"
SRC_URI = "git://bitbucket.org/devicesolutions/opal6-u-boot-2015.01.git;branch=${SRCBRANCH};protocol=http;user=user:password"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
