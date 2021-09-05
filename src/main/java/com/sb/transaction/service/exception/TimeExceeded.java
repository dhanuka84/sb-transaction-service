package com.sb.transaction.service.exception;

public class TimeExceeded extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TimeExceeded(String msg) {
		super(msg);
	}
}
