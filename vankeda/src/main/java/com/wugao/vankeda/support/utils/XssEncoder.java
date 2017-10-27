package com.wugao.vankeda.support.utils;

/**
 * 将字符串中的特殊字符转换为全角字符
 * @author Administrator
 *
 */
public class XssEncoder {
	
	/**
	 * 
	 * @param s 需要转换的字符串
	 * @return
	 */
	 public static  String encode(String s) {  
        if (s == null || s.isEmpty()) {  
            return s;  
        }  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < s.length(); i++) {  
            char c = s.charAt(i);  
            switch (c) {  
            case '>':  
                sb.append("&gt;");// 大于号  
                break;  
            case '<':  
                sb.append("&lt;");// 小于号  
                break;  
            case '&':
            	sb.append("&amp;");
            	break;
           default: sb.append(c);
           }
        }
        return sb.toString();  
    }  

}
