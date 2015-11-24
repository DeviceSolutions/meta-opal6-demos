DESCRIPTION = "Opal6 GPIO Demo Application"
SECTION = "apps"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

SRC_URI = "file://gpio-demo.c"

S = "${WORKDIR}"

do_compile() {
	     ${CC} gpio-demo.c -o gpio-demo
}

do_install() {
	     install -d ${D}${bindir}
	     install -m 0755 gpio-demo ${D}${bindir}
}
