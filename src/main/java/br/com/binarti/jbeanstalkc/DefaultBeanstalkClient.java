package br.com.binarti.jbeanstalkc;

import static java.lang.String.format;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

import br.com.binarti.jbeanstalkc.protocol.ServerStats;
import br.com.binarti.jbeanstalkc.protocol.TubeStats;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandIgnore;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandListTubeUsed;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandListTubeWatched;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandListTubes;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandPeek;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandPut;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandReserve;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandStats;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandStatsTube;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandUse;
import br.com.binarti.jbeanstalkc.protocol.commands.BeanstalkCommandWatch;

public class DefaultBeanstalkClient implements BeanstalkClient, BeanstalkProducer, BeanstalkConsumer {

	static final String DEFAULT_BEANSTALK_TUBE = "default";

	static final int DEFAULT_PUT_PRIORITY = (int) Math.pow(2, 31);
	static final int DEFAULT_PUT_DEPLAY = 0;
	static final int DEFAULT_PUT_TIME_TO_RUN = 120;
	
	private Socket socket;
	private String host;
	private int port;
	
	private String currentTube;
	
	public DefaultBeanstalkClient(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	private void checkConnection() {
		if (socket == null || !socket.isConnected()) {
			throw new BeanstalkException("No connection active");
		}
	}
	
	@Override
	public void connect() {
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			throw new BeanstalkException(format("Unable to connect to server %s on port %s", host, port), e);
		}
	}
	
	@Override
	public void close() {
		checkConnection();
		try {
			socket.close();
		} catch (IOException e) {
			throw new BeanstalkException(format("Unable to close connection from server %s on port %s", host, port), e);
		}
	}

	@Override
	public boolean isConnected() {
		return (socket != null && socket.isConnected());
	}

	/**
	 * The stats command gives statistical information about the system as a whole
	 * @return Statistical information about the beanstalkd System
	 */
	@Override
	public ServerStats serverStats() {
		checkConnection();
		return new BeanstalkCommandStats(socket).perform();
	}

	/**
	 * Retrieve statistical information about the current tube if it exists.
	 * @return Statistical information about the specified tube if it exists, otherwise, return <code>null</code>
	 */
	@Override
	public TubeStats currentTubeStats() {
		checkConnection();
		String currentTube = this.currentTube;
		if (currentTube == null) {
			currentTube = DEFAULT_BEANSTALK_TUBE;
		}
		return tubeStats(currentTube);
	}
	
	/**
	 * The stats-tube command gives statistical information about the specified tube if it exists.
	 * @return Statistical information about the specified tube if it exists, otherwise, return <code>null</code>
	 */
	@Override
	public TubeStats tubeStats(String tube) {
		checkConnection();
		return new BeanstalkCommandStatsTube(socket).perform(tube);
	}
	
	/**
	 * Returns the list of all existing tubes
	 * @return List of all existing tubes
	 */
	@Override
	public List<String> listTubes() {
		checkConnection();
		return new BeanstalkCommandListTubes(socket).perform();
	}
	
	/**
	 * Return a job by ID
	 * @param jobId The job ID
	 */
	@Override
	public BeanstalkJob peekJob(String jobId) {
		return new BeanstalkCommandPeek(socket).job(jobId);
	}
	
	/**
	 * Return the next job in ready queue or null.
	 */
	@Override
	public BeanstalkJob peekReady() {
		return new BeanstalkCommandPeek(socket).ready();
	}
	
	/**
	 * Return the delayed job with the shortest delay left
	 */
	@Override
	public BeanstalkJob peekDelayed() {
		return new BeanstalkCommandPeek(socket).delayed();
	}
	
	/**
	 * Return the next job in buried queue or null.
	 */
	@Override
	public BeanstalkJob peekBuried() {
		return new BeanstalkCommandPeek(socket).buried();
	}
	
	/**
	 * Change the currently tube to a give tube. The "use" command is for producers. <br>
	 * Subsequent put commands will put jobs into the tube specified by this command. <br>
	 * If no use command has been issued, jobs will be put into the tube named "default".
	 * 
	 * @param tube Tube name
	 */
	@Override
	public void useTube(String tube) {
		checkConnection();
		if (tube == null) {
			Objects.requireNonNull(tube, "Tube name could not be null");
		}
		//If use command use return USING and currentTube is null, update currentTube instance variable to tube value
		new BeanstalkCommandUse(socket).perform(tube);
		if (currentTube == null) {
			currentTube = tube;
		}
	}
	
	/**
	 * Returns the tube currently being used by the client. Invoke list-tube-used command in server.
	 * @return The tube currently being used by the client.
	 */
	@Override
	public String using() {
		checkConnection();
		return new BeanstalkCommandListTubeUsed(socket).perform();
	}

	/**
	 * Inserts a job into the currently tube.<br/>
	 * 
	 * @param jobBody The job body
	 * @param priority The job priority. Jobs with smaller priority values will be scheduled before jobs with larger priorities. The most urgent priority is 0; the least urgent priority is 4,294,967,295.
	 * @param deplay The number of seconds to wait before putting the job in the read queue.
	 * @param timeToRun The number of seconds to allow a worker to run this job.
	 * @return The id job.
	 */
	@Override
	public String put(String jobBody, int priority, int deplay, int timeToRun) {
		checkConnection();
		return new BeanstalkCommandPut(socket).perform(jobBody, priority, deplay, timeToRun);
	}

	/**
	 * Inserts a job into the currently tube using default parameters.<br/>
	 * 
	 * @param jobBody The job body
	 * @return The id job.
	 */
	@Override
	public String put(String jobBody) {
		checkConnection();
		return put(jobBody, DEFAULT_PUT_PRIORITY, DEFAULT_PUT_DEPLAY, DEFAULT_PUT_TIME_TO_RUN);
	}

	/**
	 * When consumer (worker) wants to consume jobs from queue. The current thread will be blocked while not receive a job.
	 * @return Reference to Job
	 */
	@Override
	public BeanstalkJob reserve() {
		checkConnection();
		return new BeanstalkCommandReserve(socket).reserve();
	}
	
	/**
	 * When consumer (worker) wants to consume jobs from queue. But this command block the receive during an specific time
	 * 
	 * @param timeout The timeout in seconds to wait for a Job. After timeout done, if no job received return null.
	 * @return Reference to Job or null case timeout occurs.
	 */
	@Override
	public BeanstalkJob reserve(int timeout) {
		checkConnection();
		return new BeanstalkCommandReserve(socket).reserve(timeout);
	}
	
	/**
	 * This command indicates to server that worker has interest to receive jobs from a tube (queue)
	 * @param tube Tube that work has interest
	 * @return The number of tubes currently in the watch list
	 */
	@Override
	public int watch(String tube) {
		checkConnection();
		return new BeanstalkCommandWatch(socket).perform(tube);
	}
	
	/**
	 * This command indicates to server that consumer don't has interest to receive jobs from tube (queue)
	 * @param tube Tube that work don't has interest
	 */
	@Override
	public int ignore(String tube) {
		checkConnection();
		return new BeanstalkCommandIgnore(socket).perform(tube);
	}
	
	/**
	 * Returns the tubes currently being watched by worker.
	 * @return The tubes currently being watched by worker.
	 */
	@Override
	public List<String> watching() {
		checkConnection();
		return new BeanstalkCommandListTubeWatched(socket).perform();
	}
	
}
