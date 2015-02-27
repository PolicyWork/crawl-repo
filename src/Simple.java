

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import java.util.ArrayList;

public class Simple {

	public static void main(String[] args) throws InterruptedException {

		
		File file = new File("CrawlerOutput.txt");
		
		try {
			BufferedWriter outputHandle = new BufferedWriter(new FileWriter(file));
							
			WebDriver driver = new HtmlUnitDriver();
			
			Logger logger = Logger.getLogger ("");
			
			logger.setLevel (Level.SEVERE);
	
			driver.get("https://github.com/Tietoarkisto/metka/commit/8211a575b8d91d62975f1fcfc559687b8a675a2a");
	
			WebElement element;
	
			String commitTitle=driver.findElement(By.xpath("//p[contains(@class,'commit-title')]")).getText();
			
			String commitMessageDesc;
			
			//Sometimes commit-desc not there on the page
			try{
				
				 commitMessageDesc=driver.findElement(By.xpath("//*[@class=\"commit-desc\"]/pre")).getText();
				 
				}catch(Exception e){
						commitMessageDesc="";
				}
			
			String commitMessage = commitTitle+"||"+commitMessageDesc;
			
			System.out.println("commitMessage="+commitMessage);
	
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}
		
	}
}
