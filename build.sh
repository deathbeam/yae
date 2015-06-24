#!/usr/bin/env bash
javac bin/launcher/*.java
jar cvfe bin/non.jar Main -C bin/launcher/ .
jar fu bin/non.jar core resources src VERSION
chmod 777 bin/non.jar