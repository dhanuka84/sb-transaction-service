package com.sb.transaction.service.exception;

public class NotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFound(String msg) {
		super(msg);
	}
}
