package project;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.utils.FileUtil;

public class ExtendReportGen {
public WebDriver driver;
public ExtentReports extentreport;
public ExtentHtmlReporter htmlreporter;
public ExtentTest testcase;
@BeforeMethod
public void launchbrowser() {
	System.setProperty("webdriver.chrome.driver", "/Users/nasar/Downloads/chromedriver");
	WebDriver driver=new ChromeDriver();
	driver.get("https://opensource-demo.orangehrmlive.com/");
	driver.manage().window().maximize();
}
@BeforeTest
public void extentreportgen() {
	extentreport=new ExtentReports();
	extentreport.setSystemInfo("Environment", "Automation");
	extentreport.setSystemInfo("Host Name", "Local Host");
	extentreport.setSystemInfo("Browser Name", "Chrome");
	extentreport.setSystemInfo("OS", "Mac-OS");
	extentreport.setSystemInfo("Tester Name", "Nasar F");
	htmlreporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/ExtentReport.html");
	htmlreporter.loadXMLConfig("extent-config.xml");
	extentreport.attachReporter(htmlreporter);
}
@Test(priority = 0)
public void loginorangehrm() throws InterruptedException {
	testcase=extentreport.createTest("Login Orange HRM Test Case");
	WebElement username = driver.findElement(By.id("txtUsername"));
	username.sendKeys("Admin");
	WebElement password = driver.findElement(By.id("txtUsername"));
	password.sendKeys("admin123");
	WebElement login_button = driver.findElement(By.id("btnLogin"));
	login_button.click();
	String home_url = driver.getCurrentUrl();
	String expectedurl="https://opensource-demo.orangehrmlive.com/index.php/dashboard";
	Assert.assertEquals(home_url, expectedurl);
	System.out.println("Login Successfull: "+home_url);
}
@Test(priority = 1)
public void verifypendingleaverequest() {
	testcase=extentreport.createTest("Verfiying Pending Leave Request Test Case");
	WebElement verifytext = driver.findElement(By.xpath("//*[@id=\"task-list-group-panel-menu_holder\"]/table/tbody/tr/td"));
	String actualtext=verifytext.getText();
	String expectedtext="No Records are Available";
	Assert.assertEquals(actualtext, expectedtext);
	System.out.println("Text Verified text matched with expected text:Passed");
}
@Test(priority = 2)
public void verifytitleofmaintenance() {
testcase=extentreport.createTest("Verify Title and Maintenance page navigated check test case");
WebElement clickonmaintenance = driver.findElement(By.xpath("//*[@id=\"menu_maintenance_purgeEmployee\"]/b"));
clickonmaintenance.click();
WebElement displayed = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/h1"));
Boolean check = displayed.isDisplayed();
Assert.assertTrue(check);
System.out.println("Maintanance page clicked and it is navigated to maintenance page successfully");
}
@AfterMethod
public void teardown(ITestResult result) throws IOException {
	if(result.getStatus()==ITestResult.FAILURE) {
		testcase.log(Status.FAIL, "Test Case Failed "+result.getName());
		testcase.log(Status.FAIL, "Test Case Failed "+result.getThrowable());
		String screenshotpath=ExtendReportGen.getscreenshot(driver, result.getName());
	}
	else if(result.getStatus()==ITestResult.SUCCESS) {
		testcase.log(Status.PASS, "Test Case Passes "+result.getName());
	}
	else if(result.getStatus()==ITestResult.SKIP) {
		testcase.log(Status.SKIP, "Test Case Skipped "+result.getName());
	}
	driver.quit();
}
@AfterTest
public void afterextentreport() {
	extentreport.flush();
}
public static String getscreenshot(WebDriver driver,String screenshotname) throws IOException {
	String dateformate=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	TakesScreenshot ts = (TakesScreenshot) driver;
	File Sourcefile= ts.getScreenshotAs(OutputType.FILE);
	String destination = System.getProperty("user.dir")+"/test-output/"+"/screenshots/" + screenshotname + dateformate + ".png";
	File destinationfile=new File(destination);
	FileUtils.copyFile(Sourcefile, destinationfile);
	return destination;
}

}
