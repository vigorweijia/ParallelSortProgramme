@echo off
fc order1.txt order2.txt
if errorlevel 1 pause
fc order1.txt order3.txt
if errorlevel 1 pause
fc order1.txt order4.txt
if errorlevel 1 pause
fc order1.txt order5.txt
if errorlevel 1 pause
fc order1.txt order6.txt
if errorlevel 1 pause
fc order1.txt order7.txt
if errorlevel 1 pause
fc order1.txt order8.txt
if errorlevel 1 pause
@echo order1-8 are all the same.
pause