package br.com.binarti.jbeanstalkc;

import static br.com.binarti.jbeanstalkc.BeanstalkClient.DEFAULT_PUT_DEPLAY;
import static br.com.binarti.jbeanstalkc.BeanstalkClient.DEFAULT_PUT_PRIORITY;

import java.net.Socket;

import br.com.binarti.jbeanstalkc.protocol.JobStats;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandBury;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandDelete;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandRelease;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandStatsJob;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandTouch;

public class BeanstalkJob {

	private String id;
	private String body;
	private Socket socket;
	
	public BeanstalkJob(String id, String body, Socket socket) {
		this.id = id;
		this.body = body;
		this.socket = socket;
	}
	
	/**
	 * Job ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Job body
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * Removes a job from the server. This command should be used when the client finish the job processing
	 */
	public void delete() {
		new BeanstalkCommandDelete(socket).perform(this.id);
	}
	
	/**
	 * This command puts a reserved job back to into the ready queue
	 */
	public void release() {
		new BeanstalkCommandRelease(socket).perform(this.id, DEFAULT_PUT_PRIORITY, DEFAULT_PUT_DEPLAY);
	}
	
	/**
	 * This command puts a reserved job back to into the ready queue
	 * @param priority The new priority to assign to the job
	 * @param delay Number of seconds to wait before putting the job in ready queue
	 */
	public void release(int priority, int delay) {
		new BeanstalkCommandRelease(socket).perform(this.id, priority, delay);
	}
	
	/**
	 * This command "bury" puts a job into "buried" state. Buried jobs are put into a FIFO linked list and will not be touched by the server again until a client kicks them with the "kick" command.
	 */
	public void bury() {
		new BeanstalkCommandBury(socket).perform(this.id, DEFAULT_PUT_PRIORITY);
	}
	
	/**
	 * This command "bury" puts a job into "buried" state. Buried jobs are put into a FIFO linked list and will not be touched by the server again until a client kicks them with the "kick" command.
	 * @param priority The new priority to assign to the job
	 */
	public void bury(int priority) {
		new BeanstalkCommandBury(socket).perform(this.id, priority);
	}
	
	/**
	 * This command request to server more time to process a job.
	 */
	public void touch() {
		new BeanstalkCommandTouch(socket).perform(this.id);
	}
	
	/**
	 * Retrieve statistical information about this job
	 * @return Statistical information about this job
	 */
	public JobStats stats() {
		return new BeanstalkCommandStatsJob(socket).perform(this.id);
	}

}
