cd /d %~dp0
del /f /s /q target
mvnw.cmd spring-boot:run
pause