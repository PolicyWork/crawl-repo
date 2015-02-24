
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class test {

	public static void main(String[] args) throws InterruptedException,	IOException {

		BufferedReader readHandle;

		File file = new File("/home/manish/workspace/crawl-repo/GitCommitMessages.txt"); //We write into this file

		try {
			
			readHandle = new BufferedReader(new FileReader("/home/manish/workspace/crawl-repo/InputPolicyKeywordsForCommitMessages.txt"));  //Input file containing keywords

			BufferedWriter outputHandle = new BufferedWriter(new FileWriter(file));
			
			String keywordLine; // this will contain each of the keywords as the program reads line by line
			
			WebDriver driver = new HtmlUnitDriver();
			
			Logger logger = Logger.getLogger ("");
			
			logger.setLevel (Level.SEVERE);

			WebElement element;
			
			Integer counter = 0; //Only for tracking and debugging

			while ((keywordLine = readHandle.readLine()) != null) {

				try {
					
					String lang="java";

					String urlToGet ="https://github.com/search?utf8=%E2%9C%93&q="+keywordLine+"&l="+lang+"&type=Code";
					
					System.out.println("urlToGet is : "+urlToGet);
					
					
					
					if(counter!=0)
					{
						counter = 0;   //not required actually to reset the counter for every new search keyword
						Thread.sleep(10000L);
						Thread.sleep(10000L);
						Thread.sleep(10000L);
						Thread.sleep(10000L);
						Thread.sleep(10000L);
					}
					
					//Send HTTP GET Request
					driver.get(urlToGet);

					/* 	Infinite loop starts here . You keep clicking on the next
						button of search results. In case it fails, an exception is caught and
						control breaks out of the infinite loop  gracefully(supposed to)
					*/
					
					
					
					while (true) {
						
						String nextPageUrl = driver.findElement(By.className("next_page")).getAttribute("href");
						
						System.out.println("Next Page URL is <><><><>:"+nextPageUrl);
						
						counter=counter+1;  //Only for tracking and debugging
						
						System.out.println("Marker..."); //Only for tracking and debugging
						
						List<WebElement> results = driver.findElements(By.xpath("//*[@id=\"code_search_results\"]/div[1]/div/p/a[2]"));
						
						List<String> urlsOfFileCommits = new LinkedList<String>();
						
						for(WebElement result : results){
							urlsOfFileCommits.add(result.getAttribute("href").replace("blob", "commits"));
							
							System.out.println("size of urls is:"+urlsOfFileCommits.size());
							
							System.out.println("urlsOfFileCommits:->"+urlsOfFileCommits);
						}
						
						for(String url : urlsOfFileCommits){//match1
							
							System.out.println("WORKING URL NOW->"+url);
							
							driver.get(url);
							
							outputHandle.write("=========================================================\n");
							
							outputHandle.write("[COMMIT-URL]:"+url+"\n\n");
							
//							outputHandle.write("=========================================================\n");
							
							List<WebElement> commitsPerFile = driver.findElements(By.xpath("//a[contains(@class,'message')]"));
							
							List<String> commitMessagesPerFile = new LinkedList<String>();
							
							for(WebElement result : commitsPerFile){
								commitMessagesPerFile.add(result.getAttribute("title"));
							}
							
							for(String eachCommitMessage:commitMessagesPerFile){
								System.out.println("Commit Message:->"+eachCommitMessage);
								outputHandle.write("[COMMIT MESSAGE]:"+eachCommitMessage+"\n\n");
							}
							
						}//match1
						
						try{
								
								outputHandle.write("[NEXT-PAGE-URL]:"+nextPageUrl+"\n\n");
						
								System.out.println("[NEXT-PAGE-URL]:"+nextPageUrl+"\n\n");
								
								outputHandle.flush();
								
								System.out.println("BEFORE GET REQUEST _+_+_+_+_+_+");
								
//								Thread.sleep(4000L);
								
								System.out.println("Counter value:="+counter);
								
								if (counter%9 == 0)
								{
									System.out.println("Before more sleep");
									Thread.sleep(10000L);
									Thread.sleep(10000L);
									Thread.sleep(10000L);
									Thread.sleep(10000L);
									Thread.sleep(10000L);
//									Thread.sleep(10000L);
//									Thread.sleep(10000L);
//									Thread.sleep(10000L);
//									Thread.sleep(10000L);
									System.out.println("After more sleep");
								}
								
//								if(nextPageUrl!=null)
//								{
//									driver.get(nextPageUrl);
//								}
//								else
//								{
//									break;
//								}
								
								driver.get(nextPageUrl);
								
						}catch(Exception e){
							
							e.printStackTrace();
							
							break;
						}

				
					}// Close the infinite while loop 

//					outputHandle.close(); 
					

				} catch (IOException e) {
					
					outputHandle.close(); //close the file only after reading all the keywords from the input file
					
					e.printStackTrace();
				}

			} // end of while loop for reading input file containing keywords

			readHandle.close(); // close the file containing keywords

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();

		}

	}// end of psvm

}// end of class
