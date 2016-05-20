package br.com.binarti.jbeanstalkc.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.STATS;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.OK;

import java.net.Socket;
import java.util.Arrays;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkResponse;
import br.com.binarti.jbeanstalkc.protocol.ServerStats;

public class BeanstalkCommandStats extends BeanstalkCommandInterface {

	public BeanstalkCommandStats(Socket socket) {
		super(socket);
	}

	public ServerStats perform() {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(STATS).build();
		BeanstalkResponse response = performChunkCommand(command, 0, Arrays.asList(OK), OK);
		return new ServerStats(response.getData());
	}
	
}
