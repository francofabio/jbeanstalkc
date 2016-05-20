package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.RELEASED;

import java.net.Socket;

import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;

public class BeanstalkCommandRelease extends BeanstalkCommandInterface {

	public BeanstalkCommandRelease(Socket socket) {
		super(socket);
	}

	public void perform(String jobId, int priority, int delay) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(BeanstalkCommandCode.RELEASE)
				.addParam(jobId).addParam(String.valueOf(priority)).addParam(String.valueOf(delay))
				.build();
		perform(command, RELEASED);
	}
	
}
