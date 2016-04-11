require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Opal6 4.1.15 kernel"
DESCRIPTION = "Linux kernel for Device Solutions Opal6 boards."

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_4.1.15_1.0.0_ga"
SRCREV = "6e9e34aeddd645b25ae4efff55305688d7778e0b"
LOCALVERSION = "-opal6"

SRC_URI += "file://defconfig \
			file://csi_mux_init.patch \
	    	file://dt_opal6.patch \
			file://dt_fix_spidev_message.patch \
"

COMPATIBLE_MACHINE = "(opal6dl|opal6q)"
