@if "%DEBUG%" == "" @echo off
setlocal
cd build\moon
call moonc -t ..\lua .
cd ..\..\
call java -cp ..\libs\bcel.jar;..\libs\luaj.jar luajc -r -d build\classes\main build\lua\
endlocal