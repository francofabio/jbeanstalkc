package br.com.binarti.jbeanstalkc.protocol;

import java.util.ArrayList;
import java.util.List;

public class BeanstalkCommandDataBuilder {

	private BeanstalkCommandCode command;
	private List<String> params;
	private String data;
	
	public BeanstalkCommandDataBuilder(BeanstalkCommandCode command) {
		this.command = command;
		this.params = new ArrayList<>();
	}
	
	public BeanstalkCommandDataBuilder addParam(String val) {
		this.params.add(val);
		return this;
	}
	
	public BeanstalkCommandDataBuilder withData(String data) {
		this.data = data;
		return this;
	}
	
	public BeanstalkRequest build() {
		return new BeanstalkRequest(command, params, data);
	}
	
}
