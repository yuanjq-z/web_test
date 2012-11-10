package code.util.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;


/**
 * http的一些接口响应获取
 *
 * @author yuanjq
 * @date 2012-10-18
 */
public class GetHttpResponseContent 
{
	// http请求帮助类
	private static HttpClientHelper http;
	
	/**
	 * 初始化http客户端实例
	 * 
	 * @return void
	 */
	private static void initHttp()
	{
		http = HttpClientHelper.getInstance();
		http.createHttpClient();
		http.SetConnectionTimeOut(5000);
		http.SetSoTimeOut(2000);
	}
	
	/**
	 * 请求类型：POST
	 * 返回数据类型：String
	 * 
	 * @param url
	 * @param params
	 * @return String
	 */
	public static String getPostStringRes(String url, Map<String, String> params)
	{
    	try 
    	{
    		initHttp();
			PostMethod postMethod =  new UTF8PostMethod(url);
			NameValuePair[] nameValue = new NameValuePair[params.size() > 0 ? params.size() : 1];
			if (params != null && !params.isEmpty()) 
			{
				Iterator<Entry<String, String>> iter = params.entrySet().iterator();
				while (iter.hasNext()) 
				{
					Entry<String, String> entry = iter.next();
					String key = entry.getKey();
					String val = entry.getValue();
					postMethod.addParameter(new NameValuePair(key, val));
				}
			}
			
    		Integer statueValue = http.doPost(postMethod); 
    		System.out.println("RESPONSE:" + statueValue.toString() + " " + postMethod.getURI().toString());
    		if(statueValue == 200)
        	{
    	    	try 
    	    	{
    				String temp = postMethod.getResponseBodyAsString();
    				return temp;
    			} 
    	    	catch (IOException e) 
    			{
    	    		System.out.println("返回数据出错:" + e.getMessage());
    				return null;
    			}
        	}
		} catch (Exception e) 
		{
			System.out.println("请求连接出错:" + e.getMessage());
			return null;
		}
    	
    	return null;
	}
	
	/**
	 * 请求类型：GET
	 * 返回数据类型：String
	 * 
	 * @param url
	 * @return String
	 */
	public static String getGetStringRes(String url)
	{
    	try 
    	{
    		initHttp();
    		GetMethod getMethod =  new UTF8GetMethod(url);
    		Integer statueValue = http.doGet(getMethod);
    		System.out.println("RESPONSE:" + statueValue.toString() + " " + getMethod.getURI().toString());
			
			if(statueValue == 200)
	    	{
		    	try 
		    	{
					String temp = getMethod.getResponseBodyAsString();
					return temp;
				} 
		    	catch (IOException e) 
				{
		    		System.out.println("返回数据出错:" + e.getMessage());
					return null;
				}
	    	}
		} catch (Exception e) 
		{
			System.out.println("请求连接出错:" + e.getMessage());
			return null;
		}
    	
    	return null;
	}
	
	/**
	 * 请求类型：POST
	 * 返回数据类型：Json
	 * 
	 * @param url
	 * @param params
	 * @return String
	 */
	public static JSONObject getPostJsonRes(String url, Map<String, String> params)
	{
    	try 
    	{
    		initHttp();
    		PostMethod postMethod =  new UTF8PostMethod(url);
    		NameValuePair[] nameValue = new NameValuePair[params.size() > 0 ? params.size() : 1];
    		if (params != null && !params.isEmpty()) 
    		{
    			Iterator<Entry<String, String>> iter = params.entrySet().iterator();
    			while (iter.hasNext()) 
    			{
    				Entry<String, String> entry = iter.next();
    				String key = entry.getKey();
    				String val = entry.getValue();
    				postMethod.addParameter(new NameValuePair(key, val));
    			}
    		}
    		
    		Integer statueValue = http.doPost(postMethod); 
    		System.out.println("RESPONSE:" + statueValue.toString() + " " + postMethod.getURI().toString());
			
			if(statueValue == 200)
	    	{
		    	try 
		    	{
					String temp = postMethod.getResponseBodyAsString();
					JSONObject jsonObject = null;
					try 
					{
						jsonObject = new JSONObject().parseObject(temp); 
					}
					catch (JSONException e)
					{
						System.out.println("json转换错误");
					}
					return jsonObject;
				} 
		    	catch (IOException e) 
				{
		    		System.out.println("返回数据出错:" + e.getMessage());
					return null;
				}
	    	}
		} catch (Exception e) 
		{
			System.out.println("请求连接出错:" + e.getMessage());
			return null;
		}
    	
    	return null;
	}
	
