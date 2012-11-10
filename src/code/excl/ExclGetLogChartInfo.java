package code.excl;

import java.io.FileOutputStream;
import java.util.Date;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import code.util.http.GetHttpResponseContent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ExclGetLogChartInfo 
{
	public static void main(String[] args) 
	{
		
		HSSFWorkbook excl = new HSSFWorkbook();
		HSSFSheet excl_sheet_60 = excl.createSheet("按日统计");
		HSSFSheet excl_sheet_13 = excl.createSheet("按月统计");
		HSSFSheet excl_sheet_5 = excl.createSheet("按季度统计");
		
		String url = "http://www.biomart.net/user/info/reqestLogChartAJAX.do?action=AjaxGetChartData&user_id=4028487718b62e0a0118ba1ad5e50126&chart_type=0";
		getExcl(excl_sheet_60, url, "最近60天访问图表");
		
		url = "http://www.biomart.net/user/info/reqestLogChartAJAX.do?action=AjaxGetChartData&user_id=4028487718b62e0a0118ba1ad5e50126&chart_type=1";
		getExcl(excl_sheet_13, url, "最近13个月访问图表");
		
		url = "http://www.biomart.net/user/info/reqestLogChartAJAX.do?action=AjaxGetChartData&user_id=4028487718b62e0a0118ba1ad5e50126&chart_type=2";
		getExcl(excl_sheet_5, url, "最近5个季度访问图表");
				
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
	
	public static void getExcl(HSSFSheet sheet, String url, String title)
	{
		int i = 0;
		HSSFRow row = sheet.createRow(i);
		row.createCell(0).setCellValue("报表生成时间");
		row.createCell(1).setCellValue(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		i = i + 1;
		
		row = sheet.createRow(i);
		row.createCell(0).setCellValue("报表名称");
		row.createCell(1).setCellValue(title);
		i = i + 1;
		
		sheet.createRow(i);
		i = i + 1;
		
		row = sheet.createRow(i);
		row.createCell(0).setCellValue("日期");
		row.createCell(1).setCellValue("访问量");
		i = i + 1;
		
		JSONObject json = GetHttpResponseContent.getGetJsonRes(url);
		if(json != null)
		{
			JSONArray days = json.getJSONArray("days");
			JSONArray pvs = json.getJSONArray("pvs");
			for(int j = 0; j < days.size(); j ++, i ++)
			{
				row = sheet.createRow(i);
				row.createCell(0).setCellValue((String)days.get(j));
				row.createCell(1).setCellValue(pvs.get(j).toString());
			}
		}
	}
}
