@echo off
mvn package

if "%errorlevel%" == "0" (
	echo Build Done
	exit /b 0
) else (
	echo Build has failed
	exit /b 1
)