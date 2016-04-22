# Copyright (C) 2014 O.S. Systems Software LTDA.
# Copyright (C) 2014-2015 Freescale Semiconductor

SUMMARY = "Produces a Manufacturing Tool compatible Linux Kernel"
DESCRIPTION = "Linux Kernel provided and supported by Freescale that produces a \
Manufacturing Tool compatible Linux Kernel to be used in updater environment"

require linux-opal6_${PV}.bb
require recipes-kernel/linux/linux-mfgtool.inc
