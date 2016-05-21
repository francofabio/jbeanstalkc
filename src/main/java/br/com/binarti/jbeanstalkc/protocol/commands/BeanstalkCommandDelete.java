package br.com.binarti.jbeanstalkc.protocol.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.DELETE;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.DELETED;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.NOT_FOUND;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;

public class BeanstalkCommandDelete extends BeanstalkCommandInterface {

	public BeanstalkCommandDelete(Socket socket) {
		super(socket);
	}

	public void perform(String jobId) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(DELETE)
				.addParam(jobId)
				.build();
		perform(command, DELETED, NOT_FOUND);
	}
	
}
