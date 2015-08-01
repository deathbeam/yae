@if "%DEBUG%" == "" @echo off
setlocal
cd build\moon
call moonc -t ..\lua .
cd ..\..\
call java -cp libs\bcel.jar;libs\luaj.jar luajc -r -d shared\build\classes\main shared\build\lua\
endlocal