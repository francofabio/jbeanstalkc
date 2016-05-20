package br.com.binarti.jbeanstalkc;

import java.util.List;

/**
 * The interface to available operations for consumers to use beanstalkd service
 * 
 * @author francofabio
 *
 */
public interface BeanstalkConsumer extends BeanstalkClientConnect {

	/**
	 * When consumer (worker) wants to consume jobs from queue. Block current thread until a job is available.
	 * @return Reference to Job
	 */
	BeanstalkJob reserve();
	
	/**
	 * When consumer (worker) wants to consume jobs from queue. But this command block the receive during an specific time
	 * 
	 * @param timeout The timeout in seconds to wait for a Job. After timeout done, if no job received return null.
	 * @return Reference to Job or null case timeout occurs.
	 */
	BeanstalkJob reserve(int timeout);
	
	/**
	 * This command indicates to server that worker has interest to receive jobs from a tube (queue)
	 * @param tube Tube that work has interest
	 * @return The number of tubes currently in the watch list
	 */
	int watch(String tube);
	
	/**
	 * This command indicates to server that consumer don't has interest to receive jobs from tube (queue)
	 * @param tube Tube that work don't has interest
	 * @return The number of tubes currently in the watch list
	 */
	int ignore(String tube);
	
	/**
	 * Returns the tubes currently being watched by worker.
	 * @return The tubes currently being watched by worker.
	 */
	List<String> watching();
	
}
