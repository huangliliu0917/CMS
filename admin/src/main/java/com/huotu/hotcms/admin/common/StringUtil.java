/*
 * 版权�?�?:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B�?4�?
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.hotcms.admin.common;

//import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtil {
    public static String DATE_PATTERN = "yyyy-MM-dd";
    public static String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String UTF8 = "utf-8";

    public static final String NETSHOP_SECRET = "123456";

    public StringUtil() {
    }

    public static boolean isNull(Object pobjObject) {
        if (pobjObject == null) {
            return true;
        }
        if (pobjObject instanceof String) {
            String s = (String) pobjObject;
            if (StringUtil.trim(StringUtil.trimMulti(s, false)).trim().length() < 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotNull(Object pobjObject) {
        return !isNull(pobjObject);
    }

    /**
     * ���ַ��������ƶ������и�ΪList?<br>
     * <br>
     *
     * @param pstrString Դ�ַ���
     * @param pstrSep    �и�����
     * @return �и���List
     */

    public static List<String> splitStringToList(String pstrString, String pstrSep) {
        if (pstrString == null || pstrSep == null) {
            return new ArrayList<String>();
        }

        return new ArrayList<String>(Arrays.asList(pstrString.split(pstrSep)));
    }

    /**
     * 04	     * ����Ƿ���emoji�ַ�
     * 05	     * @param source
     * 06	     * @return һ�����о��׳�
     * 07
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isEmpty(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing���жϵ������������ȷ���б����ַ�
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 38	     * ����emoji ���� �������������͵��ַ�
     * 39	     * @param source
     * 40	     * @return
     * 41
     */
    public static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            return source;//�����������ֱ�ӷ���
        }
        //��������������
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            }
        }

        if (buf == null) {
            return source;//���û���ҵ� emoji���飬�򷵻�Դ�ַ���
        } else {
            if (buf.length() == len) {//������������ھ������ٵ�toString����Ϊ�����������ַ���
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }

    }


    public static String trim(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("\\A[\\u0000-\\u0020?]+", "").replaceAll("[\\u0000-\\u0020?]+\\z", "");
    }

    public static String trimMulti(String value, boolean removeBlankLine) {
        if (value == null) {
            return null;
        }
        String excludeLineFeed = "&&[^\\n\\r]";
        if (removeBlankLine) {
            excludeLineFeed = "";
        }
        return value.replaceAll("(?m)^[\\u0000-\\u0020?" + excludeLineFeed + "]+", "").replaceAll(
                "(?m)[\\u0000-\\u0020?" + excludeLineFeed + "]+$", "");
    }

    /**
     * ������ת����ͨ�õ�ISO���룬?�Ͻ������ַ�ת��������
     *
     * @param str String �����ַ�?
     * @return String
     */
    public static String getStr(String str) {
        try {
            String temp_p = str;
            byte[] temp_t = temp_p.getBytes("ISO8859-1");
            String temp = new String(temp_t);
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * �൱����дString.valueOf�����objectΪ�գ�������"null"��������null
     *
     * @param object
     * @return
     */
    public static String valueOf(Object object) {
        if (object == null) {
            return null;
        }
        return String.valueOf(object);
    }

    /**
     * �������ַ���<param>input</param>�е�'ת��? \'�Լ�"ת����\". ������������ű�����? ����ֱ����JS��Ӧ��??
     *
     * @param input String
     * @return String
     */
    public static String escapeJSTags(String input) {
        if (input == null || input.length() == 0)
            return input;
        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '\'') {
                buf.append("\\'");
                continue;
            }
            if (ch == '"') {
                buf.append("\"");
                continue;
            }
            if (ch == '\r') {
                continue;
            }
            if (ch == '\n') {
                continue;
            }
            if (ch == '\\') {
                buf.append("\\\\");
                continue;
            }
            buf.append(ch);
        }
        return buf.toString();

    }

    /**
     * Escape SQL tags, ' to '';
     *
     * @param input string to replace
     * @return string
     */
    public static String escapeSQLTags(String input) {
        if (input == null || input.length() == 0)
            return input;
        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);
            if (ch == '\'')
                buf.append("\'\'");
            else
                buf.append(ch);
        }
        return buf.toString();
    }

    /**
     * �ж��ַ�?<param>str</param>��������?������??
     * <LI>����unicode���룬����ַ����е�ĳ���ַ�ֵ��<br>
     * ����? Unicode �����е����ļ������������պ������ʣ�����ġ�������? U+4e00 ~ U+9FBB ԭ�� GB2312 ? GBK
     * �еĺ��� U+3400 ~ U+4DB6 ���� GB18030.2000 ����Щ���ӵĺ��� <br>
     * ֮�䣬���ַ����к�������?
     * <LI>?Щ���ķ�����? CJK Symbols and Punctuation ��Χ���� u+3000 ? u+303F
     * <LI>���������ż�ʱ��Ӧ��ʹ�÷��� <code>toGBStringBinary</code>����ת��?<strong>ע�����������unicode�������<strong>
     * <LI>����Ŀǰϵͳ����ֻ֧����Ӣ�ģ��������һ����Ϊ��Ӣ�ģ�ʹ�÷�? <code>toISOStringBinary</code>����ת��?
     *
     * @param str ����֤���ַ�?
     * @return �������ַ���?"zh"���������ַ�����Ϊ��Ӣ���ַ���������:"en"
     */
    public static String getLanguageFromStr(String str) {
        int len;
        len = (str == null) ? 0 : str.length();
        char ch;
        for (int i = 0; i < len; i++) {
            ch = str.charAt(i);
            int value = (int) ch;
            // �����б�
            if ((value >= 0x4e00 && value <= 0x9fbb)
                    || (value >= 0x3400 && value <= 0x4db6)
                    || (value >= 0x3000 && value <= 0x303F)) {
                return "zh";
            }
        }
        return "en";
    }

    /**
     * ȫ��ת��ǵķ���
     *
     * @param QJstr ������ַ���
     * @return ת���ɰ�ǵ��ַ�?
     */
    public static final String SBCchange(String QJstr) {
        if (QJstr == null || QJstr.trim().length() == 0) {
            return QJstr;
        }
        String outStr = "";
        String Tstr = "";
        byte[] b = null;

        for (int i = 0; i < QJstr.length(); i++) {
            try {
                Tstr = QJstr.substring(i, i + 1);
                b = Tstr.getBytes("unicode");
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }

            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;

                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else
                outStr = outStr + Tstr;
        }

        return outStr;
    }

    /**
     * ���ݱ���Ƚ���ַ�����������
     *
     * @param locale
     * @param tableWidth
     * @param str
     * @return
     * @author meiyuhai
     */
    public static String newLine(Locale locale, int tableWidth, String str) {
        if (locale.getLanguage().equalsIgnoreCase("zh")) {
            return str;
        }
        int tWidth = tableWidth;
        String strContent = str;
        int lineNum = tWidth / 6;
        String strTemp = "";
        String temp = "";
        try {
            while (strContent.length() > lineNum) {
                temp = strContent.substring(0, lineNum - 1);
                int index = temp.lastIndexOf(" ");
                if (index == -1) {
                    return str;
                }
                strTemp = strTemp + strContent.substring(0, index) + "<br>";
                strContent = strContent.substring(index, strContent.length());
            }
            strTemp = strTemp + strContent;
            return strTemp.trim();
        } catch (IndexOutOfBoundsException e) {

            e.printStackTrace();
            return str;
        }
    }

    /**
     * �ж�str�Ƿ�Ϊ��
     *
     * @param str Ҫ�жϵ��ַ�
     * @return strΪnull��?Ϊ""�򷵻�true,���򷵻�false
     */
    public static boolean isEmptyStr(String str) {
        if (str == null)
            return true;
        if ("".equals(str.trim()))
            return true;
        return false;
    }

    /**
     * ��֤�մ�
     *
     * @param
     * @return
     */
    public static boolean isEmpty(Object checkStr_) {
        if (checkStr_ == null) return true;
        String checkStr = checkStr_.toString();
        return checkStr == null ? true : (checkStr.trim().length() == 0
                || "null".equalsIgnoreCase(checkStr.trim()) ? true : false);
    }

    /**
     * �Ƿ�Ϊ?
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (str != null) {
            if (str.length() == 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    /**
     * �ַ���MD5��ʽ����?<BR>
     * <br>
     * �ַ���Ϊnull���ܼ��ܵ�����·���null?
     *
     * @param aString �����ַ�?
     * @return ���ܺ���ַ�?
     */

    public static String encrypt(String aString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] tempBytes = messageDigest.digest(aString.getBytes("US-ASCII"));
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = tempBytes.length - 1; i >= 0; i--) {
                String tempByte = Integer.toHexString(tempBytes[i] & 0xFF);
                if (tempByte.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(tempByte);
            }
            return stringBuffer.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ��null���ַ���ת��?""
     *
     * @param str
     * @return
     */
    public static String getNullStr(String str) {
        if (str == null)
            return "";

        if (str.equals("null") || str.equals("NULL")) {
            return "";
        } else {
            return str;
        }
    }

    public static String getNull(Object obj) {
        if (obj == null) {
            return "";
        }
        return getNullStr(obj.toString());
    }

    /**
     * ��stringת��Ϊint��ת���쳣�򷵻�0
     *
     * @param str
     * @return
     */
    public static int getIntOfStr(String str) {
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception ingore) {
            return 0;
        }
    }

    /**
     * ��objectת��Ϊint��ת���쳣�򷵻�0
     *
     * @param obj
     * @return
     */
    public static int getIntOfObj(Object obj) {
        try {
            return Integer.parseInt(obj.toString().trim());
        } catch (Exception ingore) {
            return 0;
        }
    }

    /**
     * ��objectת��Ϊdouble��ת���쳣�򷵻�0
     *
     * @param obj
     * @return
     */
    public static double getDoubleOfObj(Object obj) {
        try {
            return Double.parseDouble(obj.toString().trim());
        } catch (Exception ingore) {
            return 0;
        }
    }

    public static String replace(String template, String placeholder,
                                 String replacement) {
        return replace(template, placeholder, replacement, false);
    }

    public static String replace(String template, String placeholder,
                                 String replacement, boolean wholeWords) {
        int loc = template.indexOf(placeholder);
        if (loc < 0) {
            return template;
        } else {
            final boolean actuallyReplace = !wholeWords
                    || loc + placeholder.length() == template.length()
                    || !Character.isJavaIdentifierPart(template.charAt(loc
                    + placeholder.length()));
            String actualReplacement = actuallyReplace ? replacement
                    : placeholder;
            return new StringBuffer(template.substring(0, loc)).append(
                    actualReplacement).append(
                    replace(template.substring(loc + placeholder.length()),
                            placeholder, replacement, wholeWords)).toString();
        }
    }

    /**
     * ��һ��ʱ���ַ�����ʽ��Ϊ��һ��ʱ���ַ���
     *
     * @param dateStr
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String DateFormat(String dateStr, String oldPattern, String newPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(oldPattern);

        Date date = null;

        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

        return new SimpleDateFormat(newPattern).format(date);
    }

    /**
     * ���߹����ʽ��ʱ?
     *
     * @param date
     * @param newPattern
     * @return
     */
    public static String DateFormat(Date date, String newPattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(newPattern);

        return sdf.format(date);
    }

    /**
     * �����ַ�����ʽ����ʱ?
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date DateFormat(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateStr);

        return date;
    }

    /**
     * �õ�����ַ�����md5����
     *
     * @return
     */
//    public static String createRandomStr32() {
//        long timestamp = new Date().getTime();
//        String str = String.valueOf(timestamp + (int) Math.random() * 100);
//        try {
//            return DigestUtils.md5Hex(str.getBytes("utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            throw new InternalError();
//        }
//    }

    /**
     * �õ�����ַ���
     *
     * @return
     */
    public static String createRandomStr(int digit) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int baseLength = base.length();
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digit; i++) {
            int number = random.nextInt(baseLength);
            stringBuilder.append(base.charAt(number));
        }
        return stringBuilder.toString();
    }

    /**
     * ���Ϊ�գ��򷵻�Ĭ����ֵ
     *
     * @param number
     * @param defNumber
     * @return
     */
    public static Integer getWithDefault(Object number, Integer defNumber) {
        if (StringUtil.isEmpty(number)) {
            return defNumber;
        }
        return Integer.valueOf(number.toString());
    }

    public static Double getWithDefault(Object object, Double defNumber) {
        if (StringUtil.isEmpty(object)) {
            return defNumber;
        }
        return (double) object;
    }
}
