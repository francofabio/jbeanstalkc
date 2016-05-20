package br.com.binarti.jbeanstalkc;

/**
 * Simple factory to create instance of client, producer or consumer.
 * 
 * @author francofabio
 *
 */
public class BeanstalkClientFactory {

	private String host;
	private int port;
	
	/**
	 * Create a new factory
	 * @param host Address to beanstalkd server
	 * @param port Port to beanstalkd server
	 */
	public BeanstalkClientFactory(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	/**
	 * Create new instance of the beanstalk client.
	 * @return New instance of the beanstalk client
	 */
	public BeanstalkClient createClient() {
		BeanstalkClient beanstalkClient = new BeanstalkClient(host, port);
		beanstalkClient.connect();
		return beanstalkClient;
	}
	
	/**
	 * Create new instance of the beanstalk client producer
	 * @return New instance of the beanstalk client producer
	 */
	public BeanstalkProducer createProducer() { 
		return createClient();
	}
	
	/**
	 * Create new instance of the beanstalk client producer
	 * @param tube The tube to use for put job
	 * @return New instance of the beanstalk client producer
	 */
	public BeanstalkProducer createProducer(String tube) { 
		BeanstalkClient beanstalkClient = createClient();
		beanstalkClient.useTube(tube);
		return beanstalkClient;
	}
	
	/**
	 * Create new instance of the beanstalk client consumer.<br>
	 * The default watch tube is "default"
	 * @return New instance of the beanstalk client consumer
	 */
	public BeanstalkConsumer createConsumer() {
		return createClient();
	}
	
	/**
	 * Create new instance of the beanstalk client consumer
	 * @param tube The tube to watch for jobs
	 * @return New instance of the beanstalk client consumer
	 */
	public BeanstalkConsumer createConsumer(String tube) {
		BeanstalkClient beanstalkClient = createClient();
		beanstalkClient.watch(tube);
		beanstalkClient.ignore(BeanstalkClient.DEFAULT_BEANSTALK_TUBE);
		return beanstalkClient;
	}
	
}
