package com.example;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class LoginTestfile {
	

	    private WebDriver driver;
	    private static final String url = "https://itassetmanagementsoftware.com/rolepermission/admin/login";
	    private static final String errorLogPath = "error_log.txt"; 

	    @BeforeClass
	    public void setUp() {
	        System.setProperty("webdriver.chrome.driver", "<path/to/chromedriver>"); 
	        driver = new ChromeDriver();
	        driver.manage().window().maximize();
	    }

	    @Test
	    public void testLogin() throws Exception {
	        driver.get(url);

	       
	        driver.findElement(By.id("username")).sendKeys("invalid_username");
	        driver.findElement(By.id("password")).sendKeys("invalid_password");
	        driver.findElement(By.id("loginBtn")).click();

	       
	        String errorMessage = driver.findElement(By.cssSelector(".error-message")).getText(); 
	        if (!errorMessage.isEmpty()) {
	            FileUtils.writeStringToFile(new File(errorLogPath), errorMessage, true); 
	        }

	        
	    }

	    @AfterClass
	    public void tearDown() {
	        driver.quit();
	    }
	}
