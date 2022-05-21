package com.dell.automation;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public abstract class BaseTest {

    public WebDriver driver;

    @BeforeSuite
    public void startDriver() {
        String path = System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", path);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        WebDriver.Options manage = driver.manage();
        manage.window().setSize(new Dimension(1024, 768));
//        manage.window().maximize();
        manage.timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @AfterSuite
    public void quitDriver() {
        driver.quit();
    }
}
