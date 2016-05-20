package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.LIST_TUBE_USED;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.USING;

import java.net.Socket;

import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkResponse;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;

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
