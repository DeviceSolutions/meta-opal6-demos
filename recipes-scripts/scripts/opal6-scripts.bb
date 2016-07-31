# Opal6 Helper Scripts

DESCRIPTION = "Opal6 Helper Scripts"
SECTION = "scripts"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

RDEPENDS_${PN} = "bash"

SRC_URI += "file://add_wifi.sh \
			file://delete_all_wireless_networks.sh \
"

do_install_append () {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/add_wifi.sh ${D}${bindir}/add_wifi
	install -m 0755 ${WORKDIR}/delete_all_wireless_networks.sh ${D}${bindir}/delete_all_wireless_networks
}