package br.com.binarti.jbeanstalkc.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.LIST_TUBE_USED;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.USING;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkResponse;

public class BeanstalkCommandListTubeUsed extends BeanstalkCommandInterface {

	public BeanstalkCommandListTubeUsed(Socket socket) {
		super(socket);
	}

	public String perform() {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(LIST_TUBE_USED).build();
		BeanstalkResponse response = perform(command, USING);
		return response.getParam(0);
	}
	
}
