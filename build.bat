@if "%DEBUG%" == "" @echo off
javac non/launcher/*.java
jar cvfe bin/non.jar Main -C launcher/ .
jar fu bin/non.jar backend resources src VERSION