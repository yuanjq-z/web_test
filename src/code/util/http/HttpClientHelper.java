package code.util.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * http请求帮助类
 *
 * @author yuanjq
 * @date 2012-10-18
 */
public class HttpClientHelper
{
	private final static String UTF8 = "UTF-8";
	private final static String GBK = "GBK";
	private static String ENCODING = UTF8;
	private ThreadLocal<HttpClient> threadLocal = new ThreadLocal<HttpClient>();
	private HttpClient client = null ;
	private static HttpClientHelper instance;
	
	/**
	 * 得到类实例
	 * @return
	 * @return HttpClientHelper
	 */
	public static HttpClientHelper getInstance()
	{
		if(instance == null)
		{
			instance = new HttpClientHelper();
		}
		return instance;
	}
	
	/**
	 * 空构造方法
	 */
	private HttpClientHelper(){
		
	}
	
	/**
	 * 设置连接超时时间 毫秒
	 * @param timeout
	 */
	public void SetConnectionTimeOut(int timeout)
	{
		client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
	}
	
	/**
	 * 设置读取返回数据超时时间 毫秒
	 * @param timeout
	 */
	public void SetSoTimeOut(int timeout)
	{
		client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
	}
	
	/**
	 * 创建httpClient实例
	 * @return
	 */
	public HttpClient createHttpClient()
	{
		client = (HttpClient) threadLocal.get();
		if (client == null) 
		{
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			client.getHttpConnectionManager().getParams().setSoTimeout(3000);
			// client.setHttpConnectionFactoryTimeout(20000);
			threadLocal.set(client);
		}
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ENCODING);
		client.getParams().setHttpElementCharset(ENCODING);
		client.getParams().setContentCharset(ENCODING);

