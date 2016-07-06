# PPP Custom Scripts for Opal6 Development Kit

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://pap \
	   	    file://provider \
"

do_install_append () {
	install -m 0755 ${WORKDIR}/pap ${D}${sysconfdir}/chatscripts/pap
	install -m 0755 ${WORKDIR}/provider ${D}${sysconfdir}/ppp/peers/provider
}
