#!/bin/sh
#################################################
#文件内容： Mysql loaddata导入文件
#作   者： xiamh
#时   间： 20180324
#参   数：
#################################################


#请求参数获取
IP=${1}
PORT=${2}
USERNAME=${3}
PASSWORD=${4}
DBNAME=${5}              	#数据库名称                       	
TABLENAME=${6}				#数据库中表的名称
FILEPATH=${7}				#需要导入的文件
FIELDS_TERMINATED=${8}		#分隔符
ENCLOSED_BY=${9}				#字段引号
LINES_TERMINATED=${10}		#换行符
COLUMNS=${11}
SPEHANDCOLUMNS=${12}
FILEHEADLINE=${13}
ENCODE=${14}

echo "********************************"
echo $*
#echo $@
echo "********************************"


#打印开始时间
echo  "***** 脚本执行开始时间 >>> " `date "+%G-%m-%d %H:%M:%S"`;

#导入分隔符文件到数据库文件
mysql -h${IP} -P${PORT} -u${USERNAME} -p${PASSWORD} ${DBNAME} <<EOF
	
	USE ${DBNAME};
	#TRUNCATE table ${TABLENAME};

	LOAD DATA LOCAL INFILE '${FILEPATH}' 
	IGNORE INTO TABLE ${TABLENAME}
	#REPLACE INTO TABLE ${TABLENAME}
	CHARACTER SET ${ENCODE}
	FIELDS TERMINATED BY '${FIELDS_TERMINATED}'	
	ENCLOSED BY '${9}'
	ESCAPED BY ''                               #将文件中的反斜杠当作普通字符

	LINES TERMINATED BY '${10}'
	IGNORE ${FILEHEADLINE} LINES
	
	${COLUMNS}
	
	${SPEHANDCOLUMNS}
	
	#字段不做特殊处理，直接插入
	#(t1,t2,t3,t4,t5,t6,t7,t8,t9)				#t1-t10是表字段名，如果与文件中的字段顺序不同，调整这里的字段顺序即可
	
	#字段做MD5，再插入（下面这种方式适合对文件中的字段需要做特殊处理）
	#(t1,t2,t3,t4,@t5,@t6,@t7,@t8,t9)
	#SET t10 = md5(@t5|@t6|@t7|@t8),
	#	 t5 = @t5,
	#	 t6 = @t6,
	#	 t7 = @t7,
	#	 t8 = @t8
	;
	
	SHOW WARNINGS;
	
EOF
if [ $? -eq 0 ]
then
	echo "***** 脚本执行成功 >>> " `date "+%G-%m-%d %H:%M:%S"`;
	exit 0;		#执行成功
else
    echo "***** 脚本执行失败 >>> " `date "+%G-%m-%d %H:%M:%S"`;	
	exit 1;	#执行失败	
fi

