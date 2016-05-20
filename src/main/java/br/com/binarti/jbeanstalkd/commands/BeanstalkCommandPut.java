package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.PUT;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.INSERTED;

import java.net.Socket;

import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandException;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkResponse;

public class BeanstalkCommandPut extends BeanstalkCommandInterface {

	public BeanstalkCommandPut(Socket socket) {
		super(socket);
	}

	public String perform(String jobBody, int priority, int deplay, int timeToRun) throws BeanstalkCommandException, BeanstalkCommandException {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(PUT)
				.addParam(String.valueOf(priority))
				.addParam(String.valueOf(deplay))
				.addParam(String.valueOf(timeToRun))
				.withData(jobBody)
				.build();
		BeanstalkResponse response = perform(command, INSERTED);
		return response.getParam(0);
	}
	
}
