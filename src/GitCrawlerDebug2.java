
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



public class GitCrawlerDebug2 {

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

						 System.out.println("List size is :" + list.size());

						// For each of the search results
						for (int i = 0; i < list.size(); i++) {

							new WebDriverWait(driver, 10)
							.until(ExpectedConditions
									.elementToBeClickable(selector));

							// System.out.println(driver.findElements(selector).get(i).getText());

							// For each file , we write the below header to
							// differentiate the code changes w.r.t files
							outputHandle
							.write("======================================================\n");
							outputHandle.write("[OUTPUT-FILENAME]: "
									+ driver.findElements(selector).get(i)
									.getText()); // write the file which // is being // processed into // the output file
							outputHandle
							.write("\n======================================================");

							outputHandle.write("\n\n");

							// Click on the title
							driver.findElements(selector).get(i).click();

							// Thread.sleep(4000L);

							element = wait
									.until(ExpectedConditions.elementToBeClickable(By
											.xpath("//a[contains(@class,'minibutton') and .//text()='History']")));

							element.click();

							By getCommitList = By
									.xpath("//a[contains(@class,'sha button-outline')]");

							List<WebElement> commitList = driver
									.findElements(getCommitList);

							 System.out.println("CommitList size is :" +commitList.size());

							for (int commitCount = 0; commitCount < commitList.size(); commitCount++) {
								System.out.println("current commitCount:"+commitCount);
								String commitName = driver
										.findElements(getCommitList)
										.get(commitCount).getText(); // works
								// but
								// redundant
								// call

								// String
								// commitName=commitList.get(commitCount).getText();

								System.out.println(commitName);

								// commitList.get(commitCount).click();
								// //suggested improvement but fails

								driver.findElements(getCommitList)
								.get(commitCount).click();

								By getChanges = By.xpath("//td[contains(@class,'blob-code blob-code-addition') or contains(@class,'blob-code blob-code-deletion')]");
								
//								By getCount = By.xpath("count(//td[contains(@class,'blob-code blob-code-addition') or contains(@class,'blob-code blob-code-deletion')])");

								List<WebElement> listChanges = driver
										.findElements(getChanges);
								
								

								System.out.println("ListChanges size is :"
										+ listChanges.size());

								if (listChanges.size() < 100)
								{
																	
									for (int count = 0; count < listChanges.size(); count++) {
	
										// System.out.println(driver.findElements(getChanges).get(count).getText());
										// //prints all the code changes associated
										// with each commit
	
										// String
										// codeChanges=driver.findElements(getChanges).get(count).getText();
	
										String codeChanges = listChanges.get(count)
												.getText();
	
										// System.out.println("Writing code changes into file now *********");
	
										outputHandle.write(codeChanges);
	
										outputHandle.write("\n");
	
									}
									
								}//This is end of if conditin for listChanges.size
								// AFTER FINISHING FIRST COMMIT, YOU MOVE TO THE
								// NEXT COMMIT ON PREVIOUS PAGE

								driver.navigate().back();

							}// end of commit count loop

							// After you have covered all the commits w.r.t a
							// single file, go back to the previous page - which
							// is that file page.

							driver.navigate().back();

							// outputHandle.close(); // This should not be here.
							// To be closed after processing all the search
							// results

							// System.out.println(textString);

							// Thread.sleep(4000L);

							// This takes you to the search results page. From
							// here we click on the next file in the next
							// iteration
							driver.navigate().back(); // you can also consider
							// storing the url of
							// results page and
							// going directly there

							// Thread.sleep(4000L);
						}

						element = driver.findElement(By.className("code-list-item"));

						try {
								System.out.println("Counter value is :"+counter);
								
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
