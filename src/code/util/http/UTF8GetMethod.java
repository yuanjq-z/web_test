package code.util.http;

import org.apache.commons.httpclient.methods.GetMethod;


/**
 * @author YunChang
 * @version: 2011-12-14 下午06:47:03
 */
public class UTF8GetMethod extends GetMethod
{
	public UTF8GetMethod(String url)
	{
		super(url);
	}

	@Override
	public String getRequestCharSet()
	{
		// return super.getRequestCharSet();
		return "utf-8";
	}
}
