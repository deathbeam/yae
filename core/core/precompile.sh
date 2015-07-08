#!/usr/bin/env bash
cd ../../assets
moonc -t ../.non/lua .
cd ../.non
java -cp libs/bcel.jar:libs/luaj.jar luajc -r -d core/build/classes/main lua/