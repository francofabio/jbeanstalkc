package br.com.binarti.jbeanstalkc.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.TOUCH;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.TOUCHED;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;

public class BeanstalkCommandTouch extends BeanstalkCommandInterface {

	public BeanstalkCommandTouch(Socket socket) {
		super(socket);
	}

	public void perform(String jobId) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(TOUCH)
				.addParam(jobId)
				.build();
		perform(command, TOUCHED);
	}
	
}
