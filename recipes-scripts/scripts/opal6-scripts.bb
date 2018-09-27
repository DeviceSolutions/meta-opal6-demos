# Opal6 Helper Scripts

DESCRIPTION = "Opal6 Helper Scripts"
SECTION = "scripts"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

RDEPENDS_${PN} = "bash"

SRC_URI += " \
	file://gpio_config.sh \
	file://gpio_value.sh \
	file://11-automount.rules \
	file://Startup.sh \
	file://InitSystem.sh \
	file://configure_mbim.sh \
	file://resetmodem.sh \
	file://configure_pmic.exe \
"

FILES_${PN} += "/etc/udev/rules.d/11-automount.rules"

do_install_append () {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/add_wifi.sh ${D}${bindir}/add_wifi
	install -m 0755 ${WORKDIR}/delete_all_wireless_networks.sh ${D}${bindir}/delete_all_wireless_networks
	install -m 0755 ${WORKDIR}/gpio_config.sh ${D}${bindir}/gpio_config
	install -m 0755 ${WORKDIR}/gpio_value.sh ${D}${bindir}/gpio_value
	install -m 0755 ${WORKDIR}/Startup.sh ${D}${bindir}/StartUp
	install -m 0755 ${WORKDIR}/resetmodem.sh ${D}${bindir}/resetmodem
	install -m 0755 ${WORKDIR}/configure_mbim.sh ${D}${bindir}/configure_mbim
	install -m 0755 ${WORKDIR}/configure_pmic.exe ${D}${bindir}/configure_pmic.exe
	install -m 0755 ${WORKDIR}/InitSystem.sh ${D}${bindir}/InitSystem

	install -d ${D}${sysconfdir}/rc5.d
	ln -sf ${bindir}/Startup ${D}${sysconfdir}/rc5.d/S91Startup
	ln -sf ${bindir}/InitSystem ${D}${sysconfdir}/rc5.d/S80InitSystem
	
	install -d ${D}/etc/udev/rules.d
	install -m 644 ${WORKDIR}/11-automount.rules ${D}/etc/udev/rules.d/11-automount.rules
}