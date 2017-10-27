package com.wugao.vankeda.support.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wugao.vankeda.infrastructure.constant.SessionConstant;
import com.wugao.vankeda.support.session.Context;

public class AuthenticationFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		HttpSession session = httpServletRequest.getSession();
		Context context = (Context)session.getAttribute(SessionConstant.CONTEXT);
		String lastVisited = (String)session.getAttribute("lastVisited");
		if(lastVisited == null || !lastVisited.equals(httpServletRequest.getRequestURI())) {
			session.setAttribute("lastVisited", httpServletRequest.getRequestURI());
		}
		if(context == null) {
//			request.getRequestDispatcher("/WEB-INF/view/error/403.jsp").forward(request, response);
			httpServletResponse.sendRedirect("/login");
		}else {
			// TOTO 已登陆，需判断权限是否充足
			chain.doFilter(httpServletRequest, httpServletResponse);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