	/**
	 * 请求类型：GET
	 * 返回数据类型：Json
	 * 
	 * @param url
	 * @return String
	 */
	public static JSONObject getGetJsonRes(String url)
	{
		try
		{
			initHttp();
			GetMethod getMethod =  new UTF8GetMethod(url);
	    	Integer statueValue = http.doGet(getMethod); 
	    	System.out.println("返回数据" + statueValue.toString());
	    	
	    	if(statueValue == 200)
	    	{
		    	try 
		    	{
					String temp = getMethod.getResponseBodyAsString();
					JSONObject jsonObject = null;
					try 
					{
						jsonObject = new JSONObject().parseObject(temp); 
					}
					catch (JSONException e)
					{
						System.out.println("json转换错误");
						return null;
					}
					return jsonObject;
				} 
		    	catch (IOException e) 
				{
		    		System.out.println("返回数据出错:" + e.getMessage());
					return null;
				}
	    	}
		} catch (Exception e)
		{
			System.out.println("请求连接出错:" + e.getMessage());
			return null;
		}
    	return null;
	}

	public static void main(String[] args) 
	{
		Map<String, String> params = new HashMap<String, String>();
		//params.put("keyword", "细胞");
		//params.put("limit", "5");
		//http://192.168.200.54:8081/webservices/biomartSearch/findAskPriceTop5
		//http://192.168.200.141:8080/biomartSearch/webservices/biomartSearch/findAskPriceTop5
		String url = "http://www.biomart.net/webservices/session/demand/getJoinNoById/3";
		url = "http://192.168.200.141:8080/biomartSearch/webservices/biomartSearch/findAskPriceTop5";
		
		url = "http://www.dxy.cn";
		
		url = "http://www.biomart.cn/info/tryViewAjax.htm?action=AjaxGetTryApplyList&try_id=560&page=2&limit=40&_=1350579634554";
		
		params.put("id", "8");
		url = "http://www.biomart.net/webservices/session/demand/getJoinNoById";
//		url = "http://localhost:8080/ebuy/webservices/session/demand/getJoinNoById/8";
		
//		url = "http://www.biomart.net/webservices/session/demand/getJoinNoById";
		url = "http://www.biomart.net/info/tryViewAjax.htm?action=AjaxGetTryApplyList&try_id=548&page=1000&limit=5&_=1350624375415";
		url = "http://localhost:8080/ebuy/info/tryViewAjax.htm?action=AjaxGetTryApplyList&try_id=514&page=199991&limit=5&_=1350624375415";
//		url = "http://localhost:8080/ebuy/info/tryViewAjax.htm?action=AjaxGetTrySuccessList&try_id=548&page=199991&limit=5&_=1350624375415";
//		
//		url = "http://www.biomart.cn/webservices/biomartSearch/findAskPriceTop5";
//		
//		// url = "http://www.biomart.net/webservices/search/getArticlesByParams";
//		
//		params.put("keyword", "PCR耗材");
//		params.put("limit", "5");
		
//		params.put("articleType", "experiment");
//		params.put("keyword", "实验");
//		params.put("expType", "2");
//		params.put("limit", "4");
		
		String temp = GetHttpResponseContent.getPostStringRes(url, params).toString();
		System.out.println(temp);
		
		temp = GetHttpResponseContent.getPostJsonRes(url, params).toString();
		System.out.println(temp);
		
		temp = GetHttpResponseContent.getGetStringRes(url).toString();
		System.out.println(temp);
	}
}
