package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.DELETE;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.DELETED;

import java.net.Socket;

import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;

public class BeanstalkCommandDelete extends BeanstalkCommandInterface {

	public BeanstalkCommandDelete(Socket socket) {
		super(socket);
	}

	public void perform(String jobId) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(DELETE)
				.addParam(jobId)
				.build();
		perform(command, DELETED);
	}
	
}
