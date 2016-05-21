package br.com.binarti.jbeanstalkc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.binarti.jbeanstalkc.BeanstalkClient;
import br.com.binarti.jbeanstalkc.BeanstalkClientFactory;
import br.com.binarti.jbeanstalkc.BeanstalkConsumer;
import br.com.binarti.jbeanstalkc.BeanstalkProducer;

public class BeanstalkClientFactoryTest {
	
	private static String BEANSTALKD_URL;
	private static BeanstalkClientFactory factory;
	
	@BeforeClass
	public static void init() {
		BEANSTALKD_URL = System.getProperty("jbeanstalkc.beanstalkd.url");
		if (BEANSTALKD_URL == null) {
			BEANSTALKD_URL = "beanstalkd://localhost:11300";
		}
		factory = new BeanstalkClientFactory(BEANSTALKD_URL);
	}
	
	@Test
	public void shoudCreateBeanstalkClient() {
		try (BeanstalkClient beanstalkClient = factory.createClient()) {
			assertTrue(beanstalkClient.isConnected());
		}
	}
	
	@Test
	public void shouldCreateProducer() {
		try (BeanstalkProducer producer = factory.createProducer()) {
			assertEquals("default", producer.using());
		}
	}
	
	@Test
	public void shouldCreateProducerUsingTube() {
		try (BeanstalkProducer producer = factory.createProducer("jbeanstalk")) {
			assertEquals("jbeanstalk", producer.using());
		}
	}
	
	@Test
	public void shouldCreateConsumer() {
		try (BeanstalkConsumer consumer = factory.createConsumer()) {
			List<String> watching = consumer.watching();
			assertEquals(1, watching.size());
			assertTrue(watching.contains("default"));
		}
	}
	
	@Test
	public void shouldCreateConsumerUsingTubeToWatch() {
		try (BeanstalkConsumer consumer = factory.createConsumer("jbeanstalk")) {
			List<String> watching = consumer.watching();
			assertEquals(1, watching.size());
			assertTrue(watching.contains("jbeanstalk"));
		}
	}
	
}
