package com.wugao.vankeda.infrastructure.spring.cache;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.google.common.base.Objects;

public class KeyGenerator implements org.springframework.cache.interceptor.KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		return Objects.hashCode(target.getClass().getName(), method.getName(), Arrays.deepHashCode(params));
	}

}
