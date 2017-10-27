package com.wugao.vankeda.infrastructure.spring.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.wugao.vankeda.infrastructure.filter.ParameterRequestWrapper;

public class RequestParameterFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);  
        filterChain.doFilter(requestWrapper, response);  
	}

}
