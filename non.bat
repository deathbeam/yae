@if "%DEBUG%" == "" @echo off
call java -classpath non.jar launcher
cd .non
call gradlew "%1"
cd ../