package br.com.binarti.jbeanstalkc.protocol.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.IGNORE;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.WATCHING;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkResponse;

public class BeanstalkCommandIgnore extends BeanstalkCommandInterface {

	public BeanstalkCommandIgnore(Socket socket) {
		super(socket);
	}

	public int perform(String tube) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(IGNORE)
				.addParam(tube)
				.build();
		BeanstalkResponse response = perform(command, WATCHING);
		String watchingCount = response.getParam(0);
		return Integer.parseInt(watchingCount);
	}
	
}
