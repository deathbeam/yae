@if "%DEBUG%" == "" @echo off
javac non/launcher/*.java
jar cvfe bin/non.jar Main -C non/launcher/ .
jar fu bin/non.jar non/backend/* non/resources/* non/src/* non/VERSION