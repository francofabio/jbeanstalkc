package br.com.binarti.jbeanstalkc;

public interface BeanstalkProducer {

	/**
	 * Change the currently tube to a give tube. The "use" command is for producers. <br>
	 * Subsequent put commands will put jobs into the tube specified by this command. <br>
	 * If no use command has been issued, jobs will be put into the tube named "default".
	 * 
	 * @param tube Tube name
	 */
	void useTube(String tube);
	
	/**
	 * Returns the tube currently being used by the client. Invoke list-tube-used command in server.
	 * @return The tube currently being used by the client.
	 */
	String using();
	
	/**
	 * Inserts a job into the currently tube.<br/>
	 * 
	 * @param jobBody The job body
	 * @param priority The job priority. Jobs with smaller priority values will be scheduled before jobs with larger priorities. The most urgent priority is 0; the least urgent priority is 4,294,967,295.
	 * @param deplay The number of seconds to wait before putting the job in the read queue.
	 * @param timeToRun The number of seconds to allow a worker to run this job.
	 * @return The id job.
	 */
	String put(String jobBody, int priority, int deplay, int timeToRun);
	
	/**
	 * Inserts a job into the currently tube using default parameters.<br/>
	 * 
	 * @param jobBody The job body
	 * @return The id job.
	 */
	String put(String jobBody);
	
}
