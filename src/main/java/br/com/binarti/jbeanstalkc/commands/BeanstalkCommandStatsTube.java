package br.com.binarti.jbeanstalkc.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.STATS_TUBE;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.NOT_FOUND;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.OK;

import java.net.Socket;
import java.util.Arrays;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkResponse;
import br.com.binarti.jbeanstalkc.protocol.TubeStats;

public class BeanstalkCommandStatsTube extends BeanstalkCommandInterface {

	public BeanstalkCommandStatsTube(Socket socket) {
		super(socket);
	}

	public TubeStats perform(String tube) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(STATS_TUBE)
				.addParam(tube).build();
		BeanstalkResponse response = performChunkCommand(command, 0, Arrays.asList(OK), NOT_FOUND, OK);
		if (response.getReason() == NOT_FOUND) {
			return null;
		}
		return new TubeStats(response.getData());
	}
	
}
