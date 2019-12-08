@echo off
cd target
java -jar volunteersservice-1.0-SNAPSHOT.jar
exit /b %errorlevel%