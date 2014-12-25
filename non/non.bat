@if "%DEBUG%" == "" @echo off
call java -classpath non.jar Main
cd .non
call gradlew "%1"
cd ../