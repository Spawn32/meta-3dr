
SRC_URI = "git://github.com/Spawn32/imx6-uboot/"
SRCREV = "solo_v1.0.0"
# branch :imx_v2014.04_3.14.28_1.0.0_ga for Kernel 3.14.28
# save UBOOT_CONFIG as separate names
do_deploy_append() {
    install ${S}/${UBOOT_BINARY} ${DEPLOYDIR}/${UBOOT_IMAGE}_${UBOOT_CONFIG}
}
