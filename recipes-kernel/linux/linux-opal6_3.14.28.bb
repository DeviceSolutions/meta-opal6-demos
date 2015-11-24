require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

SUMMARY = "Opal6 3.14.28 kernel"
DESCRIPTION = "Linux kernel for Device Solutions Opal6 boards."

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_3.14.28_1.0.0_ga"
SRCREV = "91cf351a2afc17ac4a260e4d2ad1e32d00925a1b"
LOCALVERSION = "-opal6dl"

SRC_URI += "file://defconfig \
	    file://imx6dl-opal6dk.patch \
	    file://csi_mux_init.patch \
	    file://dt_lvds_fix.patch \
	    file://rs485.patch \
	    file://dt_sgtl5000.patch \
"

COMPATIBLE_MACHINE = "(opal6dl)"
