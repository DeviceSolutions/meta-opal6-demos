#
# This file was derived from the 'Hello World!' example recipe in the
# Yocto Project Development Manual.
#

DESCRIPTION = "RS485 Test Application"
SECTION = "apps"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

SRC_URI = "file://rs485-test.c"

S = "${WORKDIR}"

do_compile() {
	     ${CC} rs485-test.c -o rs485-test
}

do_install() {
	     install -d ${D}${bindir}
	     install -m 0755 rs485-test ${D}${bindir}
}
