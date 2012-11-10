package code.util.date;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author <a href="mailto:rainmanzhu@yahoo.com">Rainman Zhu</a>
 * @version $Id: StringUtil.java,v 1.3 2005/07/31 00:13:38 hugo Exp $
 */
public class StringUtil {
	private final static char[] IllegalEmailChar = { ' ', ',', ';', '!', '#', '$', '%', '^', '&', '*', '(', ')', '[', ']', '{', '}', ':',
			'\"', '\'', '?', '+', '=', '|', '\\' };
	private final static String key = "123456789";
	public static final Pattern UUID_REG = Pattern.compile("^[a-zA-Z0-9\\-]{32}$");
	public final static DateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static DateFormat DATE_FORMATTER = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
	/** 匹配url正则 */
	public final static Pattern URL_REG = Pattern.compile("(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

	public final static Pattern SECONDLEVEL_DOMAIN = Pattern.compile("^[a-z0-9\\-]+$");
	private static final char[] zeroArray = "0000000000000000".toCharArray();

	
	private static Pattern WHITE_LIST_REFFER = Pattern
			.compile("\\.1bang1\\.cn$|\\.biomart\\.cn$|\\.centerwatch\\.cn$|\\.dopubmed\\.cn$|\\.dopubmed\\.com$|\\.dopubmed\\.net$|\\.dxycdn\\.com$|\\.dxy\\.cn$|\\.dxy\\.com$|\\.dxyer\\.cn$|\\.jiansuo\\.net$|\\.jobmd\\.cn$|\\.linkmed\\.com\\.cn$|\\.mdnews\\.cn$|\\.trademd\\.cn$|\\.pubmed\\.cn$|\\.0dxy\\.cn$|\\.guanlannet\\.com$|\\.my39k\\.com$|\\.sps-pumch\\.com$|\\.pharmcro\\.com$|\\.scsyuc\\.cn$|\\.liver-surgery\\.net$|\\.liver-surgery\\.org$|\\.onlinecra\\.com$|\\.seizure\\.org\\.cn$|\\.chinasapa\\.org$|\\.paper\\.com$|\\.dxyer\\.com$|\\.crou\\.cn$|\\.theivyediting\\.com");


	public static boolean isUrl(String value) {
		Matcher matcher = URL_REG.matcher(value);
		return matcher.find();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 锟叫讹拷锟街凤拷锟角凤拷锟斤拷 a-z, A-Z, 0-9, _ 锟斤拷锟?
	 * 
	 * @param str
	 * @return boolean if this String is word.
	 */
	public static boolean isWord(String str) {
		if (str == null) {
			return false;
		}

		byte[] asc = str.getBytes();

		for (int i = 0; i < asc.length; i++) {
			if (!isVisibleChar(asc[i])) {
				return false;
			}
		}

		return true;
	}

	public static boolean isTel(String str) {
		if (str == null) {
			return false;
		}
		String s = "0123456789- +()";
		char[] asc = str.toCharArray();
		for (int i = 0; i < asc.length; i++) {
			if (s.indexOf(asc[i]) == -1) {
				return false;
			}
		}
		return true;
	}

	public static boolean isFloat(String str) {
		if (str == null)
			return false;
		String regx = "^\\d+(\\.\\d+)?$";
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static boolean isNumber(String str) {
		if (str == null) {
			return false;
		}

		char[] asc = str.toCharArray();

		for (int i = 0; i < asc.length; i++) {
			if (!Character.isDigit(asc[i])) {
				return false;
			}
		}

		return true;
	}

	private static boolean isVisibleChar(byte asc) {
		if (((asc >= 48) && (asc <= 57)) || ((asc >= 65) && (asc <= 90)) || ((asc >= 97) && (asc <= 122)) || (asc == 95)) {
			return true;
		}

		return false;
	}

	/**
	 * 去锟斤拷崭锟斤拷
	 * 
	 * @param string_value
	 *            input String
	 * @return String ,return string after remove space char like ' ','\r\,'\n'
	 *         etc.
	 */
	public static String removeWhitespaces(String string_value) {
		if (string_value == null || string_value.equals("")) {
			return string_value;
		} else {
			char[] chars = string_value.toCharArray();
			char[] new_value = new char[chars.length];
			int counter = 0;

			for (int i = 0; i < chars.length; i++) {

				if (!Character.isSpaceChar(chars[i])) {
					new_value[counter++] = chars[i];

				}
			}
			return new String(new_value, 0, counter);
		}
	}

	/**
	 * 锟叫讹拷锟角凤拷锟斤拷Email锟斤拷址
	 * 
	 * @param str
	 * @return true if this string is like email address pattern eg. foo@bar.com
	 */
	public static boolean isEmailAddress(String str) {
		if (str == null || str.length() <= 0) {
			return false;
		}

		// int iCommonCount = 0;
		int iAltCount = 0;
		int j;
		char[] chEmail = str.trim().toCharArray();

		for (int i = 0; i < chEmail.length; i++) {
			j = 0;

			while (j < IllegalEmailChar.length) {
				if (chEmail[i] == IllegalEmailChar[j]) {
					return false; // find illegal char in email address
				}

				if (chEmail[i] > 127) {
					return false; // find HZ in email address
				}
				j++;
			}

			if (chEmail[i] == '.') { // common at the beginning or end of
				// email address
				if (i == 0 || i == chEmail.length - 1) {
					return false;
				}
			} else if (chEmail[i] == '@') { // find '.' more than one,or at the
				// beginning or end of email address
				if (++iAltCount > 1 || i == 0 || i == chEmail.length - 1) {
					return false;
				}
			}
		}

		if (str.indexOf('@') < 1) {
			return false;
		}

		// if (str.indexOf('.') < str.indexOf('@')) {
		// return false;
		// }

		return true;
	}

	/**
	 * 锟斤拷锟斤拷一锟斤拷锟斤拷+1+2+3+4+5锟侥革拷式锟斤拷傻拇锟斤拷锟斤拷锟斤拷锟斤拷小时锟斤拷锟接碉拷锟街凤拷锟斤拷锟斤拷时锟斤拷
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟绞憋拷洌∈憋拷锟斤拷锟斤拷锟饺凤拷械锟街碉拷锟斤拷锟斤拷锟斤拷锟津返回碉拷前时锟戒；
	 * 锟斤拷锟界：-1+2-3+12+30锟斤拷锟斤拷锟斤拷锟斤拷一锟斤拷锟斤拷露锟斤拷锟斤拷碌锟斤拷锟斤拷锟斤拷锟斤拷12:30锟斤拷
	 * 
	 * @param String
	 *            - time : 锟斤拷式锟斤拷锟斤拷时锟斤拷锟街凤拷
	 * @return Date
	 */
	public static Date str2date(String time) {
		if (time == null || time.length() == 0) {
			return new Date();
		}

		String tmp = time;
		tmp = replaceStrEx(tmp, "+", ";");
		tmp = replaceStrEx(tmp, "-", ";-");

		String[] tmp_array = split(tmp.substring(1), ";");

		if (tmp_array.length < 5) {
			return new Date();
		}

		int[] tem_int_array = str2int(tmp_array);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, tem_int_array[0]);
		cal.add(Calendar.MONTH, tem_int_array[1]);
		cal.add(Calendar.DAY_OF_MONTH, tem_int_array[2]);
		cal.set(Calendar.HOUR_OF_DAY, tem_int_array[3]);
		cal.set(Calendar.MINUTE, tem_int_array[4]);

		return cal.getTime();
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷娣斤拷锟絪tr2Date锟斤拷锟斤拷锟斤拷锟?
	 * 
	 * @param Date
	 *            - date
	 * @return String
	 */
	public static String date2Str(Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal_standard = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		StringBuffer sb = new StringBuffer();
		int in = 0;
		String tmp = new String();
		in = cal.get(Calendar.YEAR) - cal_standard.get(Calendar.YEAR);
		tmp = (in >= 0) ? ("+" + in) : String.valueOf(in);
		sb.append(tmp);
		in = cal.get(Calendar.MONTH) - cal_standard.get(Calendar.MONTH);
		tmp = (in >= 0) ? ("+" + in) : String.valueOf(in);
		sb.append(tmp);
		in = cal.get(Calendar.DATE) - cal_standard.get(Calendar.DATE);
		tmp = (in >= 0) ? ("+" + in) : String.valueOf(in);
		sb.append(tmp);
		sb.append("+" + cal.get(Calendar.HOUR_OF_DAY));
		sb.append("+" + cal.get(Calendar.MINUTE));

		return sb.toString();
	}

	/**
	 * Return the replaced string 锟斤拷一锟斤拷锟街凤拷锟斤拷锟芥换锟接达拷锟斤拷 For example: strnew =
	 * replaceEq("hello the world","he","good"); the strnew now is "goodllo
	 * tgood world"; Replace the string with another string,these two string's
	 * length must equal
	 * 
	 * @param sReplace
	 *            锟斤拷锟斤拷锟斤拷锟斤拷婊伙拷锟斤拷锟斤拷锟街凤拷
	 * @param sOld
	 *            锟斤拷要锟斤拷锟芥换锟斤拷锟斤拷址锟?
	 * @param sNew
	 *            锟斤拷要锟芥换锟斤拷去锟斤拷锟街凤拷
	 * @return 锟斤拷锟斤拷锟芥换锟皆猴拷锟斤拷址锟斤拷锟斤拷锟芥换锟斤拷锟缴癸拷锟斤拷锟斤拷锟斤拷原始锟斤拷锟街凤拷
	 */
	public static String replaceStrEx(String sReplace, String sOld, String sNew) {
		if (sReplace == null || sOld == null || sNew == null) {
			return null;
		}

		int iLen = sReplace.length();
		int iLenOldStr = sOld.length();
		int iLenNewStr = sNew.length();

		if (iLen <= 0 || iLenOldStr <= 0 || iLenNewStr < 0) {
			return sReplace;
		}

		// get the first sOld string index
		int[] iIndex = new int[iLen];
		iIndex[0] = sReplace.indexOf(sOld, 0);

		if (iIndex[0] == -1) {
			return sReplace;
		}

		// get all sOld string index
		int iIndexNum = 1; // get the iIndex of all sOld substring

		while (true) {
			iIndex[iIndexNum] = sReplace.indexOf(sOld, iIndex[iIndexNum - 1] + iLenOldStr);

			if (iIndex[iIndexNum] == -1) {
				break;
			}

			iIndexNum++;
		}

		// get all sub string split by sOld,and strored in a vector
		Vector vStore = new Vector();
		String sub = sReplace.substring(0, iIndex[0]);

		if (sub != null) {
			vStore.add(sub);

		}
		int i = 1;

		for (i = 1; i < iIndexNum; i++) {
			vStore.add(sReplace.substring(iIndex[i - 1] + iLenOldStr, iIndex[i]));
		}

		vStore.add(sReplace.substring(iIndex[i - 1] + iLenOldStr, iLen));

		// contact all sub string with sNew
		StringBuffer sbReplaced = new StringBuffer("");

		for (i = 0; i < iIndexNum; i++) {
			sbReplaced.append(vStore.get(i) + sNew);
		}

		sbReplaced.append(vStore.get(i));

		return sbReplaced.toString();
	}

	/**
	 * 锟斤拷莘指锟斤拷指锟斤拷址锟? Return String[],after split Split a String by a
	 * splitter(such as ",","hai",...)
	 * 
	 * @param sStr
	 *            锟斤拷要锟斤拷锟街革拷锟斤拷址锟?
	 * @param sSplitter
	 *            锟街革拷锟?
	 * @return 一锟斤拷锟叫分革拷玫锟斤拷址锟斤拷锟斤拷锟介。锟斤拷锟街革拷失锟杰斤拷锟斤拷锟斤拷null,
	 *         锟斤拷锟斤拷址锟斤拷锟矫伙拷邪锟街革拷锟斤拷姆指锟斤拷
	 *         锟斤拷锟斤拷锟斤拷只锟斤拷一锟斤拷元锟截碉拷锟街凤拷锟斤拷锟介，锟斤拷锟皆
	 *         拷鼐锟斤拷歉锟斤拷址锟斤拷?锟斤拷锟斤拷锟斤拷锟街凤拷只锟斤拷锟叫分革拷锟斤拷锟斤拷锟絥ull锟斤拷
	 */
	public static String[] split(String sStr, String sSplitter) {
		if (sStr == null || sStr.length() <= 0 || sSplitter == null || sSplitter.length() <= 0) {
			return null;
		}

		String[] saRet = null;
		int iLen = sSplitter.length();
		int[] iIndex = new int[sStr.length()];
		iIndex[0] = sStr.indexOf(sSplitter, 0);

		if (iIndex[0] == -1) {
			saRet = new String[1];
			saRet[0] = sStr;

			return saRet;
		}

		int iIndexNum = 1; // get the iIndex of all splitter substring

		while (true) {
			iIndex[iIndexNum] = sStr.indexOf(sSplitter, iIndex[iIndexNum - 1] + iLen);

			if (iIndex[iIndexNum] == -1) {
				break;
			}

			iIndexNum++;
		}

		Vector vStore = new Vector();
		int i = 0;
		String sub = null;

		for (i = 0; i < iIndexNum + 1; i++) {
			if (i == 0) {
				sub = sStr.substring(0, iIndex[0]);
			} else if (i == iIndexNum) {
				sub = sStr.substring(iIndex[i - 1] + iLen, sStr.length());
			} else {
				sub = sStr.substring(iIndex[i - 1] + iLen, iIndex[i]);

			}
			if (sub != null && sub.length() > 0) {
				vStore.add(sub);
			}
		}

		if (vStore.size() <= 0) { // only split,none string
			return null;
		}

		saRet = new String[vStore.size()];

		Enumeration e = vStore.elements();

		for (i = 0; e.hasMoreElements(); i++) {
			saRet[i] = (String) e.nextElement();

		}
		return saRet;
	}

	/**
	 * this method is to convert Sting array to int array
	 * 
	 * @param String
	 *            []
	 * @return int[]
	 */
	private static int[] str2int(String[] sChecked) {
		if (sChecked == null || sChecked.length <= 0) {
			return null;
		}

		int[] iChecked = new int[sChecked.length];

		for (int i = 0; i < sChecked.length; i++) {
			iChecked[i] = Integer.parseInt(sChecked[i]);
		}

		return iChecked;
	}

	/**
	 * 锟斤拷Throwable锟斤拷锟斤拷转锟斤拷锟斤拷锟街凤拷
	 * 
	 * @param Throwable
	 *            exception
	 * @return String
	 */
	public static final String stackTrace(Throwable e) {
		String trace = null;

		try {
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			e.printStackTrace(new PrintWriter(buf, true));
			trace = buf.toString();
		} catch (Exception ignored) {
		}

		return trace;
	}

	/**
	 * 锟街凤拷锟斤拷锟斤拷,使锟矫匡拷锟斤拷锟斤拷锟揭猴拷锟斤拷址锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷.
	 * 锟斤拷锟杰凤拷锟斤拷锟斤拷,锟斤拷锟斤拷锟斤拷锟斤拷同锟斤拷锟斤拷锟揭凤拷锟斤拷校锟斤拷}锟斤拷锟街凤拷前锟斤拷锟斤拷锟斤拷锟角凤拷一锟斤拷.
	 * 
	 * @param str
	 * @param method
	 *            锟斤拷锟揭凤拷锟斤拷 "MD5","MYSQL"
	 * @return 锟斤拷锟揭猴拷锟斤拷址锟?
	 */
	public static String digest(String str, String method) {
		if (str == null) {
			return null;
		}
		if ("MD5".equals(method)) {
			return createMD5(str);
		} else {
			return createMYSQL(str);
		}
	}

	private static String createMD5(String passwordSource) {
		String Password;
		byte[] PasswordByte;
		Password = passwordSource;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(Password.getBytes());
			PasswordByte = md.digest();
		} catch (Exception e) {
			return passwordSource;
		}

		String ReturnPassword = toHex(PasswordByte);

		return ReturnPassword;
	}

	private static final String toHex(byte[] hash) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;

		for (i = 0; i < hash.length; i++) {
			if (((int) hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString((int) hash[i] & 0xff, 16));
		}

		return buf.toString();
	}

	private static String createMYSQL(String passwordSource) {
		String Password;
//		String hashOne;
//		String hashTwo;
		long resultLong1;
		long resultLong2;
		long tempLong1 = 1345345333;
		long tempLong2 = 0x12345671;
		long tempLong3;
		long addLong = 7;
//		long templtLong2;
		char charOne;
		Password = passwordSource;

		for (int i = 0; i < Password.length(); i++) {
			charOne = Password.charAt(i);

			if ((charOne == ' ') || (charOne == '\t')) {
				continue;
			}

			tempLong3 = (long) charOne;
			tempLong1 ^= (((tempLong1 & 63) + addLong) * tempLong3) + (tempLong1 << 8);
			tempLong2 += (tempLong2 << 8) ^ tempLong1;
			addLong += tempLong3;
		}

		resultLong1 = tempLong1 & (((long) 1 << 31) - (long) 1);
		resultLong2 = tempLong2 & (((long) 1 << 31) - (long) 1);

		String ReturnPassword;
		String ReturnPassword1;
		int j = 0;
		ReturnPassword = Long.toHexString(resultLong1);
		j = ReturnPassword.length();

		for (int i = 0; i < (8 - j); i++) {
			ReturnPassword = "0" + ReturnPassword;
		}

		ReturnPassword1 = Long.toHexString(resultLong2);
		j = ReturnPassword1.length();

		for (int i = 0; i < (8 - j); i++) {
			ReturnPassword1 = "0" + ReturnPassword1;
		}

		ReturnPassword = ReturnPassword + ReturnPassword1;

		return ReturnPassword;
	}

	/**
	 * @deprected ,instead of DataUtil
	 * @param date
	 * @return String
	 */
	public static String getDateString(Date date) {
		if (date == null) {
			return "";
		}

		return SIMPLE_DATE_FORMATTER.format(date);
	}

	/**
	 * @depracted ,instead of DataUtil
	 * @param date
	 * @return String
	 */
	public static String getLongDateString(Date date) {
		if (date == null) {
			return "";
		}

		return DATE_FORMATTER.format(date);
	}


	private static String encode(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char[] cs = s.toCharArray();
		StringBuffer sb = new StringBuffer(cs.length + 2);
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == ',') {
				sb.append('\\');
			}
			sb.append(cs[i]);
		}
		return sb.toString();
	}

