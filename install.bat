@if "%DEBUG%" == "" @echo off
javac lib/*.java
jar cvfe bin/non.jar Main -C lib .
