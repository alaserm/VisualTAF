# ExcelTAF
ExcelTAF is a  **T**est **A**utomation **F**ramework for rapid and easy test case creation and execution **in EXCEL**.
ExcelTAF allows assembly of test cases in EXCEL by **just copy/pasting** keywords on one sheet and linking them to test data in another sheet.
Also this framework allows you to cut your test execution time **tenfold** by running your tests in parallel in multiple browsers at the same time.

[![Main Screen](http://23.236.144.243/VisualTAFScreenshots/overallcomponents3.png)](http://23.236.144.243/VisualTAFScreenshots/overallcomponents3.png)


# Installation
1. You would need Chrome Browser to run demo tests (https://www.google.com/chrome/).
2. Download ExcelTAF.zip file (https://github.com/alaserm/VisualTAF/raw/master/target/ExcelTAF.zip).
3. Unzip content of zip file to some folder.

# Running
1. In unzipped folder run ExcelTAF.exe
5. Right click on "Regression" node in left panel and choose "Run selected test set" option
6. Select number of parallel threads for execution, demo has 3 Excel files under Regression set, so if we specify 3 threads, then thread 1 will pick "Demo1.xlsx", thread 2 will pick "Demo2.xlsx", thread 3 will pick "Demo3.xlsx" for execution.
7. Test Execution will start with multiple browser windows (parallel execution) 
8. After test run completes you will see comprehensive test execution report

# Developing test cases
- Doubleclick on "Demo.xlsx" node to open this file in Excel and assemble instructions

   you can create new Excel file to group test cases and then drag & drop this file into Test Set node
- Expand "Keywords" node and doubleclick on any "**.js**" file to see how instructions are implemented in JS
    
    you can create new JS file to group keywords and then drag & drop this file into Keywords node
- Expand "Page Object" node and doubleclick on any "**.js**" file to see how page object binding works
- have question? contact **alaserm@yahoo.com**

