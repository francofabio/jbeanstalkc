package br.com.binarti.jbeanstalkc.protocol;

import java.util.ArrayList;
import java.util.List;

public class BeanstalkResponse extends BeanstalkPackage {

	private BeanstalkCommandReason reason;

	public BeanstalkResponse(BeanstalkCommandReason reason, List<String> params, String data) {
		super(params, data);
		this.reason = reason;
	}

	public BeanstalkCommandReason getReason() {
		return reason;
	}
	
	
	public static BeanstalkResponse decode(String response) {
		if (response.endsWith("\r\n")) {
			response = response.substring(0, response.length()-2);
		}
		String[] responseParts = response.split(" ");
		BeanstalkCommandReason reason = BeanstalkCommandReason.valueOf(responseParts[0]);
		List<String> params = new ArrayList<>();
		if (responseParts.length > 1) {
			for (int i=1; i < responseParts.length; i++) {
				params.add(responseParts[i]);
			}
		}
		return new BeanstalkResponse(reason, params, null);
	}
	
}
