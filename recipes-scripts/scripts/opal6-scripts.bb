# Opal6 Helper Scripts

DESCRIPTION = "Opal6 Helper Scripts"
SECTION = "scripts"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

inherit systemd

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
	file://modem_power.sh \
	file://initsystem.service \
	file://startup.service \
	file://qtprofile.sh \
	file://qtstartup.sh \
	file://qtstartup.service \
"

FILES_${PN} += "/etc/udev/rules.d/11-automount.rules"

FILES_${PN} += "/lib/systemd/system"
SYSTEMD_SERVICE_${PN} = "initsystem.service startup.service qtstartup.service"
#SYSTEMD_AUTO_ENABLE_${PN} = "enable"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/gpio_config.sh ${D}${bindir}/gpio_config
	install -m 0755 ${WORKDIR}/gpio_value.sh ${D}${bindir}/gpio_value
	install -m 0755 ${WORKDIR}/Startup.sh ${D}${bindir}/Startup.sh
	install -m 0755 ${WORKDIR}/resetmodem.sh ${D}${bindir}/resetmodem
	install -m 0755 ${WORKDIR}/modem_power.sh ${D}${bindir}/modem_power
	install -m 0755 ${WORKDIR}/configure_mbim.sh ${D}${bindir}/configure_mbim
	install -m 0755 ${WORKDIR}/configure_pmic.exe ${D}${bindir}/configure_pmic.exe
	install -m 0755 ${WORKDIR}/InitSystem.sh ${D}${bindir}/InitSystem.sh
	install -m 0755 ${WORKDIR}/qtstartup.sh ${D}${bindir}/qtstartup.sh

	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/initsystem.service ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/startup.service ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/qtstartup.service ${D}${systemd_system_unitdir}

	install -d ${D}/etc/udev/rules.d
	install -m 644 ${WORKDIR}/11-automount.rules ${D}/etc/udev/rules.d/11-automount.rules

	install -d ${D}/etc/profile.d
	install -m 644 ${WORKDIR}/qtprofile.sh ${D}/etc/profile.d/qtprofile.sh
}