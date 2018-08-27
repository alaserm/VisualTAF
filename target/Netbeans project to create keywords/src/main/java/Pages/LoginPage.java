/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Pages;

import Keywords.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 *
 * @author Andrei
 */
public class LoginPage {
    
    @FindBy(css="input#email")
    public   WebElement username;

    @FindBy(css="input#password")
    public   WebElement password;
    
    @FindBy(css="button.btn-primary")
    public   WebElement goButton;

    public static void navigate(WebDriver webDriver) {
        webDriver.get("http://23.236.144.243/TodosAUT/public/login");
    }
    
    private void bindControls( WebDriver webDriver) {
        PageFactory.initElements(webDriver, this );
    }

    public LoginPage(WebDriver dr) {
        bindControls(dr);
    }
    
    public static void main(String[] args) {
        
        WebDriver webDriver = new ChromeDriver();
        
        
        
        LoginPage.navigate(webDriver);
        LoginPage loginPage = new LoginPage(webDriver );
        
        
        loginPage.username.sendKeys("test");
        loginPage.password.sendKeys("test123");
        loginPage.goButton.click();    }

}
