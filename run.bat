@echo off
if "%~1"=="" (
  "C:\Program Files\Java\jdk-23\bin\java.exe" -jar url-checker.jar
) else (
  "C:\Program Files\Java\jdk-23\bin\java.exe" -jar url-checker.jar %1
)
