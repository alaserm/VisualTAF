/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Keywords;


import Pages.TicketsIndexPage;
import Pages.TicketsNewPage;
import java.util.HashMap;
import org.openqa.selenium.By;

/**
 *
 * @author Andrei
 */
public class Tickets {
    
    public void createNew(HashMap<String,String> params) {
        
        TicketsNewPage.navigate(Util.webDriver.get());
        TicketsNewPage newTicketPage = new TicketsNewPage(Util.webDriver.get() );
     
        if( params.get("subject") != null )
            newTicketPage.subjectTextBox.sendKeys(params.get("subject"));
        if( params.get("description") != null)
            newTicketPage.descriptionTextBox.sendKeys(params.get("description"));
        
        newTicketPage.createButton.click();        
    }
    
    public String verifyTicketPresence(HashMap<String,String> params) {
        
        TicketsIndexPage.navigate(Util.webDriver.get());
        TicketsIndexPage ticketsIndexPage = new TicketsIndexPage(Util.webDriver.get() );
        
        if( Util.isElementVisible(Util.webDriver.get(), By.xpath("//tr[td[2]='"+params.get("subject")+"' and td[3]='"+params.get("description")+"']") ) )
            return "FOUND";
        
        return "NOTFOUND";
    }
}
