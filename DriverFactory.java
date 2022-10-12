package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

WebDriver driver;
Properties prop;
	
	/* this method is used to initialize the driver on the basic of given browser name
	    @param browserName
	     @return this method will return the webdriver
	
	*/
	public WebDriver init_driver(Properties prop) {
	    String browserName = prop.getProperty("browser").trim();
		
		System.out.println("The browser name is : "+ browserName);
		
		if (browserName.equalsIgnoreCase("chrome")) {
			System.out.println("27");
			WebDriverManager.chromedriver().setup();
			driver= new ChromeDriver();
		}
		
		else  if (browserName.equalsIgnoreCase("firefox")) {
			System.out.println("28");
			WebDriverManager.firefoxdriver().setup();
			driver= new FirefoxDriver();
		}
		
		else  if (browserName.equalsIgnoreCase("safari")) {
			System.out.println("29");
			WebDriverManager.safaridriver().setup();
			driver= new  SafariDriver();
		}
		
		else  if (browserName.equalsIgnoreCase("edge")) {
			System.out.println("30");
			WebDriverManager.edgedriver().setup();
			driver= new  EdgeDriver();
		}
		
		else {
			System.out.println("Please pass the right browser name ....."+ browserName  );
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		//driver.get("https://demo.opencart.com/index.php?route=account/login");
		//driver.get("https://naveenautomationlabs.com/opencart");
		driver.get(prop.getProperty("url"));
		return driver;
		
	}
	
	  //returns properties class reference class object with all config properties
	   public Properties init_prop() {
		   try {
			FileInputStream ip= new FileInputStream("./src/test/resources/config/config.properties");
			prop = new Properties();
			prop.load(ip);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return prop;
	   }
	   
	   public String getScreenshot() {
			File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
			File destination = new File(path);
			try {
				FileUtils.copyFile(srcFile, destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return path;
		}
	     
    
  		
	   
	   public static WebDriver getDriver() 
	   {
			return tlDriver.get();
		}
}

