package com.cds.bigdata.parrot.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by chendongsheng5 on 2017/2/8.
 */
public class DateUtils {

  public static final String FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  public static final TimeZone TIMEZONE_ASIA_SHANGHAI = TimeZone.getTimeZone("Asia/Shanghai");
  public static final Locale DEFAULT_LOCALE = Locale.CHINA;
  private static final String[] TIME_FORMAT_TO_PARSE = new String[]{
      FORMAT_ISO_8601,
      "yyyy-MM-dd'T'HH:mm:ss'Z'",
      "yyyy-MM-dd HH:mm:ss",
      "yyyy-MM-dd",
      "yyyy/MM/dd'T'HH:mm:ss.SSS'Z'",
      "yyyy/MM/dd'T'HH:mm:ss'Z'",
      "yyyy/MM/dd HH:mm:ss",
      "yyyy/MM/dd",
      "yyyyMMddHHmmss",
      "yyyyMMdd"
  };

  private DateUtils() {

  }

  /**
   * 使用默认的格式格式化日期
   */
  public static String format(final long date) {
    return format(date, DateUtils.FORMAT_ISO_8601);
  }

  public static String format(final long date, final String format) {
    return format(new Date(date), format);
  }

  /**
   * 使用默认的格式格式化日期
   */
  public static String format(final Date date) {
    return format(date, DateUtils.FORMAT_ISO_8601);
  }

  /**
   * 按给定时间格式格式化时间
   *
   * @param date The date to format.
   * @param format The date format to use.
   * @return The formatted date.
   */
  public static String format(final Date date, final String format) {
    if (date == null) {
      throw new IllegalArgumentException("Date is null");
    }

    DateFormat formatter = new SimpleDateFormat(format, DEFAULT_LOCALE);
    formatter.setTimeZone(TIMEZONE_ASIA_SHANGHAI);

    return formatter.format(date);
  }

  /**
   * 使用默认的格式解析日期
   *
   * @param dateStr The date to parse.
   * @return The parsed date.
   */
  public static Date parse(String dateStr) {
    Date date = null;
    for (String format : TIME_FORMAT_TO_PARSE) {
      date = parse(dateStr, format);
      if (date != null) {
        break;
      }
    }
    return date;
  }

  public static Date parse(String dateStr, Locale locale, TimeZone timeZone) {
    Date date = null;
    for (String format : TIME_FORMAT_TO_PARSE) {
      date = parse(dateStr, format, locale, timeZone);
      if (date != null) {
        break;
      }
    }
    return date;
  }

  /**
   * 解析时间字符串
   *
   * @param dateStr The date to parse.
   * @param format The date formats to use sorted by completeness.
   * @return The parsed date.
   */
  public static Date parse(String dateStr, String format) {
    return parse(dateStr, format, Locale.CHINA, TIMEZONE_ASIA_SHANGHAI);
  }

  public static Date parse(String dateStr, String format, Locale locale, TimeZone timeZone) {
    Date result = null;

    if (dateStr == null) {
      throw new IllegalArgumentException("Date is null");
    }

    DateFormat parser = new SimpleDateFormat(format, locale);
    parser.setTimeZone(timeZone);
    try {
      result = parser.parse(dateStr);
    } catch (Exception e) {
      // 尝试其他格式
    }

    return result;
  }

  /**
   * 计算一定时间后的日期
   */
  public static Date after(Date startDate, long delayMs) {
    Calendar calendar = Calendar.getInstance(TIMEZONE_ASIA_SHANGHAI);
    calendar.setTime(startDate);
    calendar.add(Calendar.MILLISECOND, (int) delayMs);
    return calendar.getTime();
  }
}
