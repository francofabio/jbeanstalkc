package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.PEEK;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.PEEK_BURIED;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.PEEK_DELAYED;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.PEEK_READY;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.FOUND;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.NOT_FOUND;
import static java.util.Arrays.asList;

import java.net.Socket;

import br.com.binarti.jbeanstalkd.BeanstalkJob;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkResponse;

public class BeanstalkCommandPeek extends BeanstalkCommandInterface {

	public BeanstalkCommandPeek(Socket socket) {
		super(socket);
	}

	private BeanstalkJob processPeekResponse(BeanstalkResponse response) {
		if (response.getReason() == NOT_FOUND) {
			return null;
		}
		String jobId = response.getParam(0);
		String jobData = response.getData();
		return new BeanstalkJob(jobId, jobData, socket);
	}
	
	/**
	 * Return a job by ID
	 * @param jobId The job ID
	 */
	public BeanstalkJob job(String jobId) {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(PEEK)
				.addParam(jobId)
				.build();
		BeanstalkResponse response = performChunkCommand(command, 1, asList(FOUND), FOUND, NOT_FOUND);
		return processPeekResponse(response);
	}
	
	/**
	 * Return the next job in ready queue or null.
	 */
	public BeanstalkJob ready() {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(PEEK_READY).build();
		BeanstalkResponse response = performChunkCommand(command, 1, asList(FOUND), FOUND, NOT_FOUND);
		return processPeekResponse(response);
	}
	
	/**
	 * Return the delayed job with the shortest delay left
	 */
	public BeanstalkJob delayed() {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(PEEK_DELAYED).build();
		BeanstalkResponse response = performChunkCommand(command, 1, asList(FOUND), FOUND, NOT_FOUND);
		return processPeekResponse(response);
	}
	
	/**
	 * Return the next job in buried queue or null.
	 */
	public BeanstalkJob buried() {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(PEEK_BURIED).build();
		BeanstalkResponse response = performChunkCommand(command, 1, asList(FOUND), FOUND, NOT_FOUND);
		return processPeekResponse(response);
	}
	
}
