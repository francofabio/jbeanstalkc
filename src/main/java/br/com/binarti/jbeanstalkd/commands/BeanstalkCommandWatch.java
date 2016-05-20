package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.WATCH;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.WATCHING;

import java.net.Socket;

import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkResponse;

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
