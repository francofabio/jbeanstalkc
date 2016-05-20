package br.com.binarti.jbeanstalkd.protocol;

import static br.com.binarti.jbeanstalkd.BeanstalkClientHelper.COMMAND_LINE_SEPARATOR;

import java.util.List;

public class BeanstalkRequest extends BeanstalkPackage {

	private BeanstalkCommandCode command;

	public BeanstalkRequest(BeanstalkCommandCode command, List<String> params, String data) {
		super(params, data);
		this.command = command;
	}
	
	public String encode() {
		StringBuilder encoded = new StringBuilder();
		encoded.append(command.getCode());
		List<String> params = this.params;
		if (!params.isEmpty()) {
			encoded.append(" ");
			encoded.append(String.join(" ", params));
		}
		if (isChunk()) {
			encoded.append(" ").append(data.length());
		}
		encoded.append(COMMAND_LINE_SEPARATOR);
		if (isChunk()) {
			encoded.append(data).append(COMMAND_LINE_SEPARATOR);
		}
		return encoded.toString();
	}
	
}
