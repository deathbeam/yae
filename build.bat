@if "%DEBUG%" == "" @echo off
setlocal
cd src
call moonc -t build\lua .
cd ../
call java -cp core\libs\bcel.jar;core\libs\luaj.jar luajc -r -d src\build\classes src\build\lua
endlocal