/**
 * 
 */
package automate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * @author manish
 *
 */
public class ExcelFiller {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException,	IOException {

		BufferedReader readHandle;
		
		System.out.println("marker1");

		File file = new File("/home/manish/workspace/crawl-repo/OUTPUT/EXCELFILLER/Output1.txt"); //We write into this file
		
		File logFile = new File("/home/manish/workspace/crawl-repo/OUTPUT/EXCELFILLER/ErrorLogging.txt"); //We write into this file

		try {
			
			readHandle = new BufferedReader(new FileReader("/home/manish/workspace/crawl-repo/OUTPUT/EXCELFILLER/LinksToRead.txt"));  //Input file containing keywords
			
			BufferedWriter outputHandle = new BufferedWriter(new FileWriter(file));
			BufferedWriter logHandle = new BufferedWriter(new FileWriter(logFile));
			
			String keyword = "@PostAuthorize"; // this will contain each of the keywords as the program reads line by line
			
			WebDriver driver = new HtmlUnitDriver();
			
//			FirefoxProfile p = new FirefoxProfile();
//			
//			System.out.println("marker2");
//			
//			p.setPreference("javascript.enabled", false);
//			
//			org.openqa.selenium.WebDriver driver = new FirefoxDriver();
//			
//			System.out.println("marker3");
//			
//			driver.manage().window().maximize();
//			
			Logger logger = Logger.getLogger ("");
			
			System.out.println("marker3");
			
			logger.setLevel (Level.SEVERE);

			WebElement element;
			
			String urlToGet,commitTitle,commitMessageDesc;

			while ((urlToGet = readHandle.readLine()) != null) {
					
					System.out.println("keywordLine:"+urlToGet);

					driver.get(urlToGet);
					
					try{
						
						commitTitle=driver.findElement(By.xpath("//p[contains(@class,'commit-title')]")).getText();
						
						System.out.println("Commit Title:"+commitTitle);
						
					}catch(Exception e){
						logHandle.write("codeChangeUrl:->"+urlToGet);
						continue;
					}
					
					try{
						
//						 commitMessageDesc=driver.findElement(By.xpath("//p[contains(@class,'commit-desc')]")).getText();
						 commitMessageDesc=driver.findElement(By.xpath("//*[@class=\"commit-desc\"]/pre")).getText();
						 
						}catch(Exception e){
								commitMessageDesc="";
						}
					
					String commitMessage = commitTitle+"||"+commitMessageDesc;
			
					String search="//td[contains(@class,'blob-code blob-code-addition')  or contains(@class,'blob-code blob-code-deletion')]";
					
					//String search="//td[contains(@class,'blob-code blob-code-deletion')]";
					
					System.out.println("SEARCH STRING IS -->"+search);
					
//					List<WebElement> listChanges = driver.findElements(By.xpath(search));
					//Below works
					//List<WebElement> listChanges = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]"));
					
//					List<WebElement> listChanges = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::tr[1]/td[3]//span[1]/ancestor::span[2]/ancestor::td[1]"));
					
					List<WebElement> listChanges = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]//td[contains(@class,'blob-num blob-num-expandable')]/a[1]"));
					
					String listChanges1 = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]//a[contains(@class,'diff-expander js-expand')]")).get(0).getAttribute("data-url");
					
					String[] arrayOfValues=listChanges1.split("&");
					
					for(String value:arrayOfValues){
						if(value.toLowerCase().contains("path")){
							String[] pathArray=value.split("%2F");
							System.out.println("FileName:"+pathArray[pathArray.length-1]);
						}
					}
						
					
					
					System.out.println("+++++++++++++++++++++++++++++++");
					
					System.out.println("listChanges:"+listChanges1);
					
					System.out.println("+++++++++++++++++++++++++++++++");
					
					System.out.println("ListChanges size is :"+ listChanges.size());
					
					Boolean isCodeChangeUrlPrinted = false;

//					Boolean gotMatchingText=false;
					
					if (listChanges.size()>0)
					{
						for (int count = 0; count < listChanges.size(); count++) {

							//prints all the code changes associated with each commit
							
							String codeChanges = listChanges.get(count).getText();
							
							System.out.println("listChanges:"+listChanges.get(count));
							
							
							String lineNumber = listChanges.get(count).getAttribute("data-line");
							
							System.out.println("Line Number :"+lineNumber);							

							//To print only keyword matching lines
							
							if(codeChanges.toLowerCase().contains(keyword.toLowerCase()))
							{
									if(isCodeChangeUrlPrinted == false)
									{
										isCodeChangeUrlPrinted=true;
										
										outputHandle.write("\n=========================================================\n");
										
										outputHandle.write("[CODE-CHANGE-URL]:"+urlToGet+"\n");
										outputHandle.write("\n[COMMIT MESSAGE]:"+commitMessage+"\n");
										
										outputHandle.write("=========================================================\n\n");
									}
										
											
									System.out.println("Commit Message:->"+commitMessage);
									
//									outputHandle.write("\n[COMMIT MESSAGE]:"+commitMessage+"\n\n");
									
									System.out.println("IT IS TRUE ******************************");
									
									outputHandle.write(codeChanges);
									
									System.out.println("********");
									
									System.out.println(codeChanges);

									outputHandle.write("\n");
							}
						}
					}//This is end of if condition for listChanges.size

			} // end of while loop for reading input file containing keywords
			
			outputHandle.close(); //close the file only after reading all the keywords from the input file
			
			logHandle.close();

			readHandle.close(); // close the file containing keywords

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();

		}

	}// end of psvm

}

