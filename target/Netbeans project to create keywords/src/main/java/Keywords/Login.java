/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Keywords;

import Pages.LoginPage;
import java.util.HashMap;
import org.openqa.selenium.By;

/**
 *
 * @author Andrei
 */
public class Login {
    
   public void login(HashMap<String,String> params) {
       
        LoginPage.navigate(Util.webDriver.get());
        LoginPage loginPage = new LoginPage(Util.webDriver.get() );
     
  
        loginPage.username.sendKeys(params.get("email"));
        loginPage.password.sendKeys(params.get("password"));
        loginPage.goButton.click();
     }   
        
}
