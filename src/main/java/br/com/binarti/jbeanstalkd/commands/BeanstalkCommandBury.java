package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.BURIED;

import java.net.Socket;

import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;

public class BeanstalkCommandBury extends BeanstalkCommandInterface {

	public BeanstalkCommandBury(Socket socket) {
		super(socket);
	}

	public void perform(String jobId, int priority) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(BeanstalkCommandCode.BURY)
				.addParam(jobId).addParam(String.valueOf(priority))
				.build();
		perform(command, BURIED);
	}
	
}
