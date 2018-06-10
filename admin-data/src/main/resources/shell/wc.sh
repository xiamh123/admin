#!/bin/sh
#################################################
#文件内容： 统计文件行数
#作   者： xiamh
#时   间： 20180309
#################################################

#请求参数获取
FILE_PATH_NAME=${1}

#打印开始时间
#echo  "***** 脚本执行开始时间 >>> " `date "+%G-%m-%d %H:%M:%S"`;


wc -l ${1} | awk '{print $1}'
if [ $? -eq 0 ]
then
	#echo "***** 脚本执行成功 >>> " `date "+%G-%m-%d %H:%M:%S"`;
	exit 0;		#执行成功
else
    #echo "***** 脚本执行失败 >>> " `date "+%G-%m-%d %H:%M:%S"`;	
	exit 1;	#执行失败	
fi
