package testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;

public class BaseClass {

	
	public static WebDriver driver;
	// public String key="webdriver.chrome.driver";
	// public String value="D://New folder//chrome//chromedriver.exe";

	@BeforeTest
	public void browserLogin() {
		System.setProperty("webdriver.chrome.driver", "E:\\automation\\selenium\\Chrome driver\\chromedriver.exe");
		driver = new ChromeDriver();
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			driver.manage().window().fullscreen();
		}

	}

	

	public void Login() throws InterruptedException {
		driver.get("https://2p0.talentrecruit.com/");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='TR_Header_rptrHeader_lnkMenu_1']")).click();
		driver.findElement(By.xpath("//*[@id='MainContent_txtUserName']")).sendKeys("balaxi");
		driver.findElement(By.xpath("//*[@id='MainContent_txtPassword']")).sendKeys("Pallu579@");
		driver.findElement(By.xpath("//input[@id='MainContent_btnLogin']")).click();
		
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("window.scrollBy(0,5000)");
		try {
			Alert al = driver.switchTo().alert();
			al.accept();
		} catch (Exception e) {
			System.out.println("Dint get the Alert");
		}

			
		

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	//Thread.sleep(2000); 

		
	    
		driver.get("https://2p0.talentrecruit.com/Recruiter/AddUpdateLead.aspx");
		String str=driver.findElement(By.xpath("//*[@id='popupcontent']/div[2]/div[1]")).getText();
		
		System.out.println(str);
		
		}
	
}