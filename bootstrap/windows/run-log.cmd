cd /d %~dp0
javaw -server -Xmx512M -XX:CompileThreshold=100 -jar OrderMS.jar -o start
exit