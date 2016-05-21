package br.com.binarti.jbeanstalkc.protocol.commands;

import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.RESERVE;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandCode.RESERVE_WITH_TIMEOUT;
import static br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandReason.RESERVED;
import static java.util.Arrays.asList;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.BeanstalkJob;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkc.protocol.BeanstalkResponse;

public class BeanstalkCommandReserve extends BeanstalkCommandInterface {

	public BeanstalkCommandReserve(Socket socket) {
		super(socket);
	}

	/**
	 * When consumer (worker) wants to consume jobs from queue. Block current thread until a job is available.
	 * @return Reference to Job
	 */
	public BeanstalkJob reserve() {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(RESERVE).build();
		BeanstalkResponse response = performChunkCommand(command, 1, asList(RESERVED), RESERVED);
		String jobId = response.getParam(0);
		String jobData = response.getData();
		return new BeanstalkJob(jobId, jobData, socket);
	}
	
	/**
	 * When consumer (worker) wants to consume jobs from queue. But this command block the receive during an specific time
	 * 
	 * @param timeout The timeout in seconds to wait for a Job. After timeout done, if no job received return null.
	 * @return Reference to Job or null case timeout occurs.
	 */
	public BeanstalkJob reserve(int timeout) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(RESERVE_WITH_TIMEOUT)
				.addParam(String.valueOf(timeout))
				.build();
		BeanstalkResponse response = performChunkCommand(command, 1, asList(RESERVED), RESERVED);
		String jobId = response.getParam(0);
		String jobData = response.getData();
		return new BeanstalkJob(jobId, jobData, socket);
	}
	
}
