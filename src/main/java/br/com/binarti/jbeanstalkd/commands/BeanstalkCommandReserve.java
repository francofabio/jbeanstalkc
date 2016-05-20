package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.RESERVE;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.RESERVE_WITH_TIMEOUT;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.RESERVED;
import static java.util.Arrays.asList;

import java.net.Socket;

import br.com.binarti.jbeanstalkd.BeanstalkJob;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkResponse;

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
