package com.wugao.vankeda.infrastructure.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wugao.vankeda.infrastructure.filestore.FileInfo;

public class ServletUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getWebRootPath(HttpServletRequest req, String path) {
		return req.getSession().getServletContext().getRealPath(path).replace('\\', '/');
	}

	public static void respondFileInfo(HttpServletResponse resp, FileInfo fileInfo) {
		resp.setContentLength(fileInfo.getSize());
		resp.setContentType(ContentTypeUtil.getContentType(fileInfo.getName()));
		resp.setCharacterEncoding("UTF-8");
		resp.addHeader("Pragma", "no-cache");
		resp.addHeader("Cache-Control", "no-cache");
		resp.addHeader("Expires", "0");
		try {
			resp.addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileInfo.getName(), "UTF-8") + "\"");
		} catch (UnsupportedEncodingException e) {
			resp.addHeader("Content-Disposition", "attachment;filename=\"" + fileInfo.getName() + "\"");
		}
	}

	public static void respondBytes(HttpServletResponse resp, byte[] bytes) throws IOException {
		FileCopyUtils.copy(bytes, resp.getOutputStream());
	}

	public static void respondString(HttpServletResponse resp, String str) throws IOException {
		resp.setContentLength(str.getBytes("UTF-8").length);
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.addHeader("Pragma", "no-cache");
		resp.addHeader("Cache-Control", "no-cache");
		resp.addHeader("Expires", "0");
		resp.getWriter().write(str);
		resp.getWriter().flush();
	}

	public static void respondObjectAsJson(HttpServletResponse resp, Object obj) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.addHeader("Pragma", "no-cache");
		resp.addHeader("Cache-Control", "no-cache");
		resp.addHeader("Expires", "0");
		objectMapper.getFactory().createJsonGenerator(resp.getOutputStream(), JsonEncoding.UTF8).writeObject(obj);
		resp.getOutputStream().flush();
	}
	
	/**
	 * 判断是否用手机端访问页面
	 * @param request
	 * @return
	 */
	public static boolean judgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}

}
