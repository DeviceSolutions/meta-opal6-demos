DESCRIPTION = "Demo GTK+ Application"
SECTION = "apps"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

SRC_URI = "file://led-demo.c"

S = "${WORKDIR}"

do_compile() {
	     ${CC} led-demo.c -o led-demo `pkg-config --cflags --libs gtk+-2.0`
}

do_install() {
	     install -d ${D}${bindir}
	     install -m 0755 led-demo ${D}${bindir}
}
