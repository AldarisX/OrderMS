@echo off
PUSHD %~DP0

Rd "%WinDir%\system32\test_permissions" >NUL 2>NUL  
Md "%WinDir%\System32\test_permissions" 2>NUL||(Echo ��ʹ���Ҽ�����Ա������У�&&Pause >nul&&Exit)  
Rd "%WinDir%\System32\test_permissions" 2>NUL  

schtasks  /create  /tn  OrderMSTask /tr  "%~dp0run.cmd" /DELAY 0000:15 /sc  ONLOGON

pause