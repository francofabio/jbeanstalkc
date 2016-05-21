package br.com.binarti.jbeanstalkc.protocol.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.USE;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.USING;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandException;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkResponse;

public class BeanstalkCommandUse extends BeanstalkCommandInterface {

	public BeanstalkCommandUse(Socket socket) {
		super(socket);
	}

	public String perform(String tube) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(USE)
				.addParam(tube)
				.build();
		BeanstalkResponse response = perform(command, USING);
		String usingTube = response.getParam(0); 
		if (!tube.equals(usingTube)) {
			throw new BeanstalkCommandException(String.format("Server not changed the current tube to %s. The server responded that it is using tube %s", tube, usingTube));
		}
		return usingTube;
	}
	
}
