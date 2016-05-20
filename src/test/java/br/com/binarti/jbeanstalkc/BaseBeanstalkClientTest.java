package br.com.binarti.jbeanstalkc;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;

import br.com.binarti.jbeanstalkc.BeanstalkClient;
import br.com.binarti.jbeanstalkc.BeanstalkJob;
import br.com.binarti.jbeanstalkc.protocol.TubeStats;

public class BaseBeanstalkClientTest {

	protected static final String JBEANSTALK_TUBE = "jbeanstalk";
	protected static final String HOST = "localhost";
	protected static final int PORT = 11300;
	
	protected BeanstalkClient beanstalkClient;
	
	@Before
	public void connectBeanstalk() throws IOException {
		beanstalkClient = new BeanstalkClient(HOST, PORT);
		beanstalkClient.connect();
	}
	
	@After
	public void closeBeanstalkConnection() throws IOException {
		beanstalkClient.close();
	}
	
	protected void cleanTube(String tube) {
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		beanstalkClient.watch(JBEANSTALK_TUBE);
		TubeStats tubeStats = beanstalkClient.tubeStats(tube);
		if (tubeStats.getCurrentJobsReady() > 0) {
			do {
				BeanstalkJob job = beanstalkClient.reserve(1);
				job.delete();
				tubeStats = beanstalkClient.tubeStats(tube);
			} while (tubeStats.getCurrentJobsReady() > 0);
		}
		tubeStats = beanstalkClient.tubeStats(tube);
		if (tubeStats.getCurrentJobsDelayed() > 0) {
			BeanstalkJob job = beanstalkClient.peekDelayed();
			while (job != null) {
				job.delete();
				job = beanstalkClient.peekDelayed();
			}
		}
		tubeStats = beanstalkClient.tubeStats(tube);
		if (tubeStats.getCurrentJobsBuried() > 0) {
			BeanstalkJob job = beanstalkClient.peekBuried();
			while (job != null) {
				job.delete();
				job = beanstalkClient.peekBuried();
			}
		}
	}
	
	protected void cleanDefaultTube() {
		cleanTube(JBEANSTALK_TUBE);
	}
	
}
