# ExcelTAF
ExcelTAF is a simple, but powerful tool for rapid and easy creation of maintainable test automation scripts.
ExcelTAF allows assembly of test cases in EXCEL by **copy/pasting** keywords in Excel sheet and feeding them with test data from another sheets in the same workbook.
Also this tool allows you to cut your test execution time **tenfold** by running your tests in parallel in multiple browsers at the same time.\
This tool is designed to enforce **test automation best practices**, so there is no "record and playback" functionality or other features that are not used by *REAL life test automation experts* as they make scripts unmaintainable.
- It supports *Selenium Grid* and local test execution.
- It supports Angular and JQuery ajax waits.
- Automatically takes screeenshots upon test failure for easy bug tracking.
- CI/CD integration.
- **!!!New!!!** *API testing* is available now.
- **Save up to 70%** on your test automation development and maintenance time.


[![Main Screen](http://23.236.144.243/VisualTAFScreenshots/overallcomponents3.png)](http://23.236.144.243/VisualTAFScreenshots/overallcomponents3.png)


# Installation
1. You would need Chrome Browser if you want to run included demo tests (https://www.google.com/chrome/).
2. Download ExcelTAF.zip file (http://23.236.144.243/VisualTAF/ExcelTAF.zip).
3. Unzip content of zip file to some folder.

# Running
1. In unzipped folder run ExcelTAF.exe
5. Right click on "Regression" node in left panel and choose "Run selected test set" option to run built-in demo scripts
6. Select number of parallel threads for execution, demo has 3 Excel files under Regression set, so if we specify 3 threads, then thread 1 will pick "Demo1.xlsx", thread 2 will pick "Demo2.xlsx", thread 3 will pick "Demo3.xlsx" for execution.
7. Test Execution will start with multiple browser windows (parallel execution) 
8. After test run completes you will see comprehensive test execution report

# Developing test cases
Doubleclick on "Demo1.xlsx" node to open this file in Excel and see it's structure.
It's quite simple, in each Excel file you need to have a sheet called "Instructions", this is where you assemble test cases. Instructions sheet has "TCName", "Keyword", "Input", "ExpectedResult", "Comment" columns. In "TCName" column you give name to your test case, that's how test case starts, then in the next lines in "Keywords" column you fill in instructions and attach data to them in "Input" column, then you end test case by typing "END" in "TCNAME" column. It's better pictured in diagram below

[![Main Screen](http://23.236.144.243/VisualTAFScreenshots/CreatingTestCasesInExcel.png)](http://23.236.144.243/VisualTAFScreenshots/CreatingTestCasesInExcel.png)

Now let's see how you add on-page objects to your scripts, in the left panel of the tool
expand **"Page Objects"** folder, it acts as an Object Repository, and doubleclick on any file there to open it and see its structure.
```javascript
//LoginPage.js
//Encapsulates page objects of Login page

var LoginPage = 
{
    username : css("input#email"),
    password : css("input#password"),
    goButton : css("button.btn-primary"),
    
    ErrorMessage : css("span.help-block"),
    LoggedUserArea : css("ul.nav li.dropdown")
}

LoginPage.url = "http://23.236.144.243/TicketsAUT/public/login";



//Then in your code you can  use on-page objects like typeText(LoginPage.username,"text") or click(LoginPage.goButton)
//Notice that all object recognition properties like "input#email" stored only in one file for maintainability

```
this is how you keep your object recognition properties in one place, so if developers change object in AUT (Application Under Test) then you can easily update it in just one place in your scripts, this is automation best practice for script maintainability.


So once you added on-page objects you can easily create keywords (keyword is a reusable function to perform common workflows in your application) in **"Keywords"** folder in the left panel, keyword can be reused many times in Excel, you just feed it with different data rows for different test cases; here is example of Login keyword

```javascript
var Login = {};
Login.login = function(params) {
	
    navigate(LoginPage.url);
    waitForAngularJQueryJS();
    
    typeText(LoginPage.username, params.get("email") );
    typeText(LoginPage.password, params.get("password") );
    
    click(LoginPage.goButton);
}
```



Become an early distributor and earn millions with this hot new product ;)\
For any question contact **alaserm@yahoo.com**