		return client ;
	}
	
	/**
	 * 设置代理访问参数
	 * 
	 * @param Map
	 *            <String,Integer> proxyIPMap key=代理访问IP, value=端口
	 * @param flag
	 *            是否需要代理认证 true表示需要认证
	 * @return
	 */
	public void setHttpProxy(Map<String, Integer> proxyIPMap, boolean flag) {
		if (proxyIPMap == null) 
		{
			System.out.println("proxyConfiguration  设置代理user pwd 失败, proxyIPMap 为空");
		} else 
		{
			client.getHostConfiguration().setProxyHost(getProxyHost(proxyIPMap));
		}
		client.getParams().setAuthenticationPreemptive(flag);
	}
	
	/**
	 * 根据参数构造ProxyHost实例
	 * 
	 * @param Map
	 *            <String,Integer> proxyIPMap key=代理访问IP, value=端口
	 * @return ProxyHost
	 */
	public ProxyHost getProxyHost(Map<String, Integer> proxyIPMap) 
	{
		ProxyHost typeProxy = null;
		for (Map.Entry<String, Integer> proxyMap : proxyIPMap.entrySet()) 
		{
			typeProxy = new ProxyHost(proxyMap.getKey(), proxyMap.getValue());
			System.out.println("proxyConfiguration  设置代理IP成功, proxyIP=" + proxyMap.getKey() + "  proxyProt=" + proxyMap.getValue());
			break;
		}
		return typeProxy;
	}

	/**
	  * Post方式发送请求
	  * @param url
	  * @return
	  */
	 public HttpMethod doPost(String url)
	 {
		 HttpMethod post = getPostMethod(url);
		 try 
		 {
			 client.executeMethod(post);
		 } catch (HttpException e) 
		 {
			e.printStackTrace();
			threadLocal.remove();
			post.releaseConnection();
		 } catch (IOException e) 
		 {
			e.printStackTrace();
			threadLocal.remove();
			post.releaseConnection();
		 } 
		 return post ;
	 }
	 
	 /**
	  * 发送文件
	  * @param url
	  * @param key
	  * @param file
	  * @return
	  */
	 public int doPost(HttpMethod post) 
	 {
		 int i = 0;
		 try 
		 {
			 i = client.executeMethod(post);
		 } 
		 catch (HttpException e) 
		 {
			e.printStackTrace();
			threadLocal.remove();
			post.releaseConnection();
		 } 
		 catch (IOException e) 
		 {
			e.printStackTrace();
			threadLocal.remove();
			post.releaseConnection();
		 } 
		 return i; 
	 }
	 
	 public String getResponseBodyString(byte[] res,String encoding)
	 {
		String result = null;
		try {
			result = new String(res,encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 return result ;
	 }
	 
	 public String getResponseBodyString(byte[] res)
	 {
		 String result = null;
		 try {
			 result = new String(res,ENCODING);
		 } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		 }
		 return result ;
	 }
	 
	 /**
	  * Get方式发送请求
	  * @param url
	  * @return
	  */ 
	 public HttpMethod doGet(String url)
	 {
		 return doGet(url, ENCODING);
	 }
	 
	/**
	 * Get方式发送请求 
	 * @param url
	 * @param encoding
	 * @return
	 */
	 public HttpMethod doGet(String url,String encoding)
	 {
		 HttpMethod get = getGetMethod(url);
		 get.getParams().setContentCharset(encoding);  
		 String message = "" ;
		 try 
		 {
			 client.executeMethod(get);
		 } 
		 catch (HttpException e) 
		 {
			 e.printStackTrace();
			 message = url + ",请求超时..." + e.getMessage();
			 threadLocal.remove();
			 get.releaseConnection();
		 } 
		 catch (IOException e) 
		 {
			 e.printStackTrace();
			 message = url + ",请求超时..." + e.getMessage();
			 threadLocal.remove();
			 get.releaseConnection();
		 }
		 finally
		 {
			 System.out.println(message);
		 }
		 return get ;
	 }
	 
	/**
	 * Get方式发送请求 
	 * @param url
	 * @param encoding
	 * @return
	 */
	 public int doGet(HttpMethod get)
	 {
		 int i = 0;
		 String message = "";
		 try 
		 {
			 i = client.executeMethod(get);
		 } 
		 catch (HttpException e) 
		 {
			 e.printStackTrace();
			 message = "请求超时..." + e.getMessage();
			 threadLocal.remove();
			 get.releaseConnection();
		 } 
		 catch (IOException e) 
		 {
			 e.printStackTrace();
			 message = "请求超时..." + e.getMessage();
			 threadLocal.remove();
			 get.releaseConnection();
		 }
		 finally
		 {
			 System.out.println(message);
		 }
		 return i;
	 }
	 

	 /**
	  * get方式提交 设置数据参数
	  * 
	  * @param url
	  * @return HttpMethod
	  */
	 private HttpMethod getGetMethod(String url) 
	 {
		 try{
			 GetMethod getMethod = new GetMethod(url);
			 getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GB2312");  

			 return getMethod;
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return null;
	 }
	
	/**
	 * POST方式发送请求 解析url,设置数据参数
	 * 
	 * @param url
	 * @return HttpMethod
	 */
	private HttpMethod getPostMethod(String url) 
	{
		String host = url;
		if(url.indexOf("?") > -1)
		{
			host = url.substring(0, url.indexOf("?"));
		}else{
			host = url;
		}
		PostMethod postMethod = new PostMethod(host);
		int index = 0;
		if (url.indexOf("?") > -1) 
		{
			String params = url.substring(url.indexOf("?")+1);
			String[] paramarr = params.split("&");
			
			NameValuePair[] nvs = new NameValuePair[paramarr.length];
			for (int i=0;i<paramarr.length ; i++) 
			{
				String[] namevalue = paramarr[i].split("=");
				if(namevalue != null)
				{
					String value = namevalue.length==1?"":namevalue[1];
					/*try {
						value = value.replace("[", URLEncoder.encode("[", "utf-8"));
						value = value.replace("\"", URLEncoder.encode("\"", "utf-8"));
						value = value.replace("]", URLEncoder.encode("]", "utf-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					NameValuePair simcard = new NameValuePair(namevalue[0]==null||"".equals(namevalue[0])?"":namevalue[0], value);
					nvs[index] = simcard;
					// logger.warn("参数名称=" + namevalue[0] + "\n 值=" + value);
					index++;
				}
			}
			postMethod.setRequestBody(nvs);
			System.out.println("POST 参数设置完毕。");
		}
		return postMethod;
	}
}
