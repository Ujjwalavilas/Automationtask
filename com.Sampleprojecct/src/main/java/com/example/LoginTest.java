package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginTest {
    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testValidLogin() {
        try {
            driver.get("https://itassetmanagementsoftware.com/rolepermission/admin/login");

            WebElement usernameField = driver.findElement(By.id("//*[@id=\"username\"]"));
            WebElement passwordField = driver.findElement(By.id("password"));
            
            usernameField.sendKeys("testuser");
            passwordField.sendKeys("testpassword");
            passwordField.submit();

            TimeUnit.SECONDS.sleep(5);

            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login failed. Please check the credentials or the site.");
        } catch (Exception e) {
            logger.error("Exception occurred: ", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidLogin() {
        try {
            driver.get("https://itassetmanagementsoftware.com/rolepermission/admin/login");

            WebElement usernameField = driver.findElement(By.id("username"));
            WebElement passwordField = driver.findElement(By.id("password"));
            
            usernameField.sendKeys("invaliduser");
            passwordField.sendKeys("invalidpassword");
            passwordField.submit();

            TimeUnit.SECONDS.sleep(5);

            Assert.assertFalse(driver.getCurrentUrl().contains("dashboard"), "Login should have failed but did not.");
        } catch (Exception e) {
            logger.error("Exception occurred: ", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void testMissingUsername() {
        try {
            driver.get("https://itassetmanagementsoftware.com/rolepermission/admin/login");

            WebElement passwordField = driver.findElement(By.id("password"));
            
            passwordField.sendKeys("testpassword");
            passwordField.submit();

            TimeUnit.SECONDS.sleep(5);

            WebElement errorElement = driver.findElement(By.id("username-error"));
            Assert.assertTrue(errorElement.isDisplayed(), "Expected error message for missing username not displayed.");
        } catch (Exception e) {
            logger.error("Exception occurred: ", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void testMissingPassword() {
        try {
            driver.get("https://itassetmanagementsoftware.com/rolepermission/admin/login");

            WebElement usernameField = driver.findElement(By.id("username"));
            
            usernameField.sendKeys("testuser");
            usernameField.submit();

            TimeUnit.SECONDS.sleep(5);

            WebElement errorElement = driver.findElement(By.id("password-error"));
            Assert.assertTrue(errorElement.isDisplayed(), "Expected error message for missing password not displayed.");
        } catch (Exception e) {
            logger.error("Exception occurred: ", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void testPageLoadTimeout() {
        try {
            driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
            driver.get("https://itassetmanagementsoftware.com/rolepermission/admin/login");

            Assert.fail("Expected timeout exception not thrown.");
        } catch (Exception e) {
            if (e.getMessage().contains("timeout")) {
                logger.error("Timeout occurred as expected: ", e);
            } else {
                logger.error("Exception occurred: ", e);
                Assert.fail("Test failed due to unexpected exception: " + e.getMessage());
            }
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}