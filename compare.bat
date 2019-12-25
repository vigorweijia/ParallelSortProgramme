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