# ExlJS
ExlJS is a simple, but powerful tool for rapid and easy creation of **MAINTAINABLE** keyword and data driven test automation scripts.
ExlJS allows assembly of test cases in EXCEL by **copy/pasting** keywords in Excel sheet and feeding them with test data from another sheets in the same workbook.
Also this tool allows you to cut your test execution time **tenfold** by running your tests in parallel in multiple browsers at the same time.\
This tool is designed to enforce **test automation best practices**, so there is no "record and playback" functionality or other features that are not used by *REAL life test automation experts* as they make scripts unmaintainable.
- With this tool you can use Selenium **from JavaScript**, which is much easier than learning complex Java or C#.
- **No need** to install Node.js, Java, Selenium or other dependencies, the tool is self contained, just download this tool and you are all set.
- It supports test exection on *Selenium Grid nodes* and/or local machine.
- It supports Angular and JQuery ajax waits.
- Automatically takes screeenshots upon test failure for easy bug tracking.
- CI/CD integration: ExlJS exposes REST API that you can call from CI tools, like Jenkins, to start tests.
-  Enterprise level reporting with drill down to step level, charts, screenshots and more.
- **!!!New!!!** *API testing* is available now.
- **!!!New!!!** *Automatic code generation* - fill in test data in datasheet and press Ctrl+G for the tool to auto generate JS code for you that will interact with your datasheet (creates Page Object with web elements and keyword to interact with them).
- **Save up to 70%** on your test automation development and maintenance time.

