# Network manager extensions for ATDM Boards

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += " file://NetworkManager.conf \
	     file://networkmanager-init \
	     file://telit \
             file://ethernet \
"

RDEPENDS_${PN} = "bash"

### remove polkit since it depends on X11 features ###
DEPENDS_remove = "polkit"
EXTRA_OECONF += "--enable-polkit=disabled"
PACKAGECONFIG_remove = "consolekit"
PACKAGECONFIG_append = " modemmanager ppp"

do_install_append() {
	install -d ${D}${sysconfdir}/NetworkManager/
	install -m 0600 ${WORKDIR}/NetworkManager.conf ${D}${sysconfdir}/NetworkManager/
	
	install -d ${D}${sysconfdir}/NetworkManager/system-connections/
	install -m 0600 ${WORKDIR}/telit ${D}${sysconfdir}/NetworkManager/system-connections/telit
	install -m 0600 ${WORKDIR}/ethernet ${D}${sysconfdir}/NetworkManager/system-connections/ethernet
	
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/networkmanager-init ${D}${sysconfdir}/init.d/networkmanager
	
	install -d ${D}${sysconfdir}/rc5.d
	ln -sf ${sysconfdir}/init.d/networkmanager ${D}${sysconfdir}/rc5.d/S08networkmanager
}

#INITSCRIPT_NAME = "networkmanager"
#INITSCRIPT_PARAMS = "start 03 2 3 4 5 . stop 80 0 6 1 ."