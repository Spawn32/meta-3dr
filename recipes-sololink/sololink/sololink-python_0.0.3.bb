SUMMARY = "sololink-python"
HOMEPAGE = "https://github.com/Spawn32/sololink-python"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://requirements.txt;md5=dc90368fff309dbbef652a1fab32f191"

PROVIDES += "${PN}_${PV}"

#SRCREV = "f89466ccd7addae49cf800fdf5c67ed6bdff47d6"
SRCREV = "master"
SRC_URI = "git://github.com/Spawn32/sololink-python/"

S = "${WORKDIR}/git"

inherit setuptools
