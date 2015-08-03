package automate;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import java.util.ArrayList;


public class FindHistory {

	public static void main(String[] args) throws InterruptedException,	IOException {

		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter the search line");
		
		String searchLine=scan.nextLine();
		
		//WebDriver driver = new HtmlUnitDriver();
		
		WebDriver driver = new FirefoxDriver();
		
		
		Logger logger = Logger.getLogger ("");
		
		logger.setLevel (Level.SEVERE);

		WebElement element;
		
		String urlToGet ="https://github.com/search?l=java&p=93&q=%40PreAuthorize%28%22&type=Code&utf8=%E2%9C%93";
		
		System.out.println("urlToGet is : "+urlToGet);
		
		
		driver.manage().window().maximize();
		
		driver.get("https://github.com");
//		while(true){
//			
//			
//			
//			
//			
//		}

	}// end of psvm

}// end of class
