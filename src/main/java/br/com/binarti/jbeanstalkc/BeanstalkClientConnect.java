package br.com.binarti.jbeanstalkc;

public interface BeanstalkClientConnect extends AutoCloseable {

	/**
	 * Connect to beanstalkd service
	 */
	void connect();
	
	/**
	 * Close connection to beanstalkd service
	 */
	void close();
	
	/**
	 * Check if this client is connected to beanstalkd service
	 * @return <code>true</code> if connection is already was established, otherwise return <code>false</code>
	 */
	boolean isConnected();
}
