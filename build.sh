#!/usr/bin/env bash
javac non/launcher/*.java
jar cvfe bin/non.jar Main -C non/launcher/ .
jar fu bin/non.jar non/backend/* non/resources/* non/src/* non/VERSION
chmod 777 bin/non.jar