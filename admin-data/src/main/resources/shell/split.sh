#!/bin/sh
#################################################
#文件内容： 按行分割文件
#作   者： xiamh
#时   间： 20180324
#参   数： 1－分割文件路径＋名称，2-按多少行分割，3-切割文件名称
#   linux : split -l ${2} ${1} -d -a 4 ${3}
#   mac   : split -l ${2} ${1} ${3}
#################################################

echo "********************************"
echo $*
echo "********************************"

PARAMS1=${1}
PARAMS2=${2}
PARAMS3=${3}

split -l ${PARAMS2} ${PARAMS1} ${PARAMS3}
if [ $? -eq 0 ]
then
	exit 0;		#执行成功
else
	exit 1;	#执行失败
fi

