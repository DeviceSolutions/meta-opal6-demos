FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://0003-Remove-check-for-useSIGIO-option.patch"
