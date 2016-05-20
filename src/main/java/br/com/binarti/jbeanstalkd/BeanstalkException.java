package br.com.binarti.jbeanstalkd;

@SuppressWarnings("serial")
public class BeanstalkException extends RuntimeException {

	public BeanstalkException() {
	}

	public BeanstalkException(String message) {
		super(message);
	}

	public BeanstalkException(Throwable cause) {
		super(cause);
	}

	public BeanstalkException(String message, Throwable cause) {
		super(message, cause);
	}

}
