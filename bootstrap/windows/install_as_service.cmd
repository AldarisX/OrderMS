@echo off

rem ���ó�������
set SERVICE_EN_NAME=OrderMS
set SERVICE_CH_NAME=OrderMS

rem ���ó������������������
set BASEDIR=%~dp0
set CLASSPATH=%BASEDIR%/OrderMS.jar


rem ����prunsrv·��
set SRV=%BASEDIR%\daemon\prunsrv.exe

rem ������־·������־�ļ�ǰ׺
set LOGPATH=%BASEDIR%log\

rem �����Ϣ
echo SERVICE_NAME: %SERVICE_EN_NAME%
echo prunsrv path: %SRV%

rem ��װ
"%SRV%" //IS//"%SERVICE_EN_NAME%" --DisplayName="%SERVICE_CH_NAME%" --Classpath="%CLASSPATH%" --Install="%SRV%" --JvmMs=256 --JvmMx=512 --Startup=auto --JvmOptions=-server --StartPath="%BASEDIR%" --StartMode=java --StartClass=com.everygamer.OrderMsApplication --StopPath="%BASEDIR%" --StopMode=java --StopClass=com.everygamer.OrderMsApplication --StopMethod=doStop --LogPath="%LOGPATH%" --StdOutput=auto --StdError=auto

pause
:end