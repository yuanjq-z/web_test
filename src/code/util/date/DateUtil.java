package code.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 
 * 和时间处理有关的工具类
 * 
 * @author wangkai
 */
public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	private static String datePattern = "yyyy-MM-dd";
	private static String monthPattern = "yyyy-MM";
	private static String yearPattern = "yyyy";
	private static String timePattern = datePattern + " HH:MM a";
	private static String zeroDatetime = " 00:00 00";
	private static String nightDatetime = " 23:59 59";
	public static String fullPattern = datePattern + " HH:mm:ss";// yyyy-MM-dd
	public static String defaultDatePattern = "yyyy-MM-dd";
	public static String DATE_PATTERN = "yyyy-MM-dd";
	public static String TIME_PATTERN = "HH:mm:ss";
	public static String DATETIME_PATTERN = "yyyy.MM.dd HH:mm";
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    private static long lastMidnightTime = 0;
    public static final long SECOND = 1000L;
    public static final long MINUTE = 60000L;
    public static final long HOUR = 3600000L;
    public static final long DAY = 86400000L;
    public static final long WEEK = 604800000L;
    
    private static Map<String, Object> dateFormatCache = new HashMap<String, Object>();
	/**
	 * Return default datePattern (yyyy-MM-dd)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to yyyy-MM-dd.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate, String format) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(format);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}
	
	public static final Date formatDate(Date aDate, String format) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(format);
			returnValue = df.format(aDate);
		}

		return parse2Date(returnValue);
	}

	public static final Date formatDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(defaultDatePattern);
			returnValue = df.format(aDate);
		}

		return parse2Date(returnValue);
	}
	
	public static final String getDate(Date aDate) {
		return getDate(aDate, datePattern);
	}

	public static final String getDateOfDate(Date aDate) {
		return getDate(aDate, datePattern);
	}

	public static final String getDateOfMonth(Date aDate) {
		return getDate(aDate, monthPattern);
	}

	public static final String getDateOfYear(Date aDate) {
		return getDate(aDate, yearPattern);
	}

	public static final String getZeroDatetime() {
		return getDateOfDate(new Date()) + zeroDatetime;
	}

	public static final String getNightDatetime() {
		return getDateOfDate(new Date()) + nightDatetime;
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		if(strDate != null && strDate.indexOf("CST") != -1) {
			// 判断字符串时间是否是CST格式，是则使用美国地区匹配
			SimpleDateFormat df2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
			Date dateTemp = df2.parse(strDate);
			df2 = new SimpleDateFormat(LONG_DATE_FORMAT);
			strDate = df2.format(dateTemp);
		}
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern);

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(datePattern, aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * 
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		if(StringUtil.isEmpty(strDate))
		{
			return new Date();
		}
		try 
		{
			if (log.isDebugEnabled()) 
			{
				log.debug("converting date with pattern: " + datePattern);
			}
			aDate = convertStringToDate(datePattern, strDate);
		} catch (ParseException pe) 
		{
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return aDate;
	}

	private static SimpleDateFormat shortFormatter = new SimpleDateFormat(
			SHORT_DATE_FORMAT);

	public static String getCurDateTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat longFormatter = new SimpleDateFormat(
				LONG_DATE_FORMAT);
		String mDateTime = longFormatter.format(cal.getTime());
		return mDateTime;
	}

	public static String getCurDate() {
		Calendar cal = Calendar.getInstance();

		String mDateTime = shortFormatter.format(cal.getTime());
		return mDateTime;
	}

	public static String getDate(int delta) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, delta);

		String mDateTime = shortFormatter.format(cal.getTime());
		return mDateTime;
	}

	public static String getDate(int field, int delta) {
		Calendar cal = Calendar.getInstance();
		cal.add(field, delta);

		String mDateTime = shortFormatter.format(cal.getTime());
		return mDateTime;
	}

	public static String getDate(Calendar cal) {

		String mDateTime = shortFormatter.format(cal.getTime());
		return mDateTime;
	}

	/**
	 * 将字符串表示的日期转换为日历类Calendar
	 * 
	 * @param s
	 *            字符串表示的一天，格式例如"2002-02-12"
	 * @return 该字符串表示的Calendar
	 */
	public static Calendar parse2Calendar(String s) {
		Date date = null;
		try {
			date = shortFormatter.parse(s);
		} catch (ParseException e) {
			throw new RuntimeException(s + "的日期的格式错误，正确的格式如：2002-02-12");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		return cal;
	}

	/**
	 * 按照多种格式分析日期数据
	 * 
	 * @param date
	 * @return
	 */
	public static Date parse2Date(String date) {
		if (date == null) {
			throw new RuntimeException();
		}
		Date result = null;
		SimpleDateFormat formatter1 = new SimpleDateFormat(LONG_DATE_FORMAT);
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
		SimpleDateFormat formatter31 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy年MM月dd日");
		SimpleDateFormat formatter41 = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat formatter42 = new SimpleDateFormat("yyyy年MM月dd");
		SimpleDateFormat formatter5 = new SimpleDateFormat(SHORT_DATE_FORMAT);
		SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy/mm/dd");
		SimpleDateFormat formatter7 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat[] f = { formatter1, formatter5, formatter2,
				formatter3, formatter31, formatter4,formatter41,formatter42,formatter6,formatter7 };

		for (int i = 0; i < f.length; i++) {
			try {
				result = f[i].parse(date);
				break;
			} catch (ParseException e) {
				if (log.isDebugEnabled()) {
					
				}
				continue;
			}
		}
		if (result == null) {
			result = new Date();
		}
		return result;
	}

	/**
	 * 将指定的日期字符串转化为日期对象
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return java.util.Date
	 */
	public static Date getDate(String dateStr, String format) {

		if (dateStr == null || format == null) {
			log.debug("数据类型异常" + dateStr + "|" + format);
		}

		SimpleDateFormat df = new SimpleDateFormat(format);

		try {
			Date date = df.parse(dateStr);
			return date;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	/**  
	 * modified by tusury 
	 */
	public static int diffDay(Date date1, Date date2) {
		return (int) ((formatDate(date1).getTime()-formatDate(date2).getTime())/(3600*1000*24));
	} 
	
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofmonth = cal.get(Calendar.DATE);
		cal.add(Calendar.DATE, 1 - dayofmonth);
		return DateUtil.formatDate(cal.getTime(), datePattern);
	}
	
	public static Date getFirstDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofmonth = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, 1 - dayofmonth);
		return DateUtil.formatDate(cal.getTime(), datePattern);
	}

	public static long lastMidnightTimeInMillis() {
        if(new Date().getTime()  - lastMidnightTime > DAY) {
            setMidnightTime();
        }
        return lastMidnightTime;
    }
	
    private static void setMidnightTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        lastMidnightTime = cal.getTime().getTime();
    }
    
    public static String asHtml(Date d) {
        return asHtml(d,TimeZone.getDefault());
     }

    public static String asHtml(Date date,TimeZone timeZone) {
         if(date ==null) {
             return "";
         }
         String key = timeZone.getID();
         DateFormat formatter = (DateFormat)dateFormatCache.get(key);
         if (formatter == null) {
             formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
             formatter.setTimeZone(timeZone);
             dateFormatCache.put(key,formatter);
        }
         synchronized(formatter) {
            return formatter.format(date);
        }
    }
    
    public static String asShortString(Date date) {
        if(date==null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
    
    public static String asShortStringNoDay(Date date) {
    	if(date==null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(date);
    }
    
    public static String asShortStringOnlyDay(Date date) {
    	if(date==null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat("dd");
        return formatter.format(date);
    }

    public static String asShortString(Date date,TimeZone timeZone) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }
    
    public static String asShortHtml(Date date,TimeZone timeZone) {
        if(date ==null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
        if(timeZone!=null)
            formatter.setTimeZone(timeZone);        
        return formatter.format(date);
    }
    
    public static String asCustomString(Date date, String format) {
    	if(date==null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    
    public static String asString(Date date) {
        String key = "asString";
        DateFormat formatter = (DateFormat)dateFormatCache.get(key);
        if(formatter == null) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dateFormatCache.put(key,formatter);
        }

        return formatter.format(date);
    }
    
    public static String asLongHtml(Date date,TimeZone timeZone) {
        if(date ==null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat("E MMM d, yyyy h:mm a");
        if(timeZone!=null)
            formatter.setTimeZone(timeZone);
        return formatter.format(date);
    }
    
    public static int secondsAfter(Date a,Date b) {
        return (int)((a.getTime() - b.getTime())/1000l);
    }
    
    public static int secondsBefore(Date a,Date b) {
        return secondsAfter(b,a);
    }
    
    public static Date currentDate() {
        return new Date();
    }
    
    //获得一个月前的时间
    public static Date getDateBefore(int months) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal.add(Calendar.MONTH, (-1) * months);
		//如果与一个月前的时间不一致，则取当月1号的时间
		if(cal1.getTime().getMonth()+1 != cal.getTime().getMonth()+1){
			cal1.set(Calendar.DAY_OF_MONTH, 1);
			return cal1.getTime();
		}else{
			return cal.getTime();
		}
	}
    
    //一个时间和当前比较
    public static boolean comDate(Date date){
    	if(date == null)
    	{
    		return false;
    	}
    	if(date.after(new Date())){
    		return false;
    	}else{
    		return true;
    	}
    }
    
    public static final String formatDate2(Date aDate, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String returnValue = "";
			if (aDate != null) {
				returnValue = df.format(aDate);
			}
		return returnValue;
	}
}
