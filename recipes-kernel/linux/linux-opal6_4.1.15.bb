require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Opal6 4.1.15 kernel"
DESCRIPTION = "Linux kernel for Device Solutions Opal6 boards."

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "opal6-4.1.15-001"
SRCREV = "f68a54193db60f59b38c4b06aad03d87cb8190ac"

LOCALVERSION = "-${SRCBRANCH}"
SCMVERSION = ""

SRC_URI = "git://github.com/DeviceSolutions/linux-opal6.git;branch=${SRCBRANCH}\
		   file://defconfig \
"

COMPATIBLE_MACHINE = "(opal6dl|opal6q)"
