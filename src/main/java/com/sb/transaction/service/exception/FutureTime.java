package com.sb.transaction.service.exception;

public class FutureTime extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FutureTime(String msg) {
		super(msg);
	}
}
