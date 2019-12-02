require recipes-kernel/linux/linux-imx.inc

SUMMARY = "Opal6 4.14.x kernel"
DESCRIPTION = "Linux kernel for Device Solutions Opal6 boards."

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "4.14-2.0.x-imx"
SRCREV = "${AUTOREV}"

LOCALVERSION = "-${RELEASE_VERSION}"
SCMVERSION = ""

SRC_URI = "git://github.com/Freescale/linux-fslc.git;branch=${SRCBRANCH} \
			file://defconfig \
			file://imx6dl-opal.dtsi \
			file://imx6q-opal.dtsi \
			file://imx6dl-opaldk.dts \
			file://imx6q-opaldk.dts \
			file://csi_mux.patch \
			file://logo_linux_clut224.ppm \
"

do_configure_append () {
	cp ${WORKDIR}/imx6dl-opal.dtsi ${S}/arch/arm/boot/dts/
	cp ${WORKDIR}/imx6q-opal.dtsi ${S}/arch/arm/boot/dts/
	cp ${WORKDIR}/imx6dl-opaldk.dts ${S}/arch/arm/boot/dts/
	cp ${WORKDIR}/imx6q-opaldk.dts ${S}/arch/arm/boot/dts/
	cp ${WORKDIR}/logo_linux_clut224.ppm ${S}/drivers/video/logo/
}

COMPATIBLE_MACHINE = "(opal6dl|opal6q|opal6s)"
