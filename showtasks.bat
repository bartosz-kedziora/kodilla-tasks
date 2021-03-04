call runcrud.bat
if "%ERRORLEVEL%" == "0" goto show
echo.
echo runcrud.bat has errors - breaking work
goto fail

:show
start chrome http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto :end
echo.
echo start web browser has errors - breaking work


:fail
echo.
echo There were errors

:end
echo.
echo showtasks.bat is finished.