package br.usp.ime.escience.expressmatch.exception;

public class ExpressMatchException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExpressMatchException() {
		super();
	}

	public ExpressMatchException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExpressMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpressMatchException(String message) {
		super(message);
	}

	public ExpressMatchException(Throwable cause) {
		super(cause);
	}

	
	
}
