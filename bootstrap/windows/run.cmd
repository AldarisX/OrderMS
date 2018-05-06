cd /d %~dp0
java -server -Xmx512M -XX:CompileThreshold=100 -jar OrderMS.jar -o start
pause