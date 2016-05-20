package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.TOUCH;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.TOUCHED;

import java.net.Socket;

import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;

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
