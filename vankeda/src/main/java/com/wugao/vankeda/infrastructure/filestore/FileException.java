package com.wugao.vankeda.infrastructure.filestore;

import com.wugao.vankeda.infrastructure.exception.AppException;

public class FileException extends AppException {

	private static final long serialVersionUID = 1L;

	public FileException(String message) {
		super(message);
	}

	public FileException(Throwable throwable) {
		super(throwable);
	}

	public FileException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
