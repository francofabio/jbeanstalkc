package br.com.binarti.jbeanstalkc;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import br.com.binarti.jbeanstalkc.BeanstalkClient;
import br.com.binarti.jbeanstalkc.BeanstalkJob;
import br.com.binarti.jbeanstalkc.protocol.TubeStats;

public class BaseBeanstalkClientTest {

	protected static final String JBEANSTALK_TUBE = "jbeanstalk";
	
	protected static String BEANSTALKD_URL;
	protected static BeanstalkClientFactory factory;
	
	protected BeanstalkClient beanstalkClient;
	
	@BeforeClass
	public static void init() {
		BEANSTALKD_URL = System.getProperty("jbeanstalkc.beanstalkd.url");
		if (BEANSTALKD_URL == null) {
			BEANSTALKD_URL = "beanstalkd://localhost:11300";
		}
		factory = new BeanstalkClientFactory(BEANSTALKD_URL);
	}
	
	@Before
	public void connectBeanstalk() throws IOException {
		beanstalkClient = factory.createClient();
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
