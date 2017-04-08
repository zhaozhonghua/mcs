#!/bin/sh

#变量定义
profile_array=("dev" "product")
ip_array=("apitest.haierzhongyou.com" "api.haierzhongyou.com")
remote_cmd="/home/dev/api/remote.sh"
port="22"

#输入参数 p 或者 product 为发布正式版
i=0
if [ x$1 != x ]
then
    if [ "$1" == "p" ] || [ "$1" == "product" ] ; then
        i=1
    fi
fi

echo "ssh -t -p $port dev@${ip_array[$i]} \"sh $remote_cmd ${profile_array[$i]}\""

ssh -t -p $port dev@${ip_array[$i]} "sh $remote_cmd ${profile_array[$i]}"

