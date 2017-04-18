#!/bin/sh

echo "cd /home/dev/api"
cd /home/dev/api
if ! [ $? -eq 0 ]; then
 echo "error: cd /home/dev/api"
 exit 1
fi

BRANCH_NAME=master
if [ x$2 != x ]
then
    BRANCH_NAME=$2
fi

echo "git pull origin $BRANCH_NAME"
git pull origin $BRANCH_NAME
if ! [ $? -eq 0 ]; then
 echo "error: git pull origin $BRANCH_NAME"
 exit 2
fi

profile=""
if [ x$1 != x ]
then
    if [ "$1" == "p" ] || [ "$1" == "product" ] ; then
        profile="-P product"
    fi
fi

echo "mvn clean package $profile"
mvn clean package $profile
if ! [ $? -eq 0 ]; then
 echo "error: mvn clean package $profile"
 exit 3
fi

echo "cp /home/dev/mcs/target/api.war /opt/pub/tomcat/tomcat-8.0.36-web/webapps/ROOT.war"
cp /home/dev/mcs/target/mcs.war /opt/pub/tomcat/tomcat-8.0.36-web/webapps/ROOT.war
if ! [ $? -eq 0 ]; then
 echo "error: cp war"
 exit 4
fi

echo "supervisorctl -c /etc/supervisor/supervisor.conf restart api"
supervisorctl -c /etc/supervisor/supervisor.conf restart api
if ! [ $? -eq 0 ]; then
 echo "error: restart tomcat"
 exit 5
fi

exit 0