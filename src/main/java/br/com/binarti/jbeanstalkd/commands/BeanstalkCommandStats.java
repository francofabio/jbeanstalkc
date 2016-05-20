package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.STATS;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.OK;

import java.net.Socket;
import java.util.Arrays;

import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkResponse;
import br.com.binarti.jbeanstalkd.protocol.ServerStats;

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
