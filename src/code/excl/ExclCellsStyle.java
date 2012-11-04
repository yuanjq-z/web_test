package code.excl;

import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExclCellsStyle {

	/**
	 * 文档参考：
	 * http://www.javadocexamples.com/java_examples/org/apache/poi/hssf/usermodel/HSSFCellStyle/
	 * @param args
	 * @return void
	 */
	public static void main(String[] args) 
	{
		HSSFWorkbook excl = new HSSFWorkbook();
		HSSFCell excl_cell = excl.createSheet("测试FONT").createRow(2).createCell(2);
		excl_cell.setCellValue(new Date());
		
		HSSFCellStyle cell_style = excl.createCellStyle();
		
		cell_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);								// 水平：单元格对齐模式  （居中）
		cell_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP); 						// 垂直：单元格垂直居中
		cell_style.setBorderBottom(HSSFCellStyle.BORDER_DASH_DOT);							// 底部边框
		cell_style.setBorderLeft(HSSFCellStyle.BORDER_DASH_DOT_DOT);						// 左边框
		cell_style.setBorderRight(HSSFCellStyle.BORDER_DASHED); 							// 右边框
		cell_style.setBorderTop(HSSFCellStyle.BORDER_DOTTED); 								// 上边框
		cell_style.setBottomBorderColor(HSSFColor.BLUE.index); 								// 设置颜色
		cell_style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm")); 			// 设置时间格式
		cell_style.setFillBackgroundColor(HSSFColor.GREEN.index); 							// 背景填充色
//		cell_style.setFillPattern(cell_style.BIG_SPOTS);									// 设置单元格网线
//		cell_style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index); 					// 前景填充色
//		cell_style.setFillPattern(cell_style.BIG_SPOTS);
//		cell_style.setHidden(true);
//		cell_style.setLocked(true);
//		cell_style.setRotation((short)2);
//		cell_style.setUserStyleName("123");
		cell_style.setWrapText(true);														// 鼠标悬停显示内容
		
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
