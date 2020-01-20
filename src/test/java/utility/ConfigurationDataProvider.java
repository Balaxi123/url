package utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationDataProvider 
{
	FileInputStream fis;
	Properties pro;
	
	//to establish config file
	public ConfigurationDataProvider()
	{
		String path = "E:\\automation\\com.url\\Configuration\\configu.properties";
		File src = new File(path);
		try 
		{
			fis = new FileInputStream(src);
			pro = new Properties();
			pro.load(fis);
			if(pro != null)
			{
				System.out.println("Config file connection is established successfully");
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Exception is "+e.getMessage());
		}
	}
	
	// to get path of Application Test Data file
	public String getExcelPath()
	{
		String path = pro.getProperty("Testdata");
		return path;
	}
	
	// to get excel sheet name
	public String getSheetName()
	{
		String sheetname = pro.getProperty("sheetname");
		return sheetname;
	}
	
	// to get path of gecko drive
	public String getGeckoDriver()
	{
		String path = pro.getProperty("Geckopath");
		return path;
	}
	
	// to get path of Chrome driver
	public String getChromeDriver()
	{
		String path = pro.getProperty("Chrome_Path");
		return path;
	}
	
	// to get path of IE driver
	public String getIEDriver()
	{
		String path = pro.getProperty("IEpath");
		return path;
	}
	
	// to get browser path
	public String getBrowserType()
	{
		String browser = pro.getProperty("Browsertype");
		return browser;
	}
	
	// to get smtp host name
	public String getSMTPHostName()
	{
		String smtphostname = pro.getProperty("smtphostname");
		return smtphostname;
	}
	
	// to get smtp host port
	public String getSMTPHostNumber()
	{
		String smtpportnumber = pro.getProperty("smtpportnumber");
		return smtpportnumber;
	}
	
	// to get list of To address
	public String getToAddress()
	{
		String to = pro.getProperty("To");
		return to;
	}
	
	// to get list of CC address
	public String getCCAddress()
	{
		String cc = pro.getProperty("CC");
		return cc;
	}
	
	// to get from address
	public String getFromAddress()
	{
		String FromAddress = pro.getProperty("FromAddress");
		return FromAddress;
	}
	
	// to get from address password
	public String getFromAddressPassword()
	{
		String FromAddressPassword = pro.getProperty("FromAddressPassword");
		return FromAddressPassword;
	}
	
	public String[] getWaConfiguration()
	{
		String insatnaceId = pro.getProperty("InstanceID");
		String clientID = pro.getProperty("ClientID");
		String clientSecret = pro.getProperty("ClientSecret");
		String waGatewayURL = pro.getProperty("WaGatewayURL");
		String waGroupAdmin = pro.getProperty("WaGroupAdmin");
		String waGroupName = pro.getProperty("WaGroupName");
		String waConfiguration[] = new String[6]; 
		waConfiguration[0] = insatnaceId;
		waConfiguration[1] = clientID;
		waConfiguration[2] = clientSecret;
		waConfiguration[3] = waGatewayURL;
		waConfiguration[4] = waGroupAdmin;
		waConfiguration[5] = waGroupName;
		
		return waConfiguration;
	}
	
}
