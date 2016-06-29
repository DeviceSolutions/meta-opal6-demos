# PPP Custom Scripts for Opal6 Development Kit

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://pap \
	   	    file://provider \
	        file://2degrees \
"

do_install_append () {
	install -m 0755 ${WORKDIR}/pon ${D}${bindir}/pon
	install -m 0755 ${WORKDIR}/pap ${D}${sysconfdir}/chatscripts
	install -m 0755 ${WORKDIR}/provider ${D}${sysconfdir}/ppp/peers/provider
	install -m 0755 ${WORKDIR}/2degrees ${D}${sysconfdir}/ppp/peers/2degrees
}
