package br.com.binarti.jbeanstalkc.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.WATCH;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.WATCHING;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkResponse;

public class BeanstalkCommandWatch extends BeanstalkCommandInterface {

	public BeanstalkCommandWatch(Socket socket) {
		super(socket);
	}

	public int perform(String tube) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(WATCH)
				.addParam(tube)
				.build();
		BeanstalkResponse response = perform(command, WATCHING);
		String watchingCount = response.getParam(0);
		return Integer.parseInt(watchingCount);
	}
	
}
