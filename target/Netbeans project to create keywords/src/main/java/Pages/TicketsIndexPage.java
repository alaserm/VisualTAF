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
public class TicketsIndexPage {
    
//    @FindBy(css="input#subject")
//    public   WebElement subjectTextBox;
//
//    @FindBy(css="textarea#description")
//    public   WebElement descriptionTextBox;
//    
//    @FindBy(css="input.btn-primary[type='submit']")
//    public   WebElement createButton;

    public static void navigate(WebDriver webDriver) {
        webDriver.get("http://23.236.144.243/TodosAUT/public/tickets");
    }
    
    private void bindControls( WebDriver webDriver) {
        PageFactory.initElements(webDriver, this );
    }

    public TicketsIndexPage(WebDriver dr) {
        bindControls(dr);
    }
    
    
}
