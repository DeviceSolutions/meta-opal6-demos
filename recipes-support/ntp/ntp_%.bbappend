SUMMARY = "Network Time Protocol daemon and utilities"
DESCRIPTION = "The Network Time Protocol (NTP) is used to \
synchronize the time of a computer client or server to \
another server or reference time source, such as a radio \
or satellite receiver or modem."

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://ntp.conf \
"

do_install_append () {
	install -m 0644 ${WORKDIR}/ntp.conf ${D}${sysconfdir}/ntp.conf
}

