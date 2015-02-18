import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class NewApproach {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		WebDriver driver = new HtmlUnitDriver();
		 
		driver.get("https://github.com/search?l=java&q=hasAnyRole+spring&ref=simplesearch&type=Code&utf8=%E2%9C%93");
		
		 
		List<WebElement> results = driver.findElements(By.xpath("//*[@id=\"code_search_results\"]/div[1]/div/p/a[2]"));
		
		List<String> urlsOfFileCommits = new LinkedList<String>();
		 
		for(WebElement result : results){
			urlsOfFileCommits.add(result.getAttribute("href").replace("blob", "commits"));
			System.out.println("size of urls is:"+urlsOfFileCommits.size());
		}
		 
		for(String url : urlsOfFileCommits){
			driver.get(url);
			List<WebElement> commits = driver.findElements(By.xpath("//*[@id=\"compare\"]/div[2]/ol/li/div[2]/p/a"));
			if(commits.size()>1){
				System.out.println("Multiple testing commits: " + url);
			}else{
				System.out.println("Single testing ok commits: " + url);
			}
		}

	}

	
	
}
