package br.com.binarti.jbeanstalkc;

import java.util.List;

import br.com.binarti.jbeanstalkc.protocol.ServerStats;
import br.com.binarti.jbeanstalkc.protocol.TubeStats;

public interface BeanstalkClient extends BeanstalkProducer, BeanstalkConsumer {

	String DEFAULT_BEANSTALK_TUBE = "default";

	int DEFAULT_PUT_PRIORITY = (int) Math.pow(2, 31);
	int DEFAULT_PUT_DEPLAY = 0;
	int DEFAULT_PUT_TIME_TO_RUN = 120;

	/**
	 * The stats command gives statistical information about the system as a whole
	 * @return Statistical information about the beanstalkd System
	 */
	ServerStats serverStats();

	/**
	 * Retrieve statistical information about the current tube if it exists.
	 * @return Statistical information about the specified tube if it exists, otherwise, return <code>null</code>
	 */
	TubeStats currentTubeStats();
	
	/**
	 * The stats-tube command gives statistical information about the specified tube if it exists.
	 * @return Statistical information about the specified tube if it exists, otherwise, return <code>null</code>
	 */
	TubeStats tubeStats(String tube);
	
	/**
	 * Returns the list of all existing tubes
	 * @return List of all existing tubes
	 */
	List<String> listTubes();
	
	/**
	 * Return a job by ID
	 * @param jobId The job ID
	 */
	BeanstalkJob peekJob(String jobId);
	
	/**
	 * Return the next job in ready queue or null.
	 */
	BeanstalkJob peekReady();
	
	/**
	 * Return the delayed job with the shortest delay left
	 */
	BeanstalkJob peekDelayed();
	
	/**
	 * Return the next job in buried queue or null.
	 */
	BeanstalkJob peekBuried();
	
//	/**
//	 * Change the currently tube to a give tube. The "use" command is for producers. <br>
//	 * Subsequent put commands will put jobs into the tube specified by this command. <br>
//	 * If no use command has been issued, jobs will be put into the tube named "default".
//	 * 
//	 * @param tube Tube name
//	 */
//	public void useTube(String tube) {
//		checkConnection();
//		if (tube == null) {
//			Objects.requireNonNull(tube, "Tube name could not be null");
//		}
//		//If use command use return USING and currentTube is null, update currentTube instance variable to tube value
//		new BeanstalkCommandUse(socket).perform(tube);
//		if (currentTube == null) {
//			currentTube = tube;
//		}
//	}
//	
//	/**
//	 * Returns the tube currently being used by the client. Invoke list-tube-used command in server.
//	 * @return The tube currently being used by the client.
//	 */
//	public String using() {
//		checkConnection();
//		return new BeanstalkCommandListTubeUsed(socket).perform();
//	}
//
//	/**
//	 * Inserts a job into the currently tube.<br/>
//	 * 
//	 * @param jobBody The job body
//	 * @param priority The job priority. Jobs with smaller priority values will be scheduled before jobs with larger priorities. The most urgent priority is 0; the least urgent priority is 4,294,967,295.
//	 * @param deplay The number of seconds to wait before putting the job in the read queue.
//	 * @param timeToRun The number of seconds to allow a worker to run this job.
//	 * @return The id job.
//	 */
//	public String put(String jobBody, int priority, int deplay, int timeToRun) {
//		checkConnection();
//		return new BeanstalkCommandPut(socket).perform(jobBody, priority, deplay, timeToRun);
//	}
//
//	/**
//	 * Inserts a job into the currently tube using default parameters.<br/>
//	 * 
//	 * @param jobBody The job body
//	 * @return The id job.
//	 */
//	public String put(String jobBody) {
//		checkConnection();
//		return put(jobBody, DEFAULT_PUT_PRIORITY, DEFAULT_PUT_DEPLAY, DEFAULT_PUT_TIME_TO_RUN);
//	}
//
//	/**
//	 * When consumer (worker) wants to consume jobs from queue. The current thread will be blocked while not receive a job.
//	 * @return Reference to Job
//	 */
//	public BeanstalkJob reserve() {
//		checkConnection();
//		return new BeanstalkCommandReserve(socket).reserve();
//	}
//	
//	/**
//	 * When consumer (worker) wants to consume jobs from queue. But this command block the receive during an specific time
//	 * 
//	 * @param timeout The timeout in seconds to wait for a Job. After timeout done, if no job received return null.
//	 * @return Reference to Job or null case timeout occurs.
//	 */
//	public BeanstalkJob reserve(int timeout) {
//		checkConnection();
//		return new BeanstalkCommandReserve(socket).reserve(timeout);
//	}
//	
//	/**
//	 * This command indicates to server that worker has interest to receive jobs from a tube (queue)
//	 * @param tube Tube that work has interest
//	 * @return The number of tubes currently in the watch list
//	 */
//	public int watch(String tube) {
//		checkConnection();
//		return new BeanstalkCommandWatch(socket).perform(tube);
//	}
//	
//	/**
//	 * This command indicates to server that consumer don't has interest to receive jobs from tube (queue)
//	 * @param tube Tube that work don't has interest
//	 */
//	public int ignore(String tube) {
//		checkConnection();
//		return new BeanstalkCommandIgnore(socket).perform(tube);
//	}
//	
//	/**
//	 * Returns the tubes currently being watched by worker.
//	 * @return The tubes currently being watched by worker.
//	 */
//	public List<String> watching() {
//		checkConnection();
//		return new BeanstalkCommandListTubeWatched(socket).perform();
//	}
	
}
