echo on

taskkill /f /im chrome.exe
taskkill /f /im chromedriver.exe

rem Path > zpath.txt

rem java -version  > zjava.txt
mvn test  > zz.txt

pause