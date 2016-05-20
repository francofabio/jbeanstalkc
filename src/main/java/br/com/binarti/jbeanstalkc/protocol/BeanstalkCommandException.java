package br.com.binarti.jbeanstalkc.protocol;

import java.util.Arrays;

import br.com.binarti.jbeanstalkc.BeanstalkException;

@SuppressWarnings("serial")
public class BeanstalkCommandException extends BeanstalkException {

	private String reason;
	private BeanstalkCommandReason[] expectedReasons;
	
	public BeanstalkCommandException(String message) {
		super(message);
	}
	
	public BeanstalkCommandException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BeanstalkCommandException(String reason, BeanstalkCommandReason...expectedReasons) {
		super(String.format("Unexpected server response %s. Expected responses: %s", reason, Arrays.asList(expectedReasons)));
		this.reason = reason;
		this.expectedReasons = expectedReasons;
	}

	public String getReason() {
		return reason;
	}
	
	public BeanstalkCommandReason[] getExpectedReasons() {
		return expectedReasons;
	}
	
}
