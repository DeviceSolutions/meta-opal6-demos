require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Opal6 4.1.15 kernel"
DESCRIPTION = "Linux kernel for Device Solutions Opal6 boards."

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "opal6-4.1.15"
SRCREV = "${AUTOREV}"

LOCALVERSION = "-${SRCBRANCH}"
SCMVERSION = ""

SRC_URI = "git://github.com/DeviceSolutions/linux-opal6.git;branch=${SRCBRANCH}\
			file://defconfig \
			file://bcmhd_gcc6_indent_warning_error_fix.patch \
			file://gcc6_integrate_fix.patch \
			file://gpu-viv_gcc6_indent_warning_error_fix.patch \
"

COMPATIBLE_MACHINE = "(opal6dl|opal6q|opal6s)"
