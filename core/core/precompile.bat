@if "%DEBUG%" == "" @echo off
setlocal
cd ../../assets
moonc -l .
moonc -t ../.non/lua .
cd ../.non
java -cp libs/bcel.jar;libs/luaj.jar luajc -r -d core/build/classes/main lua/
endlocal