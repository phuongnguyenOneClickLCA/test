#!/bin/bash

PID=`ps aux|less|grep grails|grep -v grep|awk '{print $2'}`

if [ -z  "$PID" ]; then
echo grails-app has been stopped or is not running
else
echo .. killing grails-app with process id $PID
kill -9 $PID
echo grails app has been killed
fi