## Video Tutorial - From Zero to Hero in Test Automation in 20 minutes!!!
[![Video from zero to hero](http://23.236.144.243/VisualTAFScreenshots/youtubevideo.png)](https://www.youtube.com/watch?v=f3dMvVQ7NpA)

# Comparison to Selenium
1. ## Data-driving keywords<br/>
	**Selenium:**<br/>
	Either hardcoded test data or need to write JDBC code to fetch data from data storage.
	```javascript
		//hardcoded values
		driver.findElement(By.css("#FirstName")).sendKeys("George");
		driver.findElement(By.css("#LastName")).sendKeys("Orwell");
		driver.findElement(By.css("#Address")).sendKeys("22 Main st");
	```		
	**ExlJS:**<br/>
	Data seemlessly driven to keyword from Excel rows. It's super easy to create and maintain test data in Excel.
	[![Data driving compared](http://23.236.144.243/VisualTAFScreenshots/DataDrivingCompared2.png)](http://23.236.144.243/VisualTAFScreenshots/DataDrivingCompared2.png) <br/>
	**!!!New!!!** *Automatic code generation* - fill in test data in datasheet and press Ctrl+G for the tool to auto generate JS code for you that will interact with your datasheet (creates Page Object with web elements and keyword to interact with them).



2. ## Waiting for Ajax, JQuery, Angualar, React page full load<br>
	**Selenium:**<br/>
	```javascript
		//OMG!
		FluentWait<By> fluentWait = new FluentWait<By>(By.tagName("TEXTAREA"));
		fluentWait.pollingEvery(100, TimeUnit.MILLISECONDS);
		fluentWait.withTimeout(1000, TimeUnit.MILLISECONDS);
		fluentWait.until(new Predicate<By>() {
		    public boolean apply(By by) {
			try {
			    return browser.findElement(by).isDisplayed();
			} catch (NoSuchElementException ex) {
			    return false;
			}
		    }
		});
	```		
	**ExlJS:**<br/>
	```javascript
		waitForAngularJQueryJS();
		//OR
		waitForObjectToBecomeVisible(UserRegistratioPage.textArea, 30);
	```
3. ## Filling form data<br/>
	**Selenium:**<br/>
	```javascript
		void fillUserRegistrationForm(String firstName, String lastName, String address)
		{
			if( firstName!= null )
				driver.findElement(By.css("#FirstName")).sendKeys(firstName);
			if( lastName!= null )
				driver.findElement(By.css("#LastName")).sendKeys(lastName);
			if( address!= null )
				driver.findElement(By.css("#Address")).sendKeys(address);
				
		}
	```		
	**ExlJS:**<br/>
	```javascript
		function fillUserRegistrationForm(params)
		{
			//auto searches for element and fills in text straight from Excel data row
			typeText(UserRegistratioPage.firstName, params.get("FirstName") );
			typeText(UserRegistratioPage.lastName, params.get("LastName") );
			typeText(UserRegistratioPage.address, params.get("Address") );
				
		}
	```


4. ## Checkpoints/element verification <br/>
	**Selenium:**</br>
	```javascript
		if( textToVerify!= null) 
			assertTrue( driver.findElement(By.id("CustomerId")).getText(), textToVerify) );
	```		
	**ExlJS:**<br/>
	```javascript
		assertObjectText( textToVerify, UserRegistratioPage.customerId );
	```		
	
5. ## Reporting <br>
	**Selenium:**<br/>
	Virtually None, except<br/>
	```javascript
		System.out.println("TC130 started"); 
		System.out.println("Clicked Submit button"); 
		...
		System.out.println("TC130 finished"); 
	```
		
	**ExlJS:**<br/>
	Comprehensive drill-down HTML report is generated **automatically** where you clearly see which test cases passed/failed WITH step-by-step action performed by each test case and screenshots.
	[![Reporting compared](http://23.236.144.243/VisualTAFScreenshots/ReportingCompared2.png)](http://23.236.144.243/VisualTAFScreenshots/ReportingCompared2.png)
	

5.  ## Parallel test execution <br>
	**Selenium:**</br>
	You would need to create new Threads by yourself in Java code
	```javascript
		new Thread() {

		    @Override
		    public void run() {
			driver1 = new ChromeDriver();
		    }

		}.start();
		...
		new Thread() {

		    @Override
		    public void run() {
			driver2 = new ChromeDriver();
		    }

		}.start();
		
	```		
	**ExlJS:**<br/>
	Just select # of parallel threads and browser type from GUI when you start test execution, no need to code anything, test scripts are not dependant on browser type or threads.
	[![Parallel execution compared](http://23.236.144.243/VisualTAFScreenshots//threadandbrowsers2.png)](http://23.236.144.243/VisualTAFScreenshots/threadandbrowsers2.png)

6.  ## Object Identifiers <br>
	**Selenium:**</br>
	Element identifiers are hardcoded in many places in Selenium code.
	```javascript
		//test1
		driver.findElement(By.css("#LastName"))
		...
		//test2
		driver.findElement(By.css("#LastName"))
	```		
	**ExlJS:**<br/>
	Element identifiers for each page are encapsulated by "xxxxxPage" object in one place for easy maintenance later. This  test automation best practice is enforced by ExlJS tool.
	```javascript
		var LoginPage = 
		{
		    email : selectorAndDescription("input#email", "Email address field"),
		    password : selectorAndDescription("input#password", "Password field"),
		    LoginButton : selectorAndDescription("button.btn-primary", "Login Button"),

		    ErrorMessage : selectorAndDescription("span.help-block", "Error Message Area"),
		    
		    url : "http://23.236.144.243/TicketsAUT/public/login"
		}

		
	```

7.  ## Deployment and learning curve <br>
	**Selenium:**</br>
	Need to download Java, Eclipse or Netbeans IDE, selenium jars, chromedriver.exe, compile tests to jar.<br/>
	Learning curve: need to learn Java classes, add strong types to every parameter, learn JDBC for data feed.
	
	**ExlJS:**<br/>
	Just unpack downloaded zip file, everything is included, and start running tests immediately, no compilation required!<br/>
	Learning curve: 1 day max to learn how to create Javascript functions and how to copy/paste rows in Excel :-), that's it.<br/>
	ExlJS exposes REST API for automatic test execution, you can easily integrate it with CI tools like Jenkins.
<!---	
[![Main Screen](http://23.236.144.243/VisualTAFScreenshots/overallcomponents4.png)](http://23.236.144.243/VisualTAFScreenshots/overallcomponents4.png)
-->

# Installation
1. Download ExlJS.zip file (http://23.236.144.243/VisualTAF/ExlJS.zip).
2. Extract content of zip file and run **ExlJS.exe**
3. It comes with fully functional demo test scripts which can serve as a starting template for your project, you can run them from right-click context menu (for local tests Google Chrome is required).

[![Main Screen](http://23.236.144.243/VisualTAFScreenshots/runtemplatecontextmenu.png)](http://23.236.144.243/VisualTAFScreenshots/runtemplatecontextmenu.png)

*For technical support and questions contact **alaserm@yahoo.com**<br>
I can also provide affordable turn-key automation services for your organisation.*

