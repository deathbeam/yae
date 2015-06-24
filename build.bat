@if "%DEBUG%" == "" @echo off
javac bin/launcher/*.java
jar cvfe bin/non.jar Main -C bin/launcher/ .
jar fu bin/non.jar core resources src VERSION