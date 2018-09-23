# ExcelTAF 
ExcelTAF is a hybrid test automation framework for rapid and easy test case creation and execution in EXCEL.
Unlike other tools where you need complex XML configuration, VisualTAF allows creation of test cases in EXCEL by just copy/pasting instructions on one sheet and linking them to test data in another sheet.
Also this framework allows you to cut your test execution time tenfold by running tests in parallel in 10 different browsers.

[![Main Screen](http://23.236.144.243/VisualTAFScreenshots/excellinkingtoanothersheet.png)](http://23.236.144.243/VisualTAFScreenshots/excellinkingtoanothersheet.png)

[![Main Screen](http://23.236.144.243/VisualTAFScreenshots/mainappwindows.png)](http://23.236.144.243/VisualTAFScreenshots/mainappwindows.png)

# Installation
1. Install JRE 8 (http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) and Chrome browser
2. Download & Unzip content of /target/VisualTAF.zip file into some folder

# Running
1. Start VisualTAF.exe
2. Expand "Test Sets" tree node in left panel
3. Drag and drop "Demo.xlsx" file from extracted folder to "Regerssion" node
4. Drag and drop "TicketsAUT-Keywords.jar" to Dependencies node
5. Right click on "Regression" tree node and choose "Run selected test set" option
6. Select number of parallel threads for execution
7. Test Execution will start in Chrome Browser(s)
8. After execution you will see comprehensive test execution report

# Developing test cases
- Open "Demo.xlsx" file in Excel, play with it
- Check out this Youtube video for step by step instructions https://www.youtube.com/watch?v=OZywY-ZRmQM&t=9s 
- need support, contact alaserm@yahoo.com
