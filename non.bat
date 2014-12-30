@if "%DEBUG%" == "" @echo off
call java -classpath non.jar launcher.Main
cd .non
call gradlew "%1"
cd ../