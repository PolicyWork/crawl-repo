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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	public static String getLineNumber(List<WebElement> listChanges){
		
//		Boolean gotMatchingText=false;
		
		String lineNumber=null;
		
		if (listChanges.size()>0)
		{
			for (int count = 0; count < listChanges.size(); count++) {

				lineNumber = listChanges.get(count).getAttribute("data-line-number");
				
				if(lineNumber != null){
					return lineNumber;	
				}
			}
		}
		
		return lineNumber;
	}
	
	
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
				
				System.out.println("====================================================================================================================================");
					
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
					
					String commitMessage = commitTitle+commitMessageDesc;
			
					String search="//td[contains(@class,'blob-code blob-code-addition')  or contains(@class,'blob-code blob-code-deletion')]";
					
					//String search="//td[contains(@class,'blob-code blob-code-deletion')]";
					
					System.out.println("SEARCH STRING IS -->"+search);
					
//					List<WebElement> listChanges = driver.findElements(By.xpath(search));
					//Below works
					//List<WebElement> listChanges = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]"));
					
//					List<WebElement> listChanges = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::tr[1]/td[3]//span[1]/ancestor::span[2]/ancestor::td[1]"));
					
//					List<WebElement> listChanges = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]//td[contains(@class,'blob-num blob-num-expandable')]/a[1]"));
					
					//List<WebElement> listChanges = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::tr[1]/td[2]"));  //previous working 12 Aug
					
					//td[contains(@class,'blob-code blob-code-addition selected-line') or contains(@class,'blob-code blob-code-addition')]//span[contains(.,'PostAuthorize')]/ancestor::tr[1] this gives line number and code change
					//span[contains(.,'PostAuthorize')]/ancestor::table[1]//a[contains(@class,'diff-expander js-expand')]/../../../../../..//div[contains(@class,'file-header')]
					
					//===
					
					List<WebElement> lineNumberElements = driver.findElements(By.xpath("//td[contains(@class,'blob-code blob-code-addition selected-line') or contains(@class,'blob-code blob-code-addition')]//span[contains(.,'PostAuthorize')]/ancestor::tr[1]/td[2]"));
					
					//List<WebElement> lineNumberElements = driver.findElements(By.xpath("//div[contains(@data-path,'src/main/java/com/sequenceiq/cloudbreak/repository/BlueprintRepository.java')]/..//td[contains(@class,'blob-code blob-code-addition selected-line') or contains(@class,'blob-code blob-code-addition')]/span[1]"));
					
					//List<WebElement> lineNumberElements = driver.findElements(By.xpath("//td[contains(@class,'blob-code blob-code-addition selected-line') or contains(@class,'blob-code blob-code-addition')]//span[contains(.,'PostAuthorize')]/ancestor::div[1]/..//span[contains(@title,'src/main/java/com/sequenceiq/cloudbreak/repository/BlueprintRepository.java')]"));
					System.out.println("lineNumberElements:"+lineNumberElements);
					System.out.println("lineNumberElements size:"+lineNumberElements.size());
					
					String fileName="";
					String[] pathArray;
					String lineNumber="";
					String changes="";
					Boolean notNullLineFound=false;
					
					Map<String,Integer> mapper = new LinkedHashMap<String,Integer>();  //Generate one hashmap per link that you analyze
					Iterator<Map.Entry<String, Integer>> iterator ;
//					List<String> fileNameList = new ArrayList<String>(100);
//					List<Integer> lineNumberList = new ArrayList<Integer>(100);
					
					try{
						
//						List<WebElement> listChanges1 = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]//a[contains(@class,'diff-expander js-expand')]"));  //prev working but gives  files in any order						
						
						List<WebElement> fileNames = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]//a[contains(@class,'diff-expander js-expand')]/../../../../../..//div[contains(@class,'file-header')]"));
						
						System.out.println("==================================================");
						System.out.println("listChanges1 size:"+fileNames.size());
						System.out.println("listChanges1:"+fileNames);
						System.out.println("==================================================");
						
						if(fileNames.size() == 0)
							continue;
						
						int i=0;
						
						
						
						for(WebElement w:fileNames){
							
							fileName=w.getAttribute("data-path");
							
							System.out.println("FILENAME:"+fileName);
							
							String[] arrayOfValues=fileName.split("/");
							fileName=arrayOfValues[arrayOfValues.length-1];
									
									//fileNameList.add(fileName);
									mapper.put(fileName, 1);
									
									System.out.println("Added "+fileName+" to hash table");
								
							
						}
						
						iterator = mapper.entrySet().iterator() ;
						
						for(WebElement lineElement:lineNumberElements){
							lineNumber=lineElement.getAttribute("data-line-number");
							System.out.println("++lineNumber:"+lineNumber);
						System.out.println("Code Changes::"+lineElement.getText());
							
//							if(lineNumber != null){
//								lineNumberList.add(Integer.parseInt(lineNumber));
//							}
							
							System.out.println("HashMap Size:"+mapper.size());
							
							System.out.println("marker +++");
					        
							if(lineNumber != null && iterator.hasNext()){
					        	
								System.out.println("marker ***");
					        	
								notNullLineFound = true;
					        	
					            Map.Entry<String, Integer> mapEntry = iterator.next();
					            
					            System.out.println("BEFORE::"+mapEntry.getKey() +" :: "+ mapEntry.getValue());
					            
					            System.out.println("***************");
					            
					            mapEntry.setValue(Integer.parseInt(lineNumber));
					            
					            System.out.println("AFTER::"+mapEntry.getKey() +" :: "+ mapEntry.getValue());
					        }
						}
						
						if(notNullLineFound == true){
							
							System.out.println("+*+*+*");
							
							for(String key: mapper.keySet()){
					            System.out.println(key  +" :: "+ mapper.get(key));
					            outputHandle.write(urlToGet+";"+commitMessage+";"+key+";"+mapper.get(key)+"\n");
				         }						
						 outputHandle.flush();
							
						}
						 
						
						//System.out.println("Size of lineNumberList :"+lineNumberList.size());
						//System.out.println("Size of fileNameList :"+fileNameList.size());
						
						
						//String listChanges1 = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]//a[contains(@class,'diff-expander js-expand')]")).get(0).getAttribute("data-url");
						
						
					}catch(Exception e){
						System.out.println("In Exception");
						String listChanges1 = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]/../..//span[contains(@class,'js-selectable-text')]")).get(0).getAttribute("title");
						String[] arrayOfValues=listChanges1.split("/");
						fileName=arrayOfValues[arrayOfValues.length-1];
					}
						
					System.out.println("fileName:"+fileName);
					
					System.out.println("CommitMessage:"+commitMessage);
					
			} // end of while loop for reading input file containing keywords
			
			outputHandle.close(); //close the file only after reading all the keywords from the input file
			
			logHandle.close();

			readHandle.close(); // close the file containing keywords

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();

		}

	}// end of psvm

}

