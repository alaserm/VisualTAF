/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Keywords;

import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Andrei
 */
public class Util {
    
    public static  ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    
    public String startLocalWebDriver(HashMap<String,String> params) {
        
        
        webDriver.set( new ChromeDriver() );
        
        
        return "PASS";
    }
    
    public void closeWebDriver() {
        
        if( webDriver.get() != null)
            webDriver.get().quit();
        
        
    }
    
    public static boolean isElementVisible(WebDriver driver, By by) {
        try {
            if (driver.findElement(by).isDisplayed()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }

    }
    
}
