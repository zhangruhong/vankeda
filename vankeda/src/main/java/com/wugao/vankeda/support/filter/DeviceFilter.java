package com.wugao.vankeda.support.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeviceFilter implements Filter{
	
	private final List<String> mobileUserAgentPrefixes = new ArrayList<String>();

	private final List<String> mobileUserAgentKeywords = new ArrayList<String>();

	private final List<String> tabletUserAgentKeywords = new ArrayList<String>();

	private static final String TYPE_NORMAL = "normal";
	private static final String TYPE_TABLET = "tablet";
	private static final String TYPE_MOBILE = "mobile";
	private static final String DEVICE_TYPE = "custom-device-type";
	
	private static final String NORMAL_VIEW_PREFIX = "/v/";
	private static final String MOBILE_VIEW_PREFIX = "/p/";
	
	
	private static final String[] KNOWN_MOBILE_USER_AGENT_PREFIXES = new String[] {
			"w3c ", "w3c-", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq",
			"bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco",
			"eric", "hipt", "htc_", "inno", "ipaq", "ipod", "jigs", "kddi", "keji",
			"leno", "lg-c", "lg-d", "lg-g", "lge-", "lg/u", "maui", "maxo", "midp",
			"mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-", "newt", "noki",
			"palm", "pana", "pant", "phil", "play", "port", "prox", "qwap", "sage",
			"sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-",
			"siem", "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-",
			"tosh", "tsm-", "upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi",
			"wapp", "wapr", "webc", "winw", "winw", "xda ", "xda-" };

	private static final String[] KNOWN_MOBILE_USER_AGENT_KEYWORDS = new String[] {
			"blackberry", "webos", "ipod", "lge vx", "midp", "maemo", "mmp", "mobile",
			"netfront", "hiptop", "nintendo DS", "novarra", "openweb", "opera mobi",
			"opera mini", "palm", "psp", "phone", "smartphone", "symbian", "up.browser",
			"up.link", "wap", "windows ce" };

	private static final String[] KNOWN_TABLET_USER_AGENT_KEYWORDS = new String[] {
			"ipad", "playbook", "hp-tablet", "kindle" };
	
	protected void init() {
		getMobileUserAgentPrefixes().addAll(
				Arrays.asList(KNOWN_MOBILE_USER_AGENT_PREFIXES));
		getMobileUserAgentKeywords().addAll(
				Arrays.asList(KNOWN_MOBILE_USER_AGENT_KEYWORDS));
		getTabletUserAgentKeywords().addAll(
				Arrays.asList(KNOWN_TABLET_USER_AGENT_KEYWORDS));
	}
	
	protected List<String> getMobileUserAgentPrefixes() {
		return mobileUserAgentPrefixes;
	}
	
	protected List<String> getMobileUserAgentKeywords() {
		return mobileUserAgentKeywords;
	}
	
	protected List<String> getTabletUserAgentKeywords() {
		return tabletUserAgentKeywords;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		init();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rsp = (HttpServletResponse)response;
		
		//保存本次访问的路径
		String lastVisited = (String)req.getSession().getAttribute("lastVisited");
		if(lastVisited == null || !lastVisited.equals(req.getRequestURI())) {
			req.getSession().setAttribute("lastVisited", req.getRequestURI());
		}
		String userAgent = req.getHeader("User-Agent");
		String deviceType = null;
		if(req.getSession().getAttribute(DEVICE_TYPE) != null) {
			deviceType = (String)req.getSession().getAttribute(DEVICE_TYPE);
		}
		if(deviceType == null) {
			// UserAgent keyword detection of Tablet devices
			if (userAgent != null) {
				userAgent = userAgent.toLowerCase();
				// Android special case
				if (userAgent.contains("android") && !userAgent.contains("mobile")) {
					deviceType = TYPE_TABLET;
				}
				// Apple special case
				if (userAgent.contains("ipad")) {
					deviceType = TYPE_TABLET;
				}
				// Kindle Fire special case
				if (userAgent.contains("silk") && !userAgent.contains("mobile")) {
					deviceType = TYPE_TABLET;
				}
				for (String keyword : tabletUserAgentKeywords) {
					if (userAgent.contains(keyword)) {
						deviceType = TYPE_TABLET;
						break;
					}
				}
			}
			// UAProf detection
			if (req.getHeader("x-wap-profile") != null || req.getHeader("Profile") != null) {
				if (userAgent != null) {
					// Android special case
					if (userAgent.contains("android")) {
						deviceType = TYPE_MOBILE;
					}
					// Apple special case
					if (userAgent.contains("iphone") || userAgent.contains("ipod") || userAgent.contains("ipad")) {
						deviceType = TYPE_MOBILE;
					}
				}
				deviceType = TYPE_MOBILE;
			}
			// User-Agent prefix detection
			if (userAgent != null && userAgent.length() >= 4) {
				String prefix = userAgent.substring(0, 4).toLowerCase();
				if (mobileUserAgentPrefixes.contains(prefix)) {
					deviceType = TYPE_MOBILE;
				}
			}
			// Accept-header based detection
			String accept = req.getHeader("Accept");
			if (accept != null && accept.contains("wap")) {
				deviceType = TYPE_MOBILE;
			}
			// UserAgent keyword detection for Mobile devices
			if (userAgent != null) {
				// Android special case
				if (userAgent.contains("android")) {
					deviceType = TYPE_MOBILE;
				}
				// Apple special case
				if (userAgent.contains("iphone") || userAgent.contains("ipod") || userAgent.contains("ipad")) {
					deviceType = TYPE_MOBILE;
				}
				for (String keyword : mobileUserAgentKeywords) {
					if (userAgent.contains(keyword)) {
						deviceType = TYPE_MOBILE;
						break;
					}
				}
			}
			// OperaMini special case
			@SuppressWarnings("rawtypes")
			Enumeration headers = req.getHeaderNames();
			while (headers.hasMoreElements()) {
				String header = (String) headers.nextElement();
				if (header.contains("OperaMini")) {
					/*return LiteDevice.MOBILE_INSTANCE;*/
					deviceType = TYPE_MOBILE;
					break;
				}
			}
			
			if(deviceType == null) {
				deviceType = TYPE_NORMAL;
			}
		}
		deviceType = TYPE_MOBILE;
		if(deviceType != null) {
			req.getSession().setAttribute(DEVICE_TYPE, deviceType);
			if(deviceType.equals(TYPE_MOBILE) || deviceType.equals(TYPE_TABLET)) {
				String uri = req.getRequestURI();
				if(uri.startsWith(NORMAL_VIEW_PREFIX)) {
//					req.getRequestDispatcher(MOBILE_VIEW_PREFIX + uri.substring(uri.indexOf(NORMAL_VIEW_PREFIX) + MOBILE_VIEW_PREFIX.length())).forward(request, response);
					rsp.sendRedirect(MOBILE_VIEW_PREFIX + uri.substring(uri.indexOf(NORMAL_VIEW_PREFIX) + MOBILE_VIEW_PREFIX.length()) + (req.getQueryString() == null ? "" : "?" + req.getQueryString()));
				}else if(uri.startsWith(MOBILE_VIEW_PREFIX)) {
					chain.doFilter(request, response);
				}
			}else if(deviceType.equals(TYPE_NORMAL)) {
				String uri = req.getRequestURI();
				if(uri.startsWith(MOBILE_VIEW_PREFIX)) {
//					req.getRequestDispatcher(NORMAL_VIEW_PREFIX + uri.substring(uri.indexOf(MOBILE_VIEW_PREFIX) + NORMAL_VIEW_PREFIX.length())).forward(request, response);
					rsp.sendRedirect(NORMAL_VIEW_PREFIX + uri.substring(uri.indexOf(MOBILE_VIEW_PREFIX) + NORMAL_VIEW_PREFIX.length()) + (req.getQueryString() == null ? "" : "?" + req.getQueryString()));
				}else if(uri.startsWith(NORMAL_VIEW_PREFIX)) {
					chain.doFilter(request, response);
				}
			}else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
