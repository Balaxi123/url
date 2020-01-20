package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReadWrite 
{
	
	XSSFWorkbook wb = null;
	
	// To establish the excel connection
	public ExcelReadWrite(String path)
	{
		try 
		{
			File src = new File(path);
			FileInputStream fis = new FileInputStream(src);
			wb = new XSSFWorkbook(fis);
			if(wb != null)
			{
				System.out.println("Excel connection established successfully");
			}
			fis.close();	
		} 
		catch (Exception e) 
		{
			System.out.println("Exception is "+e.getMessage());
		}
	}
	
	// To get excel Row count when sheet name is passed
	public int getXlRowCount(String sheetName)
	{
		int lastRowNum = wb.getSheet(sheetName).getLastRowNum();
		return lastRowNum;
	}
	
	// To get excel Row count when sheet index is passed
	public int getXlRowCount(int sheetIndex)
	{
		int lastRowNum = wb.getSheetAt(sheetIndex).getLastRowNum();
		return lastRowNum;
		
	}
	
	//To get excel Column count when  sheet name is passed.
	public int getXlColumnCount(String sheetName)
	{
		int lastCellNum = wb.getSheet(sheetName).getRow(0).getLastCellNum();
		return lastCellNum;
	} 
	
	// to get excel Column count when sheet index is passed.
	public int getXlColumnCount(int sheetIndex)
	{
		int lastCellNum = wb.getSheetAt(sheetIndex).getRow(0).getLastCellNum();
		return lastCellNum;
	}
	
	// to read the data from the excel sheet when column index is passed
	public String readXlData (String sheetName, int rowIndex, int columnIndex)
	{
		String cellText = null;
		XSSFCell cell = wb.getSheet(sheetName).getRow(rowIndex).getCell(columnIndex);
		cellText = cell.getStringCellValue();
		return cellText;
	}
	
	// to read the data from the excel sheet when column name is passed
	public String readXlData (String sheetName, int rowIndex, String columnName)
	{
		int columnCount = getXlColumnCount(sheetName);
		int columnIndex = 0;
		String cellText = null;
		for(int i=0; i<columnCount; i++)
		{
			String columnValue = wb.getSheet(sheetName).getRow(0).getCell(i).getStringCellValue();
			if(columnValue.equalsIgnoreCase(columnName))
			{
				columnIndex = i;
				cellText = readXlData(sheetName, rowIndex, columnIndex);
				break;
			}
		}
		return cellText;
	}
	
	// to write data into excel
	public void writeXlData(String sheetName, int rowIndex, int columnIndex, String value)
	{
		XSSFRow row = wb.getSheet(sheetName).getRow(rowIndex);
		if(row == null)
		{
			row = wb.getSheet(sheetName).createRow(rowIndex);
		}
		XSSFCell cell = row.getCell(columnIndex);
		if(cell == null)
		{
			cell = row.createCell(columnIndex);
		}
		cell.setCellValue(value);
	}
	
	// to save the excel sheet
	public void saveXlFile(String path)
	{
		FileOutputStream fout = null;
		try 
		{
			fout = new FileOutputStream(path);
		} 
		catch (Exception e) 
		{
			System.out.println("Exception is "+e.getMessage());
		}
		try 
		{
			wb.write(fout);
			fout.close();
		} 
		catch (Exception e)
		{
			System.out.println("eXCEPTION IS "+e.getMessage());
		}
	}
}