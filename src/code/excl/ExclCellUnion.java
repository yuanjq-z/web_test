package code.excl;

import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

public class ExclCellUnion {

	/**
	 * @param args
	 * @return void
	 */
	public static void main(String[] args) 
	{
		HSSFWorkbook excl = new HSSFWorkbook();
		HSSFSheet excl_sheet = excl.createSheet("测试单元格合并");
		HSSFCell excl_cell = excl_sheet.createRow(0).createCell(0);
		excl_cell.setCellValue(new Date());
		HSSFCell excl_cell2 = excl_sheet.createRow(2).createCell(1);
		excl_cell2.setCellValue(new Date());
		
		excl_cell2 = excl_sheet.createRow(1).createCell(8);
		excl_cell2.setCellValue(new Date());
		
		// 合并单元格操作
		excl_sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 7)); // row：0到1行，colum：0到7列
//		excl_sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 7));
//		excl_sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 3));

		
//		HSSFCellStyle cell_style = excl.createCellStyle();
//		
//		excl_cell.setCellStyle(cell_style);
		
		try 
		{
			FileOutputStream file = new FileOutputStream("C:/Users/qiqicode/Documents/test.xls");
			excl.write(file);
			file.flush();
			file.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
