package code.excl;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExclSimple 
{	
	/**
	 * @param args
	 * @return void
	 */
	public static void main(String[] args) 
	{
		HSSFWorkbook excl = new HSSFWorkbook();
		HSSFSheet excl_sheet_60 = excl.createSheet("60日统计");
		HSSFSheet excl_sheet_13 = excl.createSheet("13月统计");
		HSSFSheet excl_sheet_5 = excl.createSheet("5季度统计");
		
		HSSFRow row_60_1 = excl_sheet_60.createRow(1);
		HSSFCell cell_60_1 = row_60_1.createCell(1);
		
		cell_60_1.setCellValue("test");
		
		
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream("C:/Users/qiqicode/Documents/test.xls");

			excl.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
