#!/usr/bin/env bash
cd ../src
moonc .
cd ../../assets
moonc -t ../.non/src .
cd ../.non
java -cp libs/bcel.jar:libs/luaj.jar luajc -r -d core/build/classes/main src/