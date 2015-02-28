# Adapted from linux-imx.inc, copyright (C) 2013, 2014 O.S. Systems Software LTDA
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Linux kernel for Device Solution boards"

SRCREV = "${AUTOREV}"
SRCBRANCH = "imx6dl-opal"
SRC_URI = "git://bitbucket.org/devicesolutions/opal6-linux-3.10.17.git;branch=${SRCBRANCH};protocol=http;user=user:password \
           file://defconfig"

# Patches
SRC_URI += "file://opal6-mmc-enum.patch"

LOCALVERSION = "-1.0.1_ga+yocto"
DEPENDS += "lzop-native bc-native"
COMPATIBLE_MACHINE = "(mx6)"
COMPATIBLE_MACHINE = "(opal6dl)"
