package testcases;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import utility.ConfigurationDataProvider;
import utility.Email;
import utility.ExcelReadWrite;
import utility.WaImageGroupSender;
import utility.WaTextGroupSender;

public class VerifyURLStatus 
{
	//private static final String TakeScreenshot = null;
	String browserType;
	WebDriver driver;
	
	@Test
	public void verifyURLStatus() throws Exception
	{
		// Initialize Extent report
		ExtentHtmlReporter htmlreporter = new ExtentHtmlReporter("./Reports/URL_Status.html");
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(htmlreporter);
		ExtentTest test = extent.createTest("Verify Client URL Status", "Verifing all the clients url's are up and runing");
		test.assignAuthor("Balaxi");
		
		// Initialize configuration file
		ConfigurationDataProvider config = new ConfigurationDataProvider();
		String smtphost = config.getSMTPHostName();
		String smtpport = config.getSMTPHostNumber();
		String from = config.getFromAddress();
		String password = config.getFromAddressPassword();
		String to = config.getToAddress();
		String cc = config.getCCAddress(); 
		
		new WaImageGroupSender();
		String[] waConfigurationData = config.getWaConfiguration();
		String instanceID = waConfigurationData[0];
		// Chrome configuration
		String chromePath = config.getChromeDriver();
		System.setProperty("webdriver.chrome.driver", chromePath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("window-size-1400,800");
		options.addArguments("headless");
		driver = new ChromeDriver(options);
		
		// Initialize excel read/write and reading the excel
		ExcelReadWrite xl = new ExcelReadWrite(config.getExcelPath());
		int rowCount = xl.getXlRowCount(config.getSheetName());
		System.out.println("Row count is "+rowCount);
		int columnCount = xl.getXlColumnCount(config.getSheetName());
		System.out.println("Column count is "+columnCount);
		

		for(int i=1; i<rowCount+1; i++)
		{
			for(int j=0; j<1; j++)
			{
				String url = xl.readXlData(config.getSheetName(),i,j);
				String product = xl.readXlData(config.getSheetName(), i, "Product");
				String appServer = xl.readXlData(config.getSheetName(), i, "ApplicationServer");
				String dbServer = xl.readXlData(config.getSheetName(), i, "DatabaseServer");
				int statuscode = verifyLinkActive(url);
				if(statuscode == 200)
				{
					test.pass(i+" This is an "+product+" product hosted on Application server - "+appServer+" Database server - "+dbServer+" and it's URL is <a href="+url+">"+url+"</a> "+statuscode);
					System.out.println(i+" This is an "+product+" product hosted on Application server - "+appServer+" Database server - "+dbServer+" and it's URL is <a href="+url+">"+url+"</a> "+statuscode);
				}
				else
				{
					test.fail(i+" This is an "+product+" product hosted on Application server - "+appServer+" Database server - "+dbServer+" and it's URL is <a href="+url+">"+url+"</a> "+statuscode);
					System.out.println(i+" This is an "+product+" product hosted on Application server - "+appServer+" Database server - "+dbServer+" and it's URL is <a href="+url+">"+url+"</a> "+statuscode);
					//Path verifyLinkActiveUI = Paths.get(takeScreenshot(url, i));
					//byte[] imageBytes = Files.readAllBytes(verifyLinkActiveUI);
					//String message = ("This is an "+product+" product hosted on Application server - "+appServer+" Database server - "+dbServer+" and it's URL is "+url+" "+statuscode);
					//WaTextGroupSender.sendGroupMessage(instanceID, clientID, clientSecret, waGatewayURL, groupAdmin, groupName, message);
					//waImageGroupSender.sendPhotoGroupMessage(groupName, imageBytes, message, waGatewayURL, clientID, clientSecret);
				}
			}
		}

		extent.flush();
		String URL_Test_Report = "./Reports/URL_Status.html";
		Email email = new Email(); 
		email.sendMail(smtphost, smtpport, from, password, to, cc, URL_Test_Report,"URL Status Verification Report");
	}
	
	public int verifyLinkActive(String testUrl)
	{
//		int code = 0;
//		
//		try
//		{
//			URL url = new URL(testUrl);
//			HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
//	        httpURLConnect.setConnectTimeout(4000);
//	        httpURLConnect.setRequestMethod("GET");
//	        httpURLConnect.connect();
//	        code = httpURLConnect.getResponseCode();
//		}
//		catch(Exception e)
//		{
//			//e.printStackTrace();
//			System.out.println("Exception is "+e.getMessage());
//		}
//		return code;
		HttpURLConnection conn = null;
		try {
		    URL url = new URL(testUrl);

		    // (set connection and read timeouts on the connection)
		    conn = (HttpURLConnection)url.openConnection();

		    return conn.getResponseCode();

		} catch (Exception ex) {
			return 0;
		} finally {
		    if (conn != null) {
		        conn.disconnect();
		    }
		}
	}
	
	public String takeScreenshot(String url, int name)
	{
		
		driver.get(url);
		TakesScreenshot capture = (TakesScreenshot) driver;
		File src = capture.getScreenshotAs(OutputType.FILE);
		String dest = "./Screenshots/"+name+".png";
		File destination = new File(dest);
		try 
		{
			FileUtils.copyFile(src, destination);
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		return dest;
	}
	
}
