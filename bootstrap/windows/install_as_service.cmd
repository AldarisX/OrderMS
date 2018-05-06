@echo off

rem 设置程序名称
set SERVICE_EN_NAME=OrderMS
set SERVICE_CH_NAME=OrderMS

rem 设置程序依赖及程序入口类
set BASEDIR=%~dp0
set CLASSPATH=%BASEDIR%/OrderMS.jar


rem 设置prunsrv路径
set SRV=%BASEDIR%\daemon\prunsrv.exe

rem 设置日志路径及日志文件前缀
set LOGPATH=%BASEDIR%log\

rem 输出信息
echo SERVICE_NAME: %SERVICE_EN_NAME%
echo prunsrv path: %SRV%

rem 安装
"%SRV%" //IS//"%SERVICE_EN_NAME%" --DisplayName="%SERVICE_CH_NAME%" --Classpath="%CLASSPATH%" --Install="%SRV%" --JvmMs=256 --JvmMx=512 --Startup=auto --JvmOptions=-server --StartPath="%BASEDIR%" --StartMode=java --StartClass=com.everygamer.OrderMsApplication --StopPath="%BASEDIR%" --StopMode=java --StopClass=com.everygamer.OrderMsApplication --StopMethod=doStop --LogPath="%LOGPATH%" --StdOutput=auto --StdError=auto

pause
:end