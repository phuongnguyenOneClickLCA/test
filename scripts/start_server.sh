#!/bin/bash
echo "deploying"
sudo chown -R yllapito:yllapito /home/maintenance/app.war
file="/home/maintenance/app.war"
command="/usr/bin/java"

if [ -f "$file" ]
then
  running=`ps ax | grep -v grep | grep $command | wc -l`

  if [ "$running" -gt 0 ]; then
     echo "restarting with Kill-PID process"
     PID=`ps aux|less|grep java|grep -v grep|awk {'print $2'}`
     kill -9 $PID
     rm -rf /opt/tomcat/webapps/app
     rm -rf /opt/tomcat/webapps/app.war
     mv $file /opt/tomcat/webapps/
     sleep 5
     /opt/tomcat/bin/startup.sh
  else
     rm -rf /opt/tomcat/webapps/app
     rm -rf /opt/tomcat/webapps/app.war
     mv $file /opt/tomcat/webapps/
     sleep 5
     /opt/tomcat/bin/startup.sh
  fi
else
  echo "Cannot start deployment script, because $file not found"
fi
sleep 10
