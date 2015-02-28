DESCRIPTION = "Demo ThingFabric Application"
SECTION = "apps"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

SRC_URI = "file://client/opal6-demo.c \
	   file://src/libemqtt.c \
	   file://include/libemqtt.h \
"

S = "${WORKDIR}"

do_compile() {
	${CC} -Iinclude -Wall -O -pthread -c src/libemqtt.c -o libemqtt.o
	${AR} rcs libemqtt.a libemqtt.o
	${CC} -Iinclude -Wall -O -pthread -c client/opal6-demo.c -o opal6-demo.o `pkg-config --cflags --libs gtk+-2.0`
	${CC} -Iinclude opal6-demo.o -o client/opal6-demo -L. -lemqtt `pkg-config --cflags --libs gtk+-2.0`
}

do_install() {
	     install -d ${D}${bindir}
	     install -m 0755 client/opal6-demo ${D}${bindir}
}
