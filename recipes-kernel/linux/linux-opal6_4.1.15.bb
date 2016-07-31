require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Opal6 4.1.15 kernel"
DESCRIPTION = "Linux kernel for Device Solutions Opal6 boards."

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "opal6-4.1.15-003"
SRCREV = "7f3d9f188f9c6821969c00b0836020bbd36fbf4e"

LOCALVERSION = "-${SRCBRANCH}"
SCMVERSION = ""

SRC_URI = "git://github.com/DeviceSolutions/linux-opal6.git;branch=${SRCBRANCH}\
		   file://defconfig \
"

COMPATIBLE_MACHINE = "(opal6dl|opal6q)"
