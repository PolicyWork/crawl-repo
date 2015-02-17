
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class GitCrawlerDebug {

	public static void main(String[] args) throws InterruptedException,
	IOException {

		BufferedReader readHandle;

		File file = new File(
				"/home/manish/workspace/GitCrawler/CrawlerOutput.txt");

		try {
			readHandle = new BufferedReader(
					new FileReader(
							"/home/manish/workspace/GitCrawler/InputPolicyKeywords.txt"));

			String keywordLine; // this will contain each of the keywords as the
			// program reads line by line

			while ((keywordLine = readHandle.readLine()) != null) {

				try {
					BufferedWriter outputHandle = new BufferedWriter(
							new FileWriter(file));

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

					// element.sendKeys("hasRole()");

					element.sendKeys(keywordLine);

					element.submit();

					WebDriverWait wait = new WebDriverWait(driver, 10);

					element = wait
							.until(ExpectedConditions.elementToBeClickable(By
									.xpath("//a[contains(@href, 'type=Code')]")));

					// element.sendKeys("Code");

					element.click();

					element = wait.until(ExpectedConditions
							.elementToBeClickable(By
									.xpath("//a[contains(@href, 'l=java')]")));

					element.click();

					Thread.sleep(4000L);

					// Infinite loop starts here . You keep clicking on the next
					// button of search results. In case it fails, an exception is caught and
					// control breaks out of the infinite loop // gracefully(supposed to)
					
					Integer counter = 0;
					while (true) {
						counter=counter+1;
						System.out.println("Marker...");

						element = driver.findElement(By
								.className("code-list-item")); // check // if // // this // line // // is // // required

						By selector = By.xpath("//p[@class='title']/a[2]"); // returns // all // the // classes // with // title // on // search // results // page

						// System.out.println(selector);

						List<WebElement> list = driver.findElements(selector);
						
						System.out.println("MARKER 21");

						 System.out.println("List size is :" + list.size());

						// For each of the search results
						for (int i = 0; i < list.size(); i++) {
							new WebDriverWait(driver, 10)
							.until(ExpectedConditions
									.elementToBeClickable(selector));
							
						}

						

						try {
								System.out.println("Counter value is :"+counter);
								Thread.sleep(4000L);
								Thread.sleep(4000L);
								element = driver.findElement(By.xpath("//a[contains(@rel,'next')]"));

								element.click();
								
								
							
						} catch (Exception e) {
							
							System.out.println("Inside break");
							
							break; // In case the fetch fails , come out of the infinite while loop
						}

					}// Close the infinite while loop

					outputHandle.close(); 
					

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // end of while loop for reading input file containing keywords

			readHandle.close(); // close the file containing keywords

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}// end of psvm

}// end of class
