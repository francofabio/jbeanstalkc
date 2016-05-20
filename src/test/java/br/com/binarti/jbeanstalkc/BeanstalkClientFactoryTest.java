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

	private static final String HOST = "localhost";
	private static final int PORT = 11300;
	
	private static BeanstalkClientFactory factory;
	
	@BeforeClass
	public static void init() {
		factory = new BeanstalkClientFactory(HOST, PORT);
	}
	
	@Test
	public void shoudCreateBeanstalkClient() {
		BeanstalkClient beanstalkClient = factory.createClient();
		assertTrue(beanstalkClient.isConnected());
	}
	
	@Test
	public void shouldCreateProducer() {
		BeanstalkProducer producer = factory.createProducer();
		assertEquals("default", producer.using());
	}
	
	@Test
	public void shouldCreateProducerUsingTube() {
		BeanstalkProducer producer = factory.createProducer("jbeanstalk");
		assertEquals("jbeanstalk", producer.using());
	}
	
	@Test
	public void shouldCreateConsumer() {
		BeanstalkConsumer consumer = factory.createConsumer();
		List<String> watching = consumer.watching();
		assertEquals(1, watching.size());
		assertTrue(watching.contains("default"));
	}
	
	@Test
	public void shouldCreateConsumerUsingTubeToWatch() {
		BeanstalkConsumer consumer = factory.createConsumer("jbeanstalk");
		List<String> watching = consumer.watching();
		assertEquals(1, watching.size());
		assertTrue(watching.contains("jbeanstalk"));
	}
	
}
