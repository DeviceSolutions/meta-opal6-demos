LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file:///home/jinglis/opal6-u-boot-2015.01/README;md5=813b058284702a930d44d94cca59ee96"
DEPENDS = "u-boot-mkimage-native"

PV = "v2015.01"

# Bitbucket
SRCREV = "${AUTOREV}"
SRCBRANCH = "master"
SRC_URI = "git://bitbucket.org/devicesolutionslinux/opal6-u-boot-2015.01.git;branch=${SRCBRANCH};protocol=http"

S = "${WORKDIR}/git"

inherit deploy

BOOTSCRIPT = "${S}/board/devicesolutions/opal6boards/6x_bootscript.txt"
UPGRADESCRIPT = "${S}/board/devicesolutions/opal6boards/6x_upgrade.txt"

do_mkimage () {
    # allow deploy to use the ${MACHINE} name to simplify things
    if [ ! -d board/devicesolutions/${MACHINE} ]; then
        mkdir -p board/devicesolutions/${MACHINE}
    fi

    uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
                  -n "boot script" -d ${BOOTSCRIPT} \
                  board/devicesolutions/${MACHINE}/6x_bootscript

    uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
                  -n "upgrade script" -d ${UPGRADESCRIPT} \
                  board/devicesolutions/${MACHINE}/6x_upgrade
}

addtask mkimage after do_compile before do_install

do_deploy () {
    install -d ${DEPLOYDIR}
    install ${S}/board/devicesolutions/${MACHINE}/6x_bootscript \
            ${DEPLOYDIR}/6x_bootscript-${MACHINE}-${PV}-${PR}
    install ${S}/board/devicesolutions/${MACHINE}/6x_upgrade \
            ${DEPLOYDIR}/6x_upgrade-${MACHINE}-${PV}-${PR}

    cd ${DEPLOYDIR}
    rm -f 6x_bootscript-${MACHINE} 6x_upgrade-${MACHINE}
    ln -sf 6x_bootscript-${MACHINE}-${PV}-${PR} 6x_bootscript-${MACHINE}
    ln -sf 6x_upgrade-${MACHINE}-${PV}-${PR} 6x_upgrade-${MACHINE}
}

addtask deploy after do_install before do_build

do_compile[noexec] = "1"
do_install[noexec] = "1"
do_populate_sysroot[noexec] = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "opal6dl"
