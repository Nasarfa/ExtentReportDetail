package project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Example {
	public class Testing {
		public WebDriver driver;
		@BeforeSuite
		public void browserlaunch() {
			System.setProperty("webdriver.chrome.driver", "/Users/nasar/Downloads/chromedriver");
			WebDriver driver=new ChromeDriver();
			driver.get("https://opensource-demo.orangehrmlive.com/");
			driver.manage().window().maximize();
		}
		@Test
		public void loginorangehrm() {
			WebElement username = driver.findElement(By.id("txtUsername"));
			username.sendKeys("Admin");
			WebElement password = driver.findElement(By.id("txtPassword"));
			password.sendKeys("admin123");
			WebElement login_button = driver.findElement(By.id("btnLogin"));
			login_button.click();
		}
		@AfterSuite
		public void teardown() {
			driver.quit();
		}
	}
}
