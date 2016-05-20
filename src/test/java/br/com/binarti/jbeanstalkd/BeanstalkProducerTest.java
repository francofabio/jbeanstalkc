package br.com.binarti.jbeanstalkd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import br.com.binarti.jbeanstalkd.protocol.TubeStats;

public class BeanstalkProducerTest extends BaseBeanstalkClientTest {

	@Test
	public void shouldChangeCurrentUsedTube() throws IOException {
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		assertEquals(beanstalkClient.using(), JBEANSTALK_TUBE);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldNotChangeCurrentTubeForANullTube() {
		beanstalkClient.useTube(null);
	}
	
	@Test
	public void shouldDetermineWhichTubeIsCurrentlyInUse() {
		assertEquals("default", beanstalkClient.using());
		beanstalkClient.useTube("mytube");
		assertEquals("mytube", beanstalkClient.using());
	}
	
	@Test
	public void shouldPutJob() {
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		String id = beanstalkClient.put("teste");
		assertNotNull(id);
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertNotNull(tubeStats);
		assertTrue(tubeStats.getCurrentJobsReady() >= 1L);
	}
	
	@Test
	public void shouldPutJobWithParameters() {
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		String id = beanstalkClient.put("teste", 2500, 0, 120);
		assertNotNull(id);
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertNotNull(tubeStats);
		assertTrue(tubeStats.getCurrentJobsReady() >= 1L);
	}
	
}
