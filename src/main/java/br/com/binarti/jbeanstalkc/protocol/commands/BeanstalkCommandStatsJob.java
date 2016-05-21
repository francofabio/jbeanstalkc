package br.com.binarti.jbeanstalkc.protocol.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.STATS_JOB;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.NOT_FOUND;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.OK;
import static java.util.Arrays.asList;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkResponse;
import br.com.binarti.jbeanstalkc.protocol.JobStats;

public class BeanstalkCommandStatsJob extends BeanstalkCommandInterface {

	public BeanstalkCommandStatsJob(Socket socket) {
		super(socket);
	}

	public JobStats perform(String jobId) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(STATS_JOB)
				.addParam(jobId)
				.build();
		BeanstalkResponse response = performChunkCommand(command, 0, asList(OK), OK, NOT_FOUND);
		if (response.getReason() != NOT_FOUND && response.getParamAs(0, Integer.class) > 0) {
			return new JobStats(response.getData());
		}
		return null;
	}
	
}
