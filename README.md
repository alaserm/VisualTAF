# ExlJS (the tool that does most of the work for you in data-driven test automation)
ExlJS is a powerful standalone GUI tool for rapid and super easy creation of **data-driven** test automation scripts. It's data-driven out of the box, no need to write code for fetching test data. ExlJS allows crafting of test cases in EXCEL by *copy/pasting* reusable instructions and adding reference to test data in another sheet in the same Excel file.<br/> It gives you great organizational structure and visibility for your test cases, built-in shortcuts make test case automation straight forward (top to bottom approach), smooth and **fun**, you will waste less brain energy on automation and have more energy for yourself and as a result feeling more **happy** in your life.
![](http://23.236.144.243/VisualTAFScreenshots/happy-family.jpg)

This tool is designed to enforce **test automation best practices** like reusable data-driven instructions, object repository.
- It's a **standalone GUI tool**, no need to remember  command line parameters :)
- it uses **Javascript**, which is much easier to use than Java or C# code, **no compilation required**.
- *Tons of built-in convenience functions for all types of interactions with web elements, tons of built-in verification points, you don't need to write any extra code.*
- *Do your automated test scripts fail because test data already exists in the system from previous automated test runs? With this tool you won't have this problem, it can automatically generate unique test data every time (no coding required).*
- **Parallel** test script execution in multiple browsers, instead of hours you can finish test runs in minutes.
- **No need** to download and install Node.js, Java, Selenium or other dependencies, the tool is self contained, just download this tool and you are all set, it works out of the box.
- Built-in **Object Repository** to keep all Web Elements of your tests in one place (test automation best practices for ease of maintenance in case web element selectors change later) 
- It supports parallel test exection on remote machines.
- It has sophisticated built-in functions to wait for page load in standard and SPA pages to make your test scripts rock stable.
- Automatically takes screeenshots upon test failure for easy defect reporting.
- Automatic **Enterprise Level Reporting** with drill down to step level (no need for you to write any code), you can see exactly what test data was used for each element and what button was clicked. You can mail this test execution report to project stakeholders for visibility, everybody can see what tests have been executed.
- **!!!New!!!** *Automatic code generation* - just fill in test data in datasheet and press **Ctrl+Shift+G** for the tool to auto generate code for you, this is the  **MAGIC hotkey**, it will create instruction code, repository with web elements and commands to to interact with them. This hotkey will also navigate you from Excel to exact instruction code and from instruction code to exact web element in object repository, so you don't need to strain your eyes and brain searching in code for instruction or web elements.
- **Save up to 70%** on your test automation development and maintenance time.
- And...It's **FREE** for personal and educational use. Free training is provided to everybody!

## Watch how I automate data driven test cases in a matter of minutes!!!
[![Video from zero to hero](http://23.236.144.243/VisualTAFScreenshots/youtubevideo.png)](https://youtu.be/rKnTu1Sx-0A)
![Automated test cases in Excel](http://23.236.144.243/VisualTAFScreenshots/crafted-test-cases-in-excel.png)


<!---	
[![Main Screen](http://23.236.144.243/VisualTAFScreenshots/overallcomponents4.png)](http://23.236.144.243/VisualTAFScreenshots/overallcomponents4.png)
-->
You can also see  test scripts developed by this tool in [this git repo](https://github.com/alaserm/DateParserAutomation)

# Installation
1. Download ExlJS.zip file from [here](http://23.236.144.243/VisualTAF/ExlJS.zip)
2. Extract content of zip file to some folder
3. Go to that folder and run **ExlJS.exe**

<!--
[![Main Screen](http://23.236.144.243/VisualTAFScreenshots/runtemplatecontextmenu.png)](http://23.236.144.243/VisualTAFScreenshots/runtemplatecontextmenu.png)
-->

*For technical support and questions contact **alaserm@yahoo.com**<br>
let me know if you have any questions or want a FREE training session via skype or zoom.*

# List of frequently used built-in functions
  **each function has documentation when you type its name in this tool editor**

## Page Element interaction functions
- clearText
- typeText
- select
- click
- clickAtXY
- moveToXY
- browserAlertBoxClick

## Verification functions, checkpoints
- assertCurrentPageUrl
- assertCurrentPageTitle
- assertObjectPresenceState
- assertObjectVisibilityState
- assertObjectEnabledState
- assertObjectSelectionState
- assertObjectText
- assertObjectAttribute
- assertObjectCss
- assertListSelection
- assertListContent
- assertBrowserAlertBoxText

## Wait functions
- sleep
- waitForStandardPageLoad
- waitForAngularJQueryJS
- waitForObjectToBecomeInvisible
- waitForObjectToBecomeVisible
- waitForObjectText


## Helper functions
- navigate
- findObject
- findRowInHtmlTable
- isObjectPresent
- isObjectVisible
- isObjectEnabled
- getText
- getValue
- getAttribute
- getCssValue
- switchToIframe
- switchBackToDefaultContent
- selectorAndDescription
- objectAndDescription
- reportStep
- debug
