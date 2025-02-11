@echo off
setlocal enabledelayedexpansion


set "work_dir=D:\Test_FrameWork"
set "lib=%work_dir%\lib"
set "src=%work_dir%\src"
set "web=%work_dir%\web"
set "web_xml=%work_dir%\web\WEB-INF\web.xml"
set "temp=D:\deploiment\Temporaire\dossier"

set "war_name=FrontController"
set "web_apps=C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps"



@REM --- CREATE TEMP FOLDER ---
:: Delete temp folder if it exists
echo * Deleting temp folder...
if exist "%temp%" (
    rd /s /q "%temp%"
)

:: Create temp folder
echo * Re-Creating temp folder...
mkdir "%temp%\WEB-INF\lib"
mkdir "%temp%\WEB-INF\classes"

:: Copy web content to temp folder
if exist "%web%" (
    echo * Copying web content to temp folder...
    xcopy /s /y "%web%\*.*" "%temp%"
)

:: Copy web.xml to temp/WEB-INF
echo * Copying web.xml to temp/WEB-INF...
xcopy /y "%web_xml%" "%temp%\WEB-INF"

:: Copy .jar files in lib to temp/WEB-INF/lib
echo * Copying .jar files in lib to temp/WEB-INF/lib...
xcopy /s /y "%lib%\*.jar" "%temp%\WEB-INF\lib"

echo Done, temp folder created.
echo ---


@REM --- COMPILATION JAVA ---
echo * Compiling java files...
:: Compilation of .java files in src with the following options
:: Note: Make sure the path to the Java compiler (javac) is correctly configured in your PATH environment variable.
:: Create a list of all .java files in the src directory and its subdirectories
dir /s /B "%src%\*.java" > sources.txt
if not exist sources.txt (
    echo No .java files found in the src directory.
    exit /b 1
)

:: Create a temporary file to store the paths of .jar files
dir /s /B "%lib%\*.jar" > libs.txt

:: Build the classpath
set "classpath="
for /F "delims=" %%i in (libs.txt) do set "classpath=!classpath!.;%%i;"

:: Check for the existence of javac
where javac >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: javac not found in the PATH.
    exit /b 1
)

:: Execute the compilation command
javac -g -d "%temp%\WEB-INF\classes" -cp "%classpath%" @sources.txt

:: Delete sources.txt and libs.txt files after compilation
del sources.txt
del libs.txt

echo Done, java files compiled.
echo ---


@REM --- CREATE WAR FILE ---
echo * Creating "%war_name%.war" file...
:: Create a .war file named [war_name].war from the temp folder and its contents in the work_dir folder
cd "%temp%"
jar cf "%work_dir%\%war_name%.war" *
cd %work_dir%

echo Done, "%war_name%.war" file created.



@REM --- DEPLOY WAR FILE ---
echo * Deploying "%war_name%.war" file...
:: Deploy the .war file to the Tomcat webapps directory
copy /y "%work_dir%\%war_name%.war" "%web_apps%"
echo Done, "%war_name%.war" file deployed.

:: Delete the .war file in the work_dir after deployment
del /f /q "%work_dir%\%war_name%.war"


echo ---
echo Deployment completed successfully.
echo `(^^_^^)`