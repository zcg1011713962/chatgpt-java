#!/bin/sh
JAVA_OPTS="-server -Dfile.encoding=UTF-8 -Xms300m -Xmx1g -Xmn500m  -DappPath=$(pwd) -DappName=mag-app -XX:+UseParallelGC -XX:+UseParallelOldGC -XX:ParallelGCThreads=8 -XX:+PrintGCDetails -XX:+PrintGCCause -Xloggc:./logs/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=100 -XX:GCLogFileSize=20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./dump/"
nohup java  $JAVA_OPTS -cp .:resources:./lib/* com.chat.Main >/dev/null 2>&1 &