	private static String decode(String s) {
		if (s == null || s.length() < 2) {
			return s;
		}
		char[] cs = s.toCharArray();
		StringBuffer sb = new StringBuffer(cs.length);

		for (int i = 0, n = cs.length; i < n; i++) {
			if (!(cs[i] == '\\' && i < n - 1 && cs[i + 1] == ',')) {
				sb.append(cs[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * convert string array to string
	 * 
	 * @param arr
	 *            String[]
	 * @return String,if arr is null of empty ,return empty string "";
	 */
	public static String arr2str(String[] arr) {
		if (arr == null) {
			return ("");
		}

		if (arr.length == 0) {
			return ("");
		}

		int length = arr.length;
		StringBuffer s = new StringBuffer();

		if (arr[0] != null && arr[0].length() > 0) {
			s.append(encode(arr[0]));
		} else {
			s.append("");

		}
		for (int i = 1; i < length; i++) {
			s.append(",");

			if (arr[i] != null && arr[i].length() > 0) {
				s.append(encode(arr[i]));
			} else {
				s.append("");
			}
		}

		return (s.toString());
	}

	/**
	 * convert int[] to string
	 * 
	 * @param arr
	 *            int[]
	 * @return String,if arr is null of empty ,return empty string "".
	 */
	public static String intarr2str(int[] arr) {
		if (arr == null) {
			return ("");
		}

		if (arr.length == 0) {
			return ("");
		}

		int length = arr.length;
		StringBuffer s = new StringBuffer();
		s.append(arr[0]);

		for (int i = 1; i < length; i++) {
			s.append(",");
			s.append(arr[i]);
		}

		return (s.toString());
	}

	/**
	 * 锟街凤拷转锟斤拷锟斤拷String[]
	 * 
	 * @param param
	 *            str
	 * @return String[] ,if str is null or empty return empty string array.
	 */
	public static String[] str2arr(String str) {
		if (str == null || str.length() < 1) {
			return new String[0];
		}

		int counter = 0;
		int pos = -1;
		int maxPosition = str.length() - 1;
		while (pos < maxPosition) {
			pos++;
			if (str.charAt(pos) == ',') {
				if (pos == 0 || str.charAt(pos - 1) != '\\') {
					counter++;
				}
			}
		}
		String[] new_str = new String[counter + 1];

		int cur = -1;
		int i = 0;
		pos = -1;
		boolean should_decode = false;
		while (pos < maxPosition) {
			pos++;
			if (str.charAt(pos) == ',') {
				if (pos != 0 && str.charAt(pos - 1) == '\\') {
					// skip
					should_decode = true;
				} else {
					if (should_decode) {
						new_str[i++] = decode(str.substring(cur + 1, pos));
						should_decode = false;
					} else {
						new_str[i++] = str.substring(cur + 1, pos);
					}
					cur = pos;
				}
			}
		}
		if (should_decode) {
			new_str[counter] = decode(str.substring(cur + 1));
		} else {
			new_str[counter] = str.substring(cur + 1);
		}
		return new_str;
	}

	/**
	 * 锟街凤拷转锟斤拷锟斤拷int[]
	 * 
	 * @param str
	 * @return int[],return empty int array if str is null or empty.
	 */
	public static int[] str2intarr(String str) {
		if (str == null || str.length() < 1) {
			return new int[0];
		}

		StringTokenizer st = new StringTokenizer(str, ",");
		int[] new_int = new int[st.countTokens()];
		int i = 0;

		while (st.hasMoreTokens()) {
			String tmp = (String) st.nextToken();

			try {
				new_int[i++] = Integer.parseInt(tmp);
			} catch (Exception e) {
			}

		}

		return new_int;
	}

	/**
	 * hash锟斤拷转锟斤拷锟斤拷锟街凤拷,锟斤拷锟节憋拷锟斤拷hash锟斤拷锟?.<br>
	 * 
	 * eg. abcd2=bbb,abcd=,abcd1=,cc=abcd
	 * 
	 * @param hash
	 * @return String,if hash is null return empty string;
	 */
	public static String hash2str(Map hash) {
		if (hash == null) {
			return "";
		}

		int max = hash.size() - 1;
		StringBuffer buf = new StringBuffer();
		Iterator it = hash.entrySet().iterator();

		for (int i = 0; i <= max; i++) {
			Map.Entry e = (Map.Entry) (it.next());
			buf.append(encode((String) e.getKey()) + "=" + encode((String) e.getValue()));

			if (i < max) {
				buf.append(",");
			}
		}

		return buf.toString();
	}

	/**
	 * 锟街凤拷转锟斤拷锟斤拷hash锟斤拷,锟斤拷锟斤拷取锟斤拷锟斤拷锟斤拷锟紿ash.<br>
	 * 
	 * eg. abcd2=bbb,abcd=,abcd1=,cc=abcd
	 * 
	 * @param String
	 * @return Hashtable,return empty hashtable if str is null or empty
	 */
	public static Hashtable str2hash(String str) {
		Hashtable hash = new Hashtable();

		if (str == null || str.length() < 1) {
			return hash;
		}

		String[] pairs = str2arr(str);

		for (int i = 0; i < pairs.length; i++) {
			try {
				int index = pairs[i].indexOf("=");
				String key1 = decode(pairs[i].substring(0, index));
				if (index == pairs[i].length() || index < 0) {
					hash.put(key1, "");
				} else {
					hash.put(key1, pairs[i].substring(index + 1));
				}
			} catch (Exception e) {
			}

		}

		return hash;
	}

	public static String getClassName(Class c) {
		String long_name = c.getName();
		return long_name.substring(long_name.lastIndexOf(".") + 1);
	}

	/**
	 * Pads the supplied String with 0's to the specified length and returns the
	 * result as a new String. For example, if the initial String is "9999" and
	 * the desired length is 8, the result would be "00009999". This type of
	 * padding is useful for creating numerical values that need to be stored
	 * and sorted as character data. Note: the current implementation of this
	 * method allows for a maximum <tt>length</tt> of 16.
	 * 
	 * @param string
	 *            the original String to pad.
	 * @param length
	 *            the desired length of the new padded String.
	 * @return a new String padded with the required number of 0's.
	 */
	public static final String zeroPadString(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		}
		StringBuffer buf = new StringBuffer(length);
		buf.append(zeroArray, 0, length - string.length()).append(string);
		return buf.toString();
	}

	public static final String highlightEnglishWords(String str, String[] words) {
		String tmp = null;
		try {
			tmp = highlightEnglishWordsInHtml(str, words);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (tmp == null) {
			return str;
		}
		return tmp;
	}

	public static final String highlightWords(String str, String[] words) {
		String tmp = null;
		try {
			tmp = highlightWordsInHtml(str, words);
		} catch (Exception ex) {
		}
		if (tmp == null) {
			return str;
		}
		return tmp;
	}

	final static String[] startHighlight = new String[] { "<b>", "<b>", "<b>" };
	final static String endHighlight = "</b>";

	private static final String highlightEnglishWordsInHtml(String string, String[] words) throws Exception {
		if (string == null || words == null) {
			return null;
		}
		char[] source = null;
		StringBuffer sb = new StringBuffer(string);
		for (int wk = 0; wk < words.length; wk++) {
			if (words[wk] == null) {
				continue;
			}
			source = sb.toString().toCharArray();
			sb.setLength(0);
			int sourceOffset = 0;
			int sourceCount = source.length;
			char[] target = words[wk].toLowerCase().toCharArray();
			int targetOffset = 0;
			int targetCount = target.length;
			int fromIndex = 0;
			if (fromIndex >= sourceCount) {
				continue;
			}
			if (targetCount == 0) {
				continue;
			}
			char first = target[targetOffset];
			int i = sourceOffset + fromIndex;
			int max = sourceOffset + (sourceCount - targetCount);

			int sbPos = 0;
			int tags1 = 0;
			char c = 0;
			startSearchForFirstChar: while (true) {
				while (i <= max) {
					c = source[i];
					switch (c) {
					case '<':
						tags1++;
						break;
					case '>':
						if (tags1 > 0)
							tags1--;
						break;
					case ',':
					case '\n':
						tags1 = 0;
						break;
					default:
					}
					if (Character.toLowerCase(c) == first) {
						break;
					}
					i++;
				}

				if (i > max) {
					break;
				}

				if (tags1 != 0 || (i > 0 && Character.isLetterOrDigit(source[i - 1]))) {
					i++;
					continue startSearchForFirstChar;
				}

				/* Found first character, now look at the rest of v2 */
				int j = i + 1;
				int end = j + targetCount - 1;
				int k = targetOffset + 1;
				while (j < end) {
					if (Character.toLowerCase(source[j++]) != target[k++]) {
						i++;
						/* Look for str's first char again. */
						continue startSearchForFirstChar;
					}
				}
				if (end < source.length - 1 && Character.isLetterOrDigit(source[end])) {
					i++;
					continue startSearchForFirstChar;
				}
				int pos = i - sourceOffset; /* Found whole string. */
				sb.append(source, sbPos, pos - sbPos);
				sb.append(startHighlight[wk % startHighlight.length]);
				sb.append(source, pos, targetCount);
				sb.append(endHighlight);
				sbPos = pos + targetCount;
				i += targetCount;
			}
			sb.append(source, sbPos, sourceCount - sbPos);
		}
		return sb.toString();
	}

	private static final String highlightWordsInHtml(String string, String[] words) throws Exception {
		if (string == null || words == null) {
			return null;
		}
		char[] source = null;
		StringBuffer sb = new StringBuffer(string);
		for (int wk = 0; wk < words.length; wk++) {
			if (words[wk] == null) {
				continue;
			}
			source = sb.toString().toCharArray();
			sb.setLength(0);
			int sourceOffset = 0;
			int sourceCount = source.length;
			char[] target = words[wk].toLowerCase().toCharArray();
			int targetOffset = 0;
			int targetCount = target.length;
			int fromIndex = 0;
			if (fromIndex >= sourceCount) {
				continue;
			}
			if (targetCount == 0) {
				continue;
			}
			char first = target[targetOffset];
			int i = sourceOffset + fromIndex;
			int max = sourceOffset + (sourceCount - targetCount);

			int sbPos = 0;
			int tags1 = 0;
			char c = 0;
			startSearchForFirstChar: while (true) {
				while (i <= max) {
					c = source[i];
					switch (c) {
					case '<':
						tags1++;
						break;
					case '>':
						if (tags1 > 0)
							tags1--;
						break;
					case ',':
					case '\n':
						tags1 = 0;
						break;
					default:
					}
					if (Character.toLowerCase(c) == first) {
						break;
					}
					i++;
				}
				if (i > max) {
					break;
				}

				if (tags1 != 0) {
					i++;
					continue startSearchForFirstChar;
				}

				/* Found first character, now look at the rest of v2 */
				int j = i + 1;
				int end = j + targetCount - 1;
				int k = targetOffset + 1;
				while (j < end) {
					if (Character.toLowerCase(source[j++]) != target[k++]) {
						i++;
						/* Look for str's first char again. */
						continue startSearchForFirstChar;
					}
				}
				int pos = i - sourceOffset; /* Found whole string. */
				sb.append(source, sbPos, pos - sbPos);
				sb.append(startHighlight[wk % startHighlight.length]);
				sb.append(source, pos, targetCount);
				sb.append(endHighlight);
				sbPos = pos + targetCount;
				i += targetCount;
			}
			sb.append(source, sbPos, sourceCount - sbPos);
		}
		return sb.toString();
	}

	/**
	 * Replaces all instances of oldString with newString in line.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * 
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replace(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line with the added
	 * feature that matches of newString in oldString ignore case.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * 
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replaceIgnoreCase(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line with the added
	 * feature that matches of newString in oldString ignore case. The count
	 * paramater is set to the number of replaces performed.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * @param count
	 *            a value that will be updated with the number of replaces
	 *            performed.
	 * 
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replaceIgnoreCase(String line, String oldString, String newString, int[] count) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			int counter = 0;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line. The count
	 * Integer is updated with number of replaces.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * 
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replace(String line, String oldString, String newString, int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * Given an array of strings, convert it to one string with the separator as
	 * given. Converts separator to "" if it is null.
	 * 
	 * @return ("" + null) if a null string_array is fed in, "" if a zero-length
	 *         string array is fed in.
	 */
	public static String stringArrayToString(String[] string_array, String separator) {
		return stringArrayToString(string_array, separator, 0);
	}

	/**
	 * Given an array of strings, convert it to one string with the separator as
	 * given. Converts separator to "" if it is null. start_index is set to 0 if
	 * less than 0.
	 * 
	 * @return ("" + null) if a null string_array is fed in, "" if a zero-length
	 *         string array is fed in or if start_index is greater than
	 *         string_array.length - 1
	 */
	public static String stringArrayToString(String[] string_array, String separator, int start_index) {
		if (string_array == null) {
			return ("" + null);
		}
		if (string_array.length == 0) {
			return ("");
		}
		if (separator == null) {
			separator = "";
		}
		int length = string_array.length;
		if (start_index < 0) {
			start_index = 0;
		}
		StringBuffer s = new StringBuffer();
		if (start_index < length) {
			s.append(string_array[start_index]);
		}
		for (int i = start_index + 1; i < length; i++) {
			s.append(separator);
			if (string_array[i] != null) {
				s.append(string_array[i]);
			}
		}
		return (s.toString());
	}

	/**
	 * This is the opposite of stringArrayToString(). Given a string containing
	 * a separator, break it into an array of strings and return that.
	 */
	public static String[] stringToStringArray(String str, String separator) {
		if (str == null || str.length() < 1) {
			return new String[0];
		}
		StringTokenizer st = new StringTokenizer(str, separator);
		String[] new_str = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			new_str[i++] = (String) st.nextToken();
		}
		return new_str;
	}

	/**
	 * Given an array of ints, convert it to one string with the separator as
	 * given.
	 * 
	 * @return ("" + null) if a null array is fed in, "" if a zero-length array
	 *         is fed in, converts separator to "" if it is null.
	 */
	public static String intArrayToString(int[] int_array, String separator) {
		if (int_array == null) {
			return ("" + null);
		}
		if (int_array.length == 0) {
			return ("");
		}
		if (separator == null) {
			separator = "";
		}
		int length = int_array.length;
		StringBuffer s = new StringBuffer();
		if (length > 0) {
			s.append(int_array[0]);
		}
		for (int i = 1; i < length; i++) {
			s.append(separator);
			s.append(int_array[i]);
		}
		return (s.toString());
	}

	/**
	 * This is the opposite of intArrayToString(). Given a string containing a
	 * separator, break it into an array of strings and return that.
	 */
	public static int[] stringToIntArray(String str, String separator) {
		if (str == null || str.length() < 1) {
			return new int[0];
		}
		StringTokenizer st = new StringTokenizer(str, separator);
		int[] new_ints = new int[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			try {
				new_ints[i] = Integer.parseInt((String) st.nextToken());
				i++;
			} catch (NumberFormatException ex) {
				new_ints[i++] = -1;
			}
		}
		return new_ints;
	}

	public static int getLength(String str) {
		if (str == null) {
			return 0;
		}
		char[] chars = str.toCharArray();
		int n = 0;
		for (int i = 0; i < chars.length; i++) {
			if (Character.UnicodeBlock.of(chars[i]) == Character.UnicodeBlock.BASIC_LATIN) {
				n++;
			} else {
				n += 2;
			}
		}
		return n;
	}

	/**
	 * Pseudo-random number generator object for use with randomString(). The
	 * Random class is not considered to be cryptographically secure, so only
	 * use these random Strings for low to medium security applications.
	 */
	private static Random randGen = new Random();

	/**
	 * Array of numbers and letters of mixed case. Numbers appear in the list
	 * twice so that there is a more equal chance that a number will be picked.
	 * We can use the array to get a random number or letter by picking a random
	 * array index.
	 */
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
			.toCharArray();

	/**
	 * Returns a random String of numbers and letters (lower and upper case) of
	 * the specified length. The method uses the Random class that is built-in
	 * to Java which is suitable for low to medium grade security uses. This
	 * means that the output is only pseudo random, i.e., each number is
	 * mathematically generated so is not truly random.
	 * <p>
	 * 
	 * The specified length must be at least one. If not, the method will return
	 * null.
	 * 
	 * @param length
	 *            the desired length of the random String to return.
	 * @return a random String of numbers and letters of the specified length.
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static final int randomInt(int length) {
		if (length < 1 && length > 9) {
			throw new java.lang.ArithmeticException("the length of random int must be between 0 and 9");
		}
		int sum = 0;
		int n = 1;
		int r = 0;
		for (int i = 1; i < length; i++) {
			r = randGen.nextInt(10);
			sum += r * n;
			n = n * 10;
		}
		r = 1 + randGen.nextInt(9);
		sum += r * n;
		return sum;
	}

	public static int indexOfIgnoreCase(String body, String p) {
		p = p.toLowerCase();
		char[] source = body.toCharArray();
		int sourceOffset = 0;
		int sourceCount = source.length;
		char[] target = p.toCharArray();
		int targetOffset = 0;
		int targetCount = target.length;
		int fromIndex = 0;

		if (fromIndex >= sourceCount) {
			return (targetCount == 0 ? sourceCount : -1);
		}
		if (fromIndex < 0) {
			fromIndex = 0;
		}
		if (targetCount == 0) {
			return fromIndex;
		}

		char first = target[targetOffset];
		int i = sourceOffset + fromIndex;
		int max = sourceOffset + (sourceCount - targetCount);

		startSearchForFirstChar: while (true) {

			/* Look for first character. */
			while (i <= max && Character.toLowerCase(source[i]) != first) {
				i++;
			}
			if (i > max) {
				return -1;
			}

			/* Found first character, now look at the rest of v2 */
			int j = i + 1;
			int end = j + targetCount - 1;
			int k = targetOffset + 1;
			while (j < end) {
				if (Character.toLowerCase(source[j++]) != target[k++]) {
					i++;
					/* Look for str's first char again. */
					continue startSearchForFirstChar;
				}
			}
			return i - sourceOffset; /* Found whole string. */
		}
	}

	public static final String escapeForXML(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	public static final String escapeForHtml(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	public static final String escapeForHtml2(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	public static final String escapeForJs(String string) {
		if (isEmpty(string))
			return string;
		Pattern p = Pattern.compile("<script.*.</script>");
		Matcher m = p.matcher(string);
		if (m.find()) {
			return string.replaceAll("<script.*.</script>", StringUtil.escapeForHtml(m.group()));
		}
		return string;
	}

	/**
	 * Unescapes the String by converting XML escape sequences back into normal
	 * characters.
	 * 
	 * @param string
	 *            the string to unescape.
	 * @return the string with appropriate characters unescaped.
	 */
	public static final String unescapeFromXML(String string) {
		string = replace(string, "&lt;", "<");
		string = replace(string, "&gt;", ">");
		string = replace(string, "&quot;", "\"");
		return replace(string, "&amp;", "&");
	}

	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
	private static final char[] AMP_ENCODE = "&amp;".toCharArray();
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
//	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	public static long countWords(String line) {
		long numWords = 0;
		int index = 0;
		boolean prevWhitespace = true;
		while (index < line.length()) {
			char c = line.charAt(index++);
			boolean currWhitespace = Character.isWhitespace(c);
			if (prevWhitespace && !currWhitespace) {
				numWords++;
			}
			prevWhitespace = currWhitespace;
		}
		return numWords;
	}

	public static String txt2html(String html) {
		if (html == null) {
			return "";
		}
		html = html.replaceAll("\n", "<br/>");
		return html;
	}

	public static String html2txt(String html) {
		if (html == null) {
			return "";
		}
		char[] chars = html.toCharArray();
		StringBuffer sb = new StringBuffer();
		int n = 0;
		int i = 0;
		char c = 0;
		int tags1 = 0;
		int tags2 = 0;
		while (i < html.length()) {
			c = chars[i++];
			switch (c) {
			case '<':
				tags1++;
				continue;
			case '>':
				if (tags1 > 0)
					tags1--;
				continue;
			case '[':
				tags2++;
				continue;
			case ']':
				if (tags2 > 0)
					tags2--;
				continue;
			case ',':
			case '\n':
				tags1 = 0;
				tags2 = 0;
				continue;
			default:
			}
			if (tags1 == 0 && tags2 == 0) {
				sb.append(c);
				n++;
			}
		}
		return sb.toString();
	}

	public static String html2txt2(String html) {
		if (html == null) {
			return "";
		}
		char[] chars = html.toCharArray();
		StringBuffer sb = new StringBuffer();
		int n = 0;
		int i = 0;
		char c = 0;
		int tags1 = 0;
		while (i < html.length()) {
			c = chars[i++];
			switch (c) {
			case '<':
				tags1++;
				continue;
			case '>':
				if (tags1 > 0)
					tags1--;
				continue;
			default:
			}
			if (tags1 == 0) {
				sb.append(c);
				n++;
			}
		}
		return sb.toString();
	}

	/**
	 * 去掉script脚本.将script中的代码包括<script>过滤掉。支持大小写区分及支持换行 add by zhanghao
	 * 2010.12.1
	 */
	public static String htmlScript2txt(String html) {
		try {
			Pattern p = Pattern.compile("<script(.+?)</script>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(html);
			if (m.find()) {
				return m.replaceAll("");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return html;
	}

	/**
	 * 去掉css脚本.将css中的代码包括<css>过滤掉。支持大小写区分及支持换行 add by zhanghao 2010.12.1
	 */
	public static String htmlCss2txt(String html) {
		try {
			Pattern p = Pattern.compile("<style(.+?)</style>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(html);
			if (m.find()) {
				return m.replaceAll("");
				// return html.replaceAll("<style(?is)(.+?)</style>", "");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return html;
	}

	/**
	 * 去掉xml脚本.将xml中的代码包括<xml>过滤掉。支持大小写区分及支持换行 add by zhanghao 2010.12.1
	 */
	public static String htmlXml2txt(String html) {
		try {
			Pattern p = Pattern.compile("<xml(.+?)</xml>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(html);
			if (m.find()) {
				return m.replaceAll("");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return html;
	}

	/**
	 * 去掉style和script,xml中的脚本.再执行html2txt2 add by zhanghao 2010.12.1
	 */
	public static String html2txt3(String html) {
		String tempHtml = html;
		tempHtml = htmlCss2txt(tempHtml);
		tempHtml = htmlScript2txt(tempHtml);
		tempHtml = htmlXml2txt(tempHtml);
		return html2txt2(tempHtml);
	}

	public static String[] parseEnglishTerms(String str) {
		List terms = new ArrayList(5);
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		char c = 0;
		boolean wordsBegins = false;
		boolean wordsEnds = false;
		while (i < chars.length) {
			c = chars[i];
			if (!Character.isLetterOrDigit(c) || Character.isSpaceChar(c)) {
				if (wordsBegins) {
					wordsEnds = true;
				}
			} else {
				wordsBegins = true;
				sb.append(c);
			}

			if (wordsEnds) {
				terms.add(sb.toString());
				sb.setLength(0);
				wordsEnds = false;
				wordsBegins = false;
			}
			i++;
		}
		if (sb.length() > 0) {
			terms.add(sb.toString());
		}
		return (String[]) terms.toArray(new String[terms.size()]);
	}

	public static String convertEncoding(String s, String from, String to) {
		try {
			return new String(s.getBytes(from), to);
		} catch (Exception e) {
			return s;
		}
	}

	public static String convertEncoding(String s, String to) {
		try {
			// System.getProperty("file.encoding")
			return new String(s.getBytes("ISO-8859-1"), to);
		} catch (Exception e) {
			return s;
		}
	}

	public static String tag2html(String body) {
		if (body == null || body.length() == 0) {
			return body;
		}
		body = body.replaceAll(">", "&gt;");
		body = body.replaceAll("<", "&lt;");
		body = body.replaceAll("\r", "");
		body = body.replaceAll("\n", "<br/>");

		body = body.replaceAll("(\\[URL|(^|\\s)(http|ftp|https):\\/\\/|(^|\\s)(www|ftp)\\.)(\\S*?)\".*?(\\]|\\[\\/URL\\]|\\s|$)", "$1$6$7");
		body = body.replaceAll("(^|\\s)((http|ftp|https):\\/\\/\\S+)", "$1<A HREF=\"$2\" TARGET=_blank>$2<\\/A>");
		body = body.replaceAll("(^|\\s)(www\\.\\S+)", "$1<A HREF=\"http:\\/\\/$2\" TARGET=_blank>$2<\\/A>");
		body = body.replaceAll("(^|\\s)(ftp\\.\\S+)", "$1<A HREF=\"ftp:\\/\\/$2\" TARGET=_blank>$2<\\/A>");
		body = body.replaceAll("(\\[URL\\])(http|https|ftp)(:\\/\\/\\S+?)(\\[\\/URL\\])", "<A HREF=\"$2$3\" TARGET=_blank>$2$3<\\/A>");
		body = body.replaceAll("(\\[URL\\])(\\S+?)(\\[\\/URL\\])", "<A HREF=\"http:\\/\\/$2\" TARGET=_blank>$2<\\/A>");
		body = body
				.replaceAll("(\\[URL=)(http|https|ftp)(:\\/\\/\\S+?)(\\])(.+?)(\\[\\/URL\\])", "<A HREF=\"$2$3\" TARGET=_blank>$5<\\/A>");
		body = body.replaceAll("(\\[URL=)(\\S+?)(\\])(.+?)(\\[\\/URL\\])", "<A HREF=\"http:\\/\\/$2\" TARGET=_blank>$4<\\/A>");

		body = body.replaceAll("\\[b\\]", "<b>");
		body = body.replaceAll("\\[/b\\]", "</b>");
		body = body.replaceAll("\\[i\\]", "<i>");
		body = body.replaceAll("\\[/i\\]", "</i>");

		body = body.replaceAll("(\\[COLOR=)(\\S+?)(\\])(.+?)(\\[\\/COLOR\\])", "<FONT COLOR=\"$2\">$4<\\/FONT>");
		return body;
	}

	public static String safehtml(String body2) {
		if (body2 == null) {
			return null;
		}
		return Pattern.compile("<[^<|\\w]*(SCRIPT|IFRAME|FRAME|APPLET)[^>]*>", Pattern.CASE_INSENSITIVE).matcher(body2).replaceAll("");
	}

	public static List<String> arr2list(String[] strs) {
		if (strs == null || strs.length == 0)
			return null;
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < strs.length; i++) {
			list.add(strs[i]);
		}
		return list;
	}

	public static String replace(String str, String[] illChars) {
		if (isEmpty(str))
			return null;
		str = str.toLowerCase();
		for (int i = 0; i < illChars.length; i++) {
			str = str.replace(illChars[i], "");
		}
		return str.trim();
	}

	public static String transferred(String str, String[] illStrs) {
		if (isEmpty(str))
			return null;
		str = str.toLowerCase();
		str.toCharArray();
		for (int i = 0; i < illStrs.length; i++) {
			// StringBuffer s1 = new StringBuffer("\\\\");
			// s1.append(illStrs[i]);
			// StringBuffer s2 = new StringBuffer("\\\\\\\\");
			// s2.append(illStrs[i]);
			// System.out.println(s1 + " ==> " + s2);
			// str = str.replaceAll(s1.toString(), s2.toString());
			// System.out.println(str);
			str = str.replace(illStrs[i], "");
			// str = str.replaceAll("\\\\" + illStrs[i], "\\\\\\\\" +
			// illStrs[i]);
		}
		return str;
	}

	public static String escape(String str, String[] illStrs) {
		if (isEmpty(str))
			return null;
		str = str.toLowerCase();
		str.toCharArray();
		for (int i = 0; i < illStrs.length; i++) {
			str = str.replace(illStrs[i], "\\" + illStrs[i]);
		}
		return str;
	}

	/**
	 * 查找Context中的第一幅图片.并返回其地址
	 * 
	 * @param content
	 * @return List
	 * @throws Exception
	 */
	public static String getFirstPreviewImg(String content) {
		String regx = "<img( ||.*?)src=('|\"|)(.*?)('|\"|>| )";
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(content);
		while (m.find()) {
			String imgPath = m.group();
			imgPath = imgPath.substring(imgPath.indexOf("src=") + 5, imgPath.length() - 1);
			return imgPath;
		}
		return "";
	}

	/**
	 * 将字符串中的敏感词汇替换成 ***
	 * 
	 * @param sensitive_keywords
	 * @param oldString
	 * @return newString
	 */
	public static String checkSensitive(String sensitive_keywords, String oldString) {
		// String regx=sensitive_keywords.replaceAll(",","|");
		// while(regx.endsWith("|")) {
		// regx = regx.substring(0,regx.length()-1);
		// }
		// Pattern pattern=Pattern.compile(regx);
		// Matcher match = pattern.matcher(oldString);
		// String newString = match.replaceAll("***");
		// return newString;

		String[] regx = sensitive_keywords.split(",");
		String regxString = "";
		for (int i = 0; i < regx.length; i++) {
			String s = regx[i].replaceAll("\\s", "");
			if (StringUtil.isNotEmpty(s) && !s.equals("|")) {
				s = addBlank(s);
				regxString += "|" + s;
			}
		}
		if (regxString.startsWith("|")) {
			regxString = regxString.substring(1);
		}
		if (StringUtil.isEmpty(regxString))
			return oldString;
		Pattern pattern = Pattern.compile(regxString);
		Matcher match = pattern.matcher(oldString);
		String newString = match.replaceAll("***");
		return newString;
	}

	// 字符串中的字符与字符之间加入若干个空格
	private static String addBlank(String s) {
		char[] chars = s.toCharArray();
		String newString = "" + chars[0];
		for (int i = 1; i < chars.length; i++) {
			newString += "(\\s*)" + chars[i];
		}
		return newString;
	}

	/**
	 * 产生较短UUID,返回36进制的128位uuid
	 * 
	 * @return
	 */
	public static String getShortUUID() {
		return UUIDtoShortString(UUID.randomUUID());
	}

	private static String UUIDtoShortString(UUID u) {
		long mostSigBits = u.getMostSignificantBits();
		long leastSigBits = u.getLeastSignificantBits();
		return (digits(mostSigBits >> 32, 8) + digits(mostSigBits >> 16, 4) + digits(mostSigBits, 4) + digits(leastSigBits >> 48, 4) + digits(
				leastSigBits, 12));
	}

	private static String digits(long val, int digits) {
		long hi = 1L << (digits * 4);
		return Long.toString(hi | (val & (hi - 1)), 36).substring(1);
	}

	/**
	 * 将double 转换成 String
	 */
	public static String doubleToStr(double value, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(value);
	}

	/**
	 * 将double 转换成 String, 默认格式： #.##
	 */
	public static String doubleToStr(double value) {
		return doubleToStr(value, "#.##");
	}

	/**
	 * 判断是否包含图片
	 */
	public static boolean hasImg(String body) {
		String regx = "<img( ||.*?)src=('|\"|)(.*?)('|\"|>| )";
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(body);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否包含swf等媒体文件
	 */
	public static boolean hasMedia(String body) {
		String regx = "<embed( ||.*?)src=('|\"|)(.*?)('|\"|>| )";
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(body);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static boolean isSecondLevelDomain(String domain) {
		if (StringUtil.isEmpty(domain))
			return false;
		Matcher matcher = SECONDLEVEL_DOMAIN.matcher(domain);
		return matcher.find();
	}
	
	public static boolean isUUID(String id) {
		if (isEmpty(id))
			return false;
		Matcher matcher = UUID_REG.matcher(id);
		return matcher.find();
	}

	/**
	 * 识别body内的url，将url替换成 <a href="URL">URL</a>
	 * URL要求是以http,ftp,file开头,style是标签样式
	 * 
	 * @param body
	 * @return
	 */
	public static String url2Link(String body, String style) {
		if (StringUtil.isEmpty(body)) {
			return null;
		}
		Matcher matcher = URL_REG.matcher(body);
		StringBuffer sbr = new StringBuffer();
		String replace = null;
		while (matcher.find()) {
			String map = matcher.group();
			replace = "<a href=\"" + map + "\" target=\"_blank\"";
			if (StringUtil.isNotEmpty(style)) {
				replace += " style = \"" + style + "\">";
			} else {
				replace += ">";
			}
			replace += map + "</a>";
			matcher.appendReplacement(sbr, replace);
			replace = null;
		}
		matcher.appendTail(sbr);
		return sbr.toString();
	}

	public static String subNameString(String str) {
		if (str.indexOf("匿名") != -1) {
			str = "匿名用户";
		} else if (isNotEmpty(str) && str.length() > 4) {
			str = str.substring(0, 2) + "***" + str.substring(str.length() - 2, str.length());
		} else {
			str += "***";
		}
		return str;
	}
	
	/**
     * JSON字符串特殊字符处理，比如：“\A1;1300”  yjq
     * @param s
     * @return String
     */
    public static String string2Json(String s) 
    {      
        StringBuilder sb = new StringBuilder();      
        for (int i = 0; i < s.length(); i++) 
        {
        	char c = s.charAt(i);  
        	 switch (c){
        	 case '\"':      
                 sb.append("\\\"");      
                 break;      
             case '\\':      
                 sb.append("\\\\");      
                 break;      
             case '/':      
                 sb.append("\\/");      
                 break;      
             case '\b':      
                 sb.append("\\b");      
                 break;      
             case '\f':      
                 sb.append("\\f");      
                 break;      
             case '\n':      
                 sb.append("\\n");      
                 break;      
             case '\r':      
                 sb.append("\\r");      
                 break;      
             case '\t':      
                 sb.append("\\t");      
                 break;      
             default:      
                 sb.append(c);   
        	 }
         }    
        return sb.toString();   
    }
    
	/**
     * 文字替换处理  yjq
     * @param s
     * @return String
     */
    public static String replaceAll2(String w) 
    {
    	return  w.replaceAll(" ","-").replaceAll("/|，|,|;|：|:|;", "-").replaceAll("/[^a-zA-Z0-9\u2E80-\u9FFF]*$/","").replaceAll("&|%|!|#", "");
    }
    
	/**
     * 文字删除  yjq
     * @param s
     * @return String
     */
    public static String deleteWords(String w, String dw) 
    {
    	if(StringUtil.isEmpty(w) || dw == null)
    	{
    		return w;
    	}
    	return w.replaceFirst(dw, "");
    }
    
    /**
     * 文字删除  yjq
     * 非正则删除
     * @param s
     * @param dw a|B|c 代表删除a B 和c
     * @return String
     */
    public static String deleteWordsAll(String w, String dw) 
    {
    	if(StringUtil.isEmpty(w) || dw == null)
    	{
    		return w;
    	}
    	String[] temp = dw.split("|");
    	for(String tmp : temp)
    	{
    		if(StringUtil.isEmpty(tmp)) continue;
    		w = w.replace(tmp, "");
    	}
    	return w;
    }
}
