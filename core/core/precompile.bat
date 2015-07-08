@if "%DEBUG%" == "" @echo off
setlocal
cd ..\..\assets
call moonc -t ..\.non\lua .
cd ..\.non
call java -cp libs\bcel.jar;libs\luaj.jar luajc -r -d core\build\classes\main lua\
endlocal