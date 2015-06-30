@if "%DEBUG%" == "" @echo off
cd src
moonc -t build/lua .
cd ../
java -cp core/libs/bcel.jar;core/libs/luaj.jar luajc -r -d src/build/classes src/build/lua
javac bin/launcher/*.java
jar cvfe bin/non.jar Main -C bin/launcher/ .
jar fu bin/non.jar core res src VERSION