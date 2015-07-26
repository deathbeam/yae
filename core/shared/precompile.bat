@if "%DEBUG%" == "" @echo off
setlocal
cd ..\..\assets
call moonc -t ..\.non\shared\build\lua .
cd ..\.non
call java -cp libs\bcel.jar;libs\luaj.jar luajc -r -d shared\build\classes\main shared\build\lua\
endlocal
