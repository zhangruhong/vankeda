package com.wugao.vankeda.infrastructure.spring.web.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;

import com.wugao.vankeda.infrastructure.exception.AppException;
import com.wugao.vankeda.infrastructure.utils.ServletUtil;
import com.wugao.vankeda.infrastructure.validation.ValidateException;

public class HandlerExceptionResolver implements org.springframework.web.servlet.HandlerExceptionResolver, Ordered {

	private static final Log log = LogFactory.getLog(HandlerExceptionResolver.class);
	
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> badRequestReason = new HashMap<String, Object>();
		ex.printStackTrace();
		try {
			if (ex instanceof BindException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				badRequestReason.put("validateError", convertToValidateException((BindException) ex).getErrors());
			} else if (ex instanceof ValidateException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				badRequestReason.put("validateError", ((ValidateException) ex).getErrors());
			} else if (ex instanceof AppException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				badRequestReason.put("applicationError", ((AppException) ex).getMessage());
			} else {
				log.error("处理请求时发生异常 " + request.getMethod() + " " + request.getRequestURI(), ex);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				badRequestReason.put("applicationError", ((AppException) ex).getMessage());
			}
			// for bad request
			if (!response.isCommitted() && !badRequestReason.isEmpty()) {
				ServletUtil.respondObjectAsJson(response, badRequestReason);
			}
		} catch (Throwable throwable) {
			log.warn("处理响应状态发生异常", throwable);
		}
		
		
		// 如果该方法返回了null，则Spring会继续寻找其他的HandlerExceptionResolver，直到返回了一个ModelAndView对象。
		return new ModelAndView();
	}
	
	
	private ValidateException convertToValidateException(BindException bindException) {
		BindingResult bindingResult = bindException.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		Map<String, String> errors = new HashMap<String, String>();
		String fieldTypeName;
		String fieldName;
		for (FieldError fe : fieldErrors) {
			fieldName = fe.getField();
			fieldTypeName = convertClassName(bindingResult.getFieldType(fieldName));
			if (!errors.containsKey(fieldName)) {
				errors.put(fieldName, "必须为" + fieldTypeName);
			}
		}
		return new ValidateException(errors);
	}

	private String convertClassName(Class<?> clazz) {
		if (clazz.equals(Integer.class)) {
			return "整数";
		} else if (clazz.equals(Float.class)) {
			return "浮点数";
		} else if (clazz.equals(Double.class)) {
			return "双精度浮点数";
		} else if (clazz.equals(Boolean.class)) {
			return "布尔型";
		} else {
			return clazz.getSimpleName();
		}
	}
	
	
}
