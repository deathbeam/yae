@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  No nonsense startup script for Windows
@rem
@rem ##########################################################################

cd wrapper
if [%2]==[] goto param
call gradlew "%1" -Parg="%2"
goto end
:param
call gradlew "%1"
:end
cd ../
