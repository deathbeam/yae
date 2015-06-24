#!/usr/bin/env bash
javac launcher/*.java
jar cvfe bin/non.jar Main -C launcher/ .
jar fu bin/non.jar backend resources src VERSION
chmod 777 bin/non.jar