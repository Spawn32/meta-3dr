# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Freescale"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_3.14.28_1.0.0_ga"
LOCALVERSION = "-1.0.0_ga"
SRCREV = "91cf351a2afc17ac4a260e4d2ad1e32d00925a1b"

SRC_URI += " \
    file://0001-ARM-imx6q-drop-unnecessary-semicolon.patch \
    file://0002-ARM-clk-imx6q-fix-video-divider-for-rev-T0-1.0.patch \
    file://0003-ARM-imx6sl-Disable-imx6sl-specific-code-when-imx6sl-.patch \
    file://0004-mmc-sdhci-esdhc-imx-Fixup-runtime-PM-conditions-duri.patch \
    file://0005-Revert-net-fec-fix-the-warning-found-by-dma-debug.patch \
"

COMPATIBLE_MACHINE = "(mx6)"

IMX_TEST_SUPPORT = "y"

INITRAMFS_IMAGE = "3dr-initramfs"

do_configure_prepend() {
   # In some cases we'll use a different defconfig
   # example is manufacturing image which uses imx_v7_mfg_defconfig
   # however need way to change it back during daily build

   if [ -z "${FSL_KERNEL_DEFCONFIG}" ] ; then
       echo " defconfig from local.conf not set"
       fsl_defconfig='imx6solo_defconfig'
   else
       echo " Use local.conf for defconfig to set"
       fsl_defconfig=${FSL_KERNEL_DEFCONFIG}
   fi

   # check that defconfig file exists
   if [ ! -e "${S}/arch/arm/configs/$fsl_defconfig" ]; then
       fsl_defconfig='imx6solo_defconfig'
   fi


    cp ${S}/arch/arm/configs/${fsl_defconfig} ${S}/.config
    cp ${S}/arch/arm/configs/${fsl_defconfig} ${S}/../defconfig

    cp ${WORKDIR}/aufs_type.h ${S}/include/uapi/linux
    cp -r ${WORKDIR}/aufs ${S}/fs/
}

# copy zImage to deploy directory
# update uImage with defconfig ane git info in name
# this is since build script can build multiple ways
# and will overwrite previous builds

do_deploy_append () {
    install -d ${DEPLOY_DIR_IMAGE}

    if [ -z "${FSL_KERNEL_DEFCONFIG}" ] ; then
       fsl_defconfig='imx6solo_defconfig'
    else
       fsl_defconfig=${FSL_KERNEL_DEFCONFIG}
       # check that defconfig file exists
       if [ ! -e "${S}/arch/arm/configs/${FSL_KERNEL_DEFCONFIG}" ]; then
           fsl_defconfig='imx6solo_defconfig'
       fi
    fi

    install  arch/arm/boot/uImage ${DEPLOY_DIR_IMAGE}/uImage_$fsl_defconfig
    install  arch/arm/boot/zImage ${DEPLOY_DIR_IMAGE}/zImage_$fsl_defconfig
}
