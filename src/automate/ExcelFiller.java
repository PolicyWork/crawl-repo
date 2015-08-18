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
import java.util.ArrayList;
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

		File file = new File("/home/manish/workspace/crawl-repo/OUTPUT/EXCELFILLER/PostFilter_ExcelNextWorkingSet.txt"); //We write into this file
		
		File logFile = new File("/home/manish/workspace/crawl-repo/OUTPUT/EXCELFILLER/PostFilter_ErrorLogging_ExcelNextWorkingSet.txt"); //We write into this file

		try {
			
			readHandle = new BufferedReader(new FileReader("/home/manish/workspace/crawl-repo/OUTPUT/EXCELFILLER/nextSetAfter85PagesPostFilter.txt"));  //Input file containing keywords
			
			BufferedWriter outputHandle = new BufferedWriter(new FileWriter(file));
			BufferedWriter logHandle = new BufferedWriter(new FileWriter(logFile));
			
			String keyword = "PostFilter"; // this will contain each of the keywords as the program reads line by line
			
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
					
//					List<WebElement> lineNumberElements = driver.findElements(By.xpath("//td[contains(@class,'blob-code blob-code-addition selected-line') or contains(@class,'blob-code blob-code-addition') or contains(@class,'blob-code blob-code-deletion') or contains(@class,'blob-code blob-code-deletion selected-line')]//span[contains(.,'PostAuthorize') and contains(.,'@')]/ancestor::tr[1]/td[2]"));
					
					//List<WebElement> lineNumberElements = driver.findElements(By.xpath("//td[contains(@class,'blob-code blob-code-addition selected-line') or contains(@class,'blob-code blob-code-addition') or contains(@class,'blob-code blob-code-deletion') or contains(@class,'blob-code blob-code-deletion selected-line')]//span[contains(.,'PostAuthorize') and contains(.,'@')]/ancestor::tr[1]/*")); //this works for deleted postauthorize also
					
					List<WebElement> lineNumberElements = driver.findElements(By.xpath("//td[contains(@class,'blob-code blob-code-addition selected-line') or contains(@class,'blob-code blob-code-addition') or contains(@class,'blob-code blob-code-deletion') or contains(@class,'blob-code blob-code-deletion selected-line')]//span[contains(.,'"+keyword+"') and contains(.,'@')]/ancestor::tr[1]/*")); //this works for deleted postauthorize also
					
					//List<WebElement> lineNumberElements = driver.findElements(By.xpath("//div[contains(@data-path,'src/main/java/com/sequenceiq/cloudbreak/repository/BlueprintRepository.java')]/..//td[contains(@class,'blob-code blob-code-addition selected-line') or contains(@class,'blob-code blob-code-addition')]/span[1]"));
					
					//List<WebElement> lineNumberElements = driver.findElements(By.xpath("//td[contains(@class,'blob-code blob-code-addition selected-line') or contains(@class,'blob-code blob-code-addition')]//span[contains(.,'PostAuthorize')]/ancestor::div[1]/..//span[contains(@title,'src/main/java/com/sequenceiq/cloudbreak/repository/BlueprintRepository.java')]"));
					System.out.println("lineNumberElements:"+lineNumberElements);
					System.out.println("lineNumberElements size:"+lineNumberElements.size());
					
					String fileName="";
					String[] pathArray;
					String lineNumber="";
					String changes="";
					Boolean notNullLineFound=false;
					WebElement fileNameHandle = null;
					String prevFileName;
					List<Integer> listNumbersForMap=null;
					
					//Map<String,Integer[]> mapper = new LinkedHashMap<String,Integer[]>();  //Generate one hashmap per link that you analyze
					Map<String,List<Integer>> mapper = new LinkedHashMap<String,List<Integer>>();  //Generate one hashmap per link that you analyze
//					Iterator<Map.Entry<String, Integer>> iterator ;
//					List<String> fileNameList = new ArrayList<String>(100);
//					List<Integer> lineNumberList = new ArrayList<Integer>(100);
//					List<Integer> listNumbersForMap = new ArrayList<Integer>();
					
					try{
						
/*//						List<WebElement> listChanges1 = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]//a[contains(@class,'diff-expander js-expand')]"));  //prev working but gives  files in any order						
						
						List<WebElement> fileNames = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]//a[contains(@class,'diff-expander js-expand')]/../../../../../..//div[contains(@class,'file-header')]"));
						
						System.out.println("==================================================");
						System.out.println("listChanges1 size:"+fileNames.size());
						System.out.println("listChanges1:"+fileNames);
						System.out.println("==================================================");
						
						if(fileNames.size() == 0)
							continue;
						
						int i=0;*/
						
						
						
/*						for(WebElement w:fileNames){
							
							fileName=w.getAttribute("data-path");
							
							System.out.println("FILENAME:"+fileName);
							
							String[] arrayOfValues=fileName.split("/");
							fileName=arrayOfValues[arrayOfValues.length-1];
									
									//fileNameList.add(fileName);
									mapper.put(fileName, 1);
									
									System.out.println("Added "+fileName+" to hash table");
								
							
						}*/
						
						/*iterator = mapper.entrySet().iterator() ;*/
						
						int counter=0;
						
						for(WebElement lineElement:lineNumberElements){
							
							counter++;
							
							System.out.println("LINE ELEMENT::"+lineElement);
							
							lineNumber=lineElement.getAttribute("data-line-number");
							
							if(lineNumber == null)
								continue;
							
							System.out.println("++lineNumber:"+lineNumber);
							System.out.println("Code Changes::"+lineElement.getText());
							System.out.println("td id value::"+lineElement.getAttribute("id"));
							
							String searchFileName="//td[contains(@id,'"+lineElement.getAttribute("id")+"')]/ancestor::div[1]/..//div[contains(@class,'file-header')]";
							fileNameHandle = driver.findElements(By.xpath(searchFileName)).get(0);
							
							System.out.println("\n\nFILEHANDLE:"+fileNameHandle+"\n\n");
							
							System.out.println("============================================++++++++++++++++++++++++++++++++++"+lineNumber);
							
							if(fileNameHandle.toString().contains("data-path")){
								fileName=fileNameHandle.getAttribute("data-path");
								//System.out.println("Exception filename:"+fileName);
								
								if(fileName.contains("/")){
									String[] arrayOfValues=fileName.split("/");
									fileName=arrayOfValues[arrayOfValues.length-1];
//									System.out.println("\n BEFORE FILENAME:"+fileName);
									fileName = fileName.substring(0, fileName.length());
//									System.out.println("\n AFTER FILENAME:"+fileName);
								}
							}
							else
							{
								String[] arrayOfValues=fileNameHandle.toString().split("/");
								fileName=arrayOfValues[arrayOfValues.length-1];
								fileName = fileName.substring(0, fileName.length()-3);
							}
							
							prevFileName=fileName;
//							System.out.println("\n\n\nFileName Found By New Method ->>>>"+fileName);
							
							
							
							if(lineNumber != null){
								System.out.println("************************Inserting into HASHMAP line number:"+lineNumber);
								
//								listNumbersForMap.add(Integer.parseInt(lineNumber))
								
								if((mapper.get(fileName) == null) /*|| (counter == lineNumberElements.size())*/) {
									
									listNumbersForMap = new ArrayList<Integer>();		//for every new file, create a new list
									listNumbersForMap.add(Integer.parseInt(lineNumber));
									mapper.put(fileName,listNumbersForMap);
									
									/*if(mapper.size()!=0){
										mapper.put(prevFileName,listNumbersForMap);
										System.out.println("Adding to map when map already has element");
									}
									else
									{
										
										mapper.put(fileName,listNumbersForMap);
										System.out.println("Adding for first time");
									}*/
									
									
								}
								else
								{
									System.out.println(" ADDING IN THE ARRAY LIST NOW ...");
									listNumbersForMap.add(Integer.parseInt(lineNumber));
								}
								//mapper.put(fileName,Integer.parseInt(lineNumber));
								
								
								
//								System.out.println("marker+++++++++++");
								notNullLineFound=true;
					        }
						}
//						System.out.println("marker+++++++++++");
						
						if(notNullLineFound == true){
							
//							System.out.println("+*+*+*");
							
							System.out.println("HASHMAP SIZE:"+mapper.size());
							
							for (Map.Entry<String, List<Integer>> entry : mapper.entrySet()) {
								System.out.println("\n\n\n\n *********Entry********\n\n ");
								
								System.out.println("Entry key:"+entry.getKey());
								System.out.println("Entry key:"+entry.getValue());
						        for(int lineNumberFromMap : entry.getValue()){
						        	
						        	System.out.println(urlToGet+"|"+commitMessage+"|"+entry.getKey()+"|"+lineNumberFromMap+"\n");
						        	outputHandle.write(urlToGet+"|"+commitMessage+"|"+entry.getKey()+"|"+lineNumberFromMap+"\n");
						        }
						    }
							
							/*for(String key: mapper.keySet()){
					            System.out.println(key  +" :: "+ mapper.get(key));
					            outputHandle.write(urlToGet+"|"+commitMessage+"|"+key+"|"+mapper.get(key)+"\n");
				         }	*/					
//						 outputHandle.flush();
							
						}
						 
						
						//System.out.println("Size of lineNumberList :"+lineNumberList.size());
						//System.out.println("Size of fileNameList :"+fileNameList.size());
						
						
						//String listChanges1 = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]//a[contains(@class,'diff-expander js-expand')]")).get(0).getAttribute("data-url");
						
						
					}catch(Exception e){
						System.out.println("In Exception***********************************************\n");
						System.out.println("In Exception***********************************************\n");
						/*String listChanges1 = driver.findElements(By.xpath("//span[contains(.,'PostAuthorize')]/ancestor::table[1]/../..//span[contains(@class,'js-selectable-text')]")).get(0).getAttribute("title");
						String[] arrayOfValues=listChanges1.split("/");
						fileName=arrayOfValues[arrayOfValues.length-1];*/
						
						/*fileName=fileNameHandle.getAttribute("data-path");
						System.out.println("Exception filename:"+fileName);
						*/
						
						
					}
						
//					System.out.println("fileName:"+fileName);
					
//					System.out.println("CommitMessage:"+commitMessage);
					
			} // end of while loop for reading input file containing keywords
			
			outputHandle.close(); //close the file only after reading all the keywords from the input file
			
			logHandle.close();

			readHandle.close(); // close the file containing keywords

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();

		}

	}// end of psvm

}

