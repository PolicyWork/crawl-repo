

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import java.util.ArrayList;

public class TestCommitList {

	public static void main(String[] args) throws InterruptedException {

		
		File file = new File("CrawlerOutput.txt");
		
		try {
			BufferedWriter outputHandle = new BufferedWriter(new FileWriter(file));
			
				
			FirefoxProfile p = new FirefoxProfile();
			p.setPreference("javascript.enabled", false);
	
			// Create a new instance of the Firefox driver
			org.openqa.selenium.WebDriver driver = new FirefoxDriver();
	
			// Maximize the window
			driver.manage().window().maximize();
	
			driver.get("https://github.com");
	
			WebElement element;
	
			// Enter Login Credentials
			element = driver.findElement(By.name("q"));
	
			element.sendKeys("hasRole()");
	
			element.submit();
	
			WebDriverWait wait = new WebDriverWait(driver, 10);
	
			element = wait.until(ExpectedConditions.elementToBeClickable(By
					.xpath("//a[contains(@href, 'type=Code')]")));
	
			// element.sendKeys("Code");
	
			element.click();
	
			element = wait.until(ExpectedConditions.elementToBeClickable(By
					.xpath("//a[contains(@href, 'l=java')]")));
	
			element.click();
	
			Thread.sleep(4000L);
	
			System.out.println("Marker...");
	
			element = driver.findElement(By.className("code-list-item")); // check
			// if
			// this
			// line
			// is
			// required
	
			By selector = By.xpath("//p[@class='title']/a[2]"); // returns all the classes with title on search results page
	
			System.out.println(selector);
	
			List<WebElement> list = driver.findElements(selector);
	
			System.out.println("List size is :" + list.size());
	
			//For each of the search results
			for (int i = 0; i < list.size(); i++) {
	
				new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(selector));
	
//				System.out.println(driver.findElements(selector).get(i).getText());
				
				//For each file , we write the below header to differentiate the code changes w.r.t files
				outputHandle.write("======================================================\n");
				outputHandle.write("[OUTPUT-FILENAME]: "+driver.findElements(selector).get(i).getText());   //write the file which is being processed into the output file
				outputHandle.write("\n======================================================");
				
				outputHandle.write("\n\n");
				
				// Click on the title
				driver.findElements(selector).get(i).click();

				Thread.sleep(4000L);
	
				element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class,'minibutton') and .//text()='History']")));
	
				element.click();
				
				//Get list of commits for that file
	
				By getCommitList = By.xpath("//a[contains(@class,'sha button-outline')]");
	
				List<WebElement> commitList = driver.findElements(getCommitList);
				
				System.out.println("ListChanges size is :" + commitList.size());
				
				//Process each of the commits
				
				for(int commitCount = 0; commitCount < commitList.size(); commitCount++)
				{
					String commitName=driver.findElements(getCommitList).get(commitCount).getText();
					
					System.out.println(commitName);
					
//					driver.findElements(getCommitList).get(commitCount).click();
					
					commitList.get(commitCount).click();
					
//					Thread.sleep(4000L);
					
					driver.navigate().back();
					
				}
		
					outputHandle.close();  // This should not be here. To be closed after processing all the search results
					
					//			System.out.println(textString);
		
					Thread.sleep(4000L);
		
					driver.navigate().back();
		
					Thread.sleep(4000L);
				}
	
//			outputHandle.close();  //This line should be uncommented in the final code
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}//end of psvm
	
}//end of class
