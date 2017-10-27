package com.wugao.vankeda.infrastructure.spring.format;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

public class DateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		Date date = convertA(source);
		if (date == null) {
			date = convertB(source);
			if (date == null) {
				date = convertC(source);
				if (date == null) {
					date = convertD(source);
					if (date == null) {
						date = convertE(source);
					}
				}
			}
		}
		return date;
	}

	private Date convertA(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

	private Date convertB(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

	private Date convertC(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(source);
		} catch (Exception e) {
			return null;
		}
	}
	
	private Date convertD(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(source);
		} catch (Exception e) {
			return null;
		}
	}
	
	private Date convertE(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

}
