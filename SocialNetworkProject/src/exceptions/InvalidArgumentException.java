package exceptions;

public class InvalidArgumentException extends Exception {

	
	private static final long serialVersionUID = -5617758274483170018L;

	public InvalidArgumentException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidArgumentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidArgumentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidArgumentException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidArgumentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
