
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
import org.python.antlr.ast.keyword;


public class CommitMessageReaderDebug {

	public static void main(String[] args) throws InterruptedException,	IOException {

		BufferedReader readHandle;

		File file = new File("/home/manish/workspace/crawl-repo/OUTPUT/BEST_MATCH/CrawlerOutput_Round2_run1.txt"); //We write into this file
		
		File logFile = new File("/home/manish/workspace/crawl-repo/OUTPUT/BEST_MATCH/ErrorLogging_Round2_run1.txt"); //We write into this file

		try {
			
			readHandle = new BufferedReader(new FileReader("/home/manish/workspace/crawl-repo/InputPolicyKeywords.txt"));  //Input file containing keywords
			
			BufferedWriter outputHandle = new BufferedWriter(new FileWriter(file));
			BufferedWriter logHandle = new BufferedWriter(new FileWriter(logFile));
			
			String keywordLine; // this will contain each of the keywords as the program reads line by line
			
			WebDriver driver = new HtmlUnitDriver();
			
			Logger logger = Logger.getLogger ("");
			
			logger.setLevel (Level.SEVERE);

			WebElement element;
			
			String urlToGet;
			
//			Integer counter = 0; //Only for tracking and debugging
			
//			boolean restart=true;
			
			boolean restart=false;

			while ((keywordLine = readHandle.readLine()) != null) {

				try {
					
					String lang="java";
					
					if(restart == true)
					{
						urlToGet = "https://github.com/search?l=java&p=89&q=%40PreAuthorize%28%22&type=Code&utf8=%E2%9C%93"; //for restart
//						keywordLine = "intercept-url pattern";
						restart=false;
					}
					else
					{
						 urlToGet ="https://github.com/search?utf8=%E2%9C%93&q="+keywordLine+"&l="+lang+"&type=Code"; //original

					}
					
					System.out.println("keywordLine:"+keywordLine);
				
					System.out.println("urlToGet is : "+urlToGet);
					

					//Send HTTP GET Request
					driver.get(urlToGet);

					/* 	Infinite loop starts here . You keep clicking on the next
						button of search results. In case it fails, an exception is caught and
						control breaks out of the infinite loop  gracefully(supposed to)
					*/
					
					while (true) {
						
						String nextPageUrl = driver.findElement(By.className("next_page")).getAttribute("href");
						
						System.out.println("Next Page URL is <><><><>:"+nextPageUrl);
						
//						counter=counter+1;  //Only for tracking and debugging
						
						List<WebElement> results = driver.findElements(By.xpath("//*[@id=\"code_search_results\"]/div[1]/div/p/a[2]"));
						
						List<String> urlsOfFileCommits = new LinkedList<String>();
						
						for(WebElement result : results){
							urlsOfFileCommits.add(result.getAttribute("href").replace("blob", "commits"));
							System.out.println("size of urls is:"+urlsOfFileCommits.size());
						}
						
						for(String url : urlsOfFileCommits){//match1
							
							driver.get(url);
							
							List<WebElement> commitsPerFile = driver.findElements(By.xpath("//*[@id=\"compare\"]/div[2]/ol/li/div[2]/p/a"));
							
							List<String> urlsOfcommitsPerFile = new LinkedList<String>();
							
							for(WebElement result : commitsPerFile){
							
								urlsOfcommitsPerFile.add(result.getAttribute("href"));
							
							}
							
							for(String codeChangeUrl:urlsOfcommitsPerFile){
								
								driver.get(codeChangeUrl);
								
								System.out.println("codeChangeUrl:->"+codeChangeUrl);
								
								String commitTitle;
								
								try{
									
									commitTitle=driver.findElement(By.xpath("//p[contains(@class,'commit-title')]")).getText();
									
								}catch(Exception e){
									logHandle.write("codeChangeUrl:->"+codeChangeUrl);
									continue;
								}
								
								
								String commitMessageDesc;
								
								//Sometimes commit-desc not there on the page
								try{
									
//									 commitMessageDesc=driver.findElement(By.xpath("//p[contains(@class,'commit-desc')]")).getText();
									 commitMessageDesc=driver.findElement(By.xpath("//*[@class=\"commit-desc\"]/pre")).getText();
									 
									}catch(Exception e){
											commitMessageDesc="";
									}
								
								String commitMessage = commitTitle+"||"+commitMessageDesc;
								
//								Boolean gotMatchingText = false;
								
//								GET ALL THE CODE CHANGES ON THE PAGE - LOOK FOR LINES STARTING WITH + AND -
															
								String search="//td[contains(@class,'blob-code blob-code-addition')  or contains(@class,'blob-code blob-code-deletion')]";
								//String search="//td[contains(@class,'blob-code blob-code-deletion')]";
								
								System.out.println("SEARCH STRING IS -->"+search);
								
								List<WebElement> listChanges = driver.findElements(By.xpath(search));
								
								System.out.println("+++++++++++++++++++++++++++++++");
								
								System.out.println("ListChanges size is :"+ listChanges.size());
								
								Boolean isCodeChangeUrlPrinted = false;

//								Boolean gotMatchingText=false;
								
								if (listChanges.size()>0)
								{
									for (int count = 0; count < listChanges.size(); count++) {

										//prints all the code changes associated with each commit
										
										String codeChanges = listChanges.get(count).getText();

										//To print only keyword matching lines
										
										if(codeChanges.toLowerCase().contains(keywordLine.toLowerCase()))
										{
												if(isCodeChangeUrlPrinted == false)
												{
													isCodeChangeUrlPrinted=true;
													
													outputHandle.write("\n=========================================================\n");
													
													outputHandle.write("[CODE-CHANGE-URL]:"+codeChangeUrl+"\n");
													outputHandle.write("\n[COMMIT MESSAGE]:"+commitMessage+"\n");
													
													outputHandle.write("=========================================================\n\n");
												}
														
												System.out.println("Commit Message:->"+commitMessage);
												
//												outputHandle.write("\n[COMMIT MESSAGE]:"+commitMessage+"\n\n");
												
												System.out.println("IT IS TRUE ******************************");
												
												outputHandle.write(codeChanges);
												
												System.out.println("********");
												
												System.out.println(codeChanges);
	
												outputHandle.write("\n");
										}
									}
								}//This is end of if condition for listChanges.size
							}
						}//match1
						
						try{
								System.out.println("BEFORE GOING TO NEXT PAGE <><><>");
								
								outputHandle.write("[NEXT-PAGE-URL]:"+nextPageUrl+"\n\n");
								
								outputHandle.flush();
								
								System.out.println("BEFORE GET REQUEST _+_+_+_+_+_+");
								
								driver.get(nextPageUrl);
								
						}catch(Exception e){
							
							e.printStackTrace();
							
							break;  //to break out of infinite loop
						}
				
					}// Close the infinite while loop 

				} catch (IOException e) {
					
					outputHandle.close(); //close the file only after reading all the keywords from the input file
					logHandle.close();
					
					e.printStackTrace();
				}

			} // end of while loop for reading input file containing keywords

			readHandle.close(); // close the file containing keywords

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();

		}

	}// end of psvm

}// end of class
