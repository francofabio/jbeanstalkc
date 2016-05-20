package br.com.binarti.jbeanstalkc.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.RELEASED;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;

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
