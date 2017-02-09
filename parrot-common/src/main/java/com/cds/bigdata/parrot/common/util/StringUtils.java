package com.cds.bigdata.parrot.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by chendongsheng5 on 2017/2/8.
 */
public class StringUtils {

  /**
   * 判断字符串是否为空（null或者“”）
   *
   * @param s
   * @return
   */
  public static boolean isEmptyOrNull(String s){
    return s == null || s.trim().isEmpty();
  }

  /**
   * 判断字符串数组是否为空（null或者元素个数为0）
   *
   * @param s
   * @return
   */
  public static boolean isEmptyOrNull(String[] s){
    return s == null || s.length == 0;
  }

  /**
   * 将一个数组中的元素连接成一个字符串,格式如下：
   * <clause><items[0]><splitter><items[1]>...<clause>
   * @param items
   * @param splitter
   * @param clause
   * @return
   */
  public static String mkString(String[] items, String splitter, String clause){
    StringBuilder sb;
    if(!isEmptyOrNull(items)) {
      sb = new StringBuilder(items[0]);
      for(int i = 1; i < items.length; i++){
        sb.append(splitter).append(items[i]);
      }
    }else{
      sb = new StringBuilder();
    }
    return isEmptyOrNull(clause)? sb.toString()
        : clause + sb.toString() + clause;
  }

  /**
   * 将一个数组中的元素连接成一个字符串,格式如下：
   * <clause><items[0]><splitter><items[1]>...<clause>
   * @param items
   * @param splitter
   * @param clause
   * @return
   */
  public static String mkString(List<String> items, String splitter, String clause){
    StringBuilder sb;
    if(items != null && items.size() > 0) {
      sb = new StringBuilder(items.get(0));
      for(int i = 1; i < items.size(); i++){
        sb.append(splitter).append(items.get(i));
      }
    }else{
      sb = new StringBuilder();
    }
    return isEmptyOrNull(clause)? sb.toString()
        : clause + sb.toString() + clause;
  }

  /**
   * 将一个Map中各个元素连接成字符串
   * <clause><items[0].key><connector><items[0].value><splitter><items[1].key>...<clause>
   * @param src
   * @param splitter
   * @param connector
   * @param clause
   * @return
   */
  public static String mkString(Map<String, String> src, String splitter, String connector, String clause) {
    return mkString(mkStrings(src, connector), splitter, clause);
  }

  /**
   * 将一个Map中各个元素连接成字符串素组
   * [<items[0].key><connector><items[0].value>]
   * @param src
   * @param connector
   * @return
   */
  public static String[] mkStrings(Map<String, String> src, String connector) {
    String[] items = new String[src.size()];
    int i = 0;
    for (Map.Entry<String, String> entry : src.entrySet()) {
      items[i++] = entry.getKey() + connector + entry.getValue();
    }
    return items;
  }

  /**
   * 去掉一个字符串头尾包裹的字符,如stripe("\'abc\'", "\'") => "abc"
   * @param s
   * @param token
   * @return
   */
  public static String stripe(String s, String token) {
    return stripe(s, token, token);
  }

  /**
   * 去掉一个字符串头尾包裹的字符,stripe("(abc)", "(", ")") => "abc"
   * @param s
   * @param token1
   * @param token2
   * @return
   */
  public static String stripe(String s, String token1, String token2) {
    if(s.startsWith(token1) && s.endsWith(token2)){
      return s.substring(token1.length(), s.length() - token2.length());
    }
    return s;
  }

  /**
   * 解析参数适用
   * @param args
   * @param delim
   * @param clause
   * @return
   */
  public static String[] tokenizer(String args, String delim, String clause){
    List<String> tokens = new ArrayList<>();
    StringTokenizer tokenizer = new StringTokenizer(args, delim);
    boolean inline = false;
    StringBuilder currentElement = new StringBuilder();
    while (tokenizer.hasMoreTokens()){
      String token = tokenizer.nextToken();
      if(!inline){
        if(token.startsWith(clause)) {
          if(token.length() > 1 && token.endsWith(clause)) {
            currentElement.append(token.substring(1, token.length() - 1));
          }else{
            currentElement.append(token.substring(1));
            inline = true;
          }
        }else {
          currentElement.append(token);
        }
      }else if(inline){
        if(token.endsWith(clause)){
          currentElement.append(delim).append(token.substring(0, token.length() - 1));
          inline = false;
        }else{
          currentElement.append(delim).append(token);
        }
      }
      if(!inline) {
        tokens.add(currentElement.toString().trim());
        currentElement = new StringBuilder();
      }
    }
    if(inline){
      tokens.add(currentElement.toString().trim());
    }
    return tokens.toArray(new String[tokens.size()]);
  }
}
