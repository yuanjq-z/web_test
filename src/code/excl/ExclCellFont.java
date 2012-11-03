package code.excl;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExclCellFont 
{

	/**
	 * @param args
	 * @return void
	 */
	public static void main(String[] args) 
	{
		HSSFWorkbook excl = new HSSFWorkbook();
		HSSFCell excl_cell = excl.createSheet("测试FONT").createRow(2).createCell(2);
		excl_cell.setCellValue("我在测试我在测试我在测试我在测试我在测试我在测试");
		
		HSSFFont font = excl.createFont();                  // 设置单元格内的文字内容：
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  		// 加粗
		font.setColor(HSSFFont.COLOR_RED);             		// 颜色
		// font.setFontHeight((short)300);                  // 字体大小   基本点高度
		font.setFontHeightInPoints((short)20);        		// 字体大小  字号
		font.setFontName(HSSFFont.FONT_ARIAL); 				// 字体名，如"黑体"
		font.setItalic(true); 								// 倾斜
		font.setStrikeout(true);  							// 删除线
		font.setTypeOffset((short)-1);						// 1:字体上移    2:字体下移
		font.setUnderline(HSSFFont.U_DOUBLE_ACCOUNTING);  	// 底部 下划线类型（双线）
		HSSFCellStyle cell_style = excl.createCellStyle();
		cell_style.setFont(font);
		excl_cell.setCellStyle(cell_style);
		
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
