# Adapted from linux-imx.inc, copyright (C) 2013, 2014 O.S. Systems Software LTDA
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Linux kernel for Device Solution boards"

# Bitbucket
SRCBRANCH = "imx6dl-opal"
SRCREV = "${AUTOREV}"
SRC_URI = "git://bitbucket.org/devicesolutionslinux/opal6-linux-3.10.17.git;branch=${SRCBRANCH};protocol=http \
           file://defconfig"

LOCALVERSION = "-1.0.1_ga"
DEPENDS += "lzop-native bc-native"

COMPATIBLE_MACHINE = "(mx6)"
COMPATIBLE_MACHINE = "(opal6dl)"
