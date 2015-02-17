
import java.io.BufferedWriter;
import java.io.FileWriter;
//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Example1  {
	
	public static void main(String[] args) throws InterruptedException {
		
		FirefoxProfile p = new FirefoxProfile();
		p.setPreference("javascript.enabled", false);
		
		// Create a new instance of the Firefox driver
        
		org.openqa.selenium.WebDriver driver = new FirefoxDriver();
        
		// visit realsearchgroup site
//        driver.get("https://github.com/search?l=java&q=hasRole&type=Code&utf8=%E2%9C%93");
        
//        driver.get("https://github.com/search?l=java&q=hasRole&type=Code&utf8=%E2%9C%93");   //working line commented for testing
        
//        https://github.com/search?l=java&q=hasRole%28%29hasRole&ref=searchresults&type=Code&utf8=%E2%9C%93
		
		
		driver.get("https://github.com"); 
        
        WebElement element;
        
        //Enter Login Credentials
        element = driver.findElement(By.name("q"));

    
        element.sendKeys("hasRole()");
        
        element.submit();
        WebDriverWait wait = new WebDriverWait(driver, 10);
            
   
      
//      driver.findElement(By.xpath("//a[@href='?storeid=1217&menu=detail&eventid=32432']")).click();
      
      
      
      
      element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'type=Code')]")));
      
//      element.sendKeys("Code");

       element.click();
       
       Thread.sleep(4000L);
      
  
        
//        wait(driver);
        
        
        System.out.println("Marker...");
       
      element = driver.findElement(By.className("code-list-item"));
      
      List<WebElement> elementList = new LinkedList<WebElement>();
      
      for(WebElement iterator: elementList)
      {
    	  iterator =driver.findElement(By.) 
      }
      
       
       
        
//        Gets List of links that are to be crawled
//        List<String> lnkList = new LinkedList<String>();
//        for(String lnk: lnkList)
//        {
//        	//follow individual links
//        	element =  driver.findElement(By.linkText(lnk));
//            element.click();
//            wait(driver);
//            processSource(lnk, driver.getPageSource());
//            //get back to List page
//            driver.navigate().back();
//            wait(driver);
//        }
        
        //Close the browser
//        driver.quit();
    }

	private static void processSource(String id, String pageSource) {
		
		try
		{
			FileWriter fileWriter = new FileWriter("/home/manish/research/ResearchScripts/selenium/" + id.replaceAll("\\s", "_") + ".html");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(pageSource);
			bufferedWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//TODO Write Code to create XLS Sheet
		//Document doc = Jsoup.parse(pageSource);
		//DataRow row = new DataRow();
		//row.setActivity("");
	}

	

//	private static void wait(WebDriver driver) {
//		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver d) {
//                return d.findElement(By.linkText("List")).getText().toLowerCase().startsWith("list");
//            }
//        });
//	}
}
