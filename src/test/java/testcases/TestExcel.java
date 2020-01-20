package testcases;

import utility.ExcelReadWrite;

public class TestExcel 
{
	static String excelpath = "E:\\automation\\com.url\\Application Testdata\\List_Client_URL.xlsx";
	static String sheetname = "sheet1";
	
	public static void main(String[] args) 
	{
		ExcelReadWrite xl = new ExcelReadWrite(excelpath);
		int rowcount =xl.getXlRowCount(sheetname);
		int columncount = xl.getXlColumnCount(sheetname);
		for(int i=1; i<rowcount; i++ )
		{
			for(int j=0; j<columncount; j++)
			{
				String url = xl.readXlData(sheetname, i, j);
				System.out.println(url);
			}
			
		}

	}

}
