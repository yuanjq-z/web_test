package code.util.http;

import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author YunChang
 * @version: 2011-12-14 下午06:47:03
 */
public class UTF8PostMethod extends PostMethod
{
	public UTF8PostMethod(String url)
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
