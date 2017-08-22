
SRC_URI = "git://github.com/Spawn32/imx6-uboot/"
SRCREV = "solo_v1.0.0"
#SRCREV = "sololink_v1.9.1"
# Tag :sololink_v1.9.1 for Kernel 3.14.28
# save UBOOT_CONFIG as separate names
do_deploy_append() {
    install ${S}/${UBOOT_BINARY} ${DEPLOYDIR}/${UBOOT_IMAGE}_${UBOOT_CONFIG}
}
