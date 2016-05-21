package br.com.binarti.jbeanstalkc.protocol.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.PUT;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.INSERTED;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandException;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkResponse;

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
