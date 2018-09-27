FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

DEPENDS += "gdk-pixbuf-native"

PRINC = "8"

SRC_URI += "file://psplash-colors.h \
			file://psplash-config.h \
			file://remove_progressbar_msgfield.patch"

SPLASH_IMAGES = "file://psplash-poky-img.png;outsuffix=default"

do_configure_append () {
	cd ${S}
	cp ../psplash-colors.h ./
	cp ../psplash-config.h ./
}
