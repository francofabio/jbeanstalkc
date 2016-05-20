package br.com.binarti.jbeanstalkc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.com.binarti.jbeanstalkc.BeanstalkClient;
import br.com.binarti.jbeanstalkc.BeanstalkJob;
import br.com.binarti.jbeanstalkc.protocol.JobStats;
import br.com.binarti.jbeanstalkc.protocol.TubeStats;

public class BeanstalkConsumerTest extends BaseBeanstalkClientTest {

	@Test
	public void shouldAddTubeInWatchList() {
		assertEquals(2, beanstalkClient.watch(JBEANSTALK_TUBE));
		List<String> watchingList = beanstalkClient.watching();
		assertNotNull(watchingList);
		assertEquals(2, watchingList.size());
		assertTrue(watchingList.contains(JBEANSTALK_TUBE));
	}
	
	@Test
	public void shouldIgnoreTubeFromWatchList() {
		beanstalkClient.watch(JBEANSTALK_TUBE);
		assertEquals(1, beanstalkClient.ignore("default"));
		List<String> watchingList = beanstalkClient.watching();
		assertEquals(1, watchingList.size());
	}
	
	@Test
	public void shouldConsumeAndDeleteJob() {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		beanstalkClient.put("test");
		
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsReady());
		
		beanstalkClient.watch(JBEANSTALK_TUBE);
		BeanstalkJob job = beanstalkClient.reserve();
		assertEquals("test", job.getBody());
		job.delete();
		
		tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(0, tubeStats.getCurrentJobsReady());
	}
	
	@Test
	public void shouldConsumeAndReleaseJob() {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		beanstalkClient.put("test");
		
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsReady());
		
		beanstalkClient.watch(JBEANSTALK_TUBE);
		BeanstalkJob job = beanstalkClient.reserve();
		assertEquals("test", job.getBody());
		job.release();
		
		tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsReady());
	}
	
	@Test
	public void shouldConsumeAndReleaseJobWithDelay() throws Exception {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		beanstalkClient.put("test");
		
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsReady());
		
		beanstalkClient.watch(JBEANSTALK_TUBE);
		BeanstalkJob job = beanstalkClient.reserve();
		assertEquals("test", job.getBody());
		job.release(BeanstalkClient.DEFAULT_PUT_PRIORITY, 2);
		
		Thread.sleep(1000 * 2);
		
		tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsReady());
	}
	
	@Test
	public void shouldConsumeAndBuryJob() {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		beanstalkClient.put("test");
		
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsReady());
		
		beanstalkClient.watch(JBEANSTALK_TUBE);
		BeanstalkJob job = beanstalkClient.reserve();
		assertEquals("test", job.getBody());
		job.bury();
		
		tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsBuried());
	}
	
	@Test
	public void shouldConsumeAndTouchJob() throws Exception {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		beanstalkClient.put("test");
		
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsReady());
		
		beanstalkClient.watch(JBEANSTALK_TUBE);
		BeanstalkJob job = beanstalkClient.reserve();
		assertEquals("test", job.getBody());
		job.touch();
		
		tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsReserved());
		assertEquals(0, tubeStats.getCurrentJobsReady());
		job.delete();
	}
	
	@Test
	public void shouldGetJobStats() {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		beanstalkClient.put("test");
		
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsReady());
		
		beanstalkClient.watch(JBEANSTALK_TUBE);
		BeanstalkJob job = beanstalkClient.reserve();
		assertEquals("test", job.getBody());
		
		JobStats jobStats = job.stats();
		assertEquals(job.getId(), jobStats.getId());
		assertEquals("reserved", jobStats.getState());
		assertEquals(JBEANSTALK_TUBE, jobStats.getTube());
		assertTrue(jobStats.getAge() >= 0);
		assertTrue(jobStats.getPriority() >= BeanstalkClient.DEFAULT_PUT_PRIORITY);
		assertTrue(jobStats.getDelay() >= 0);
		assertTrue(jobStats.getTimeToRun() >= BeanstalkClient.DEFAULT_PUT_TIME_TO_RUN);
		assertTrue(jobStats.getTimeLeft() >= 0);
		assertTrue(jobStats.getFile() >= 0);
		assertTrue(jobStats.getReserves() >= 0);
		assertTrue(jobStats.getTimeouts() >= 0);
		assertTrue(jobStats.getReleases() >= 0);
		assertTrue(jobStats.getBuries() >= 0);
		assertTrue(jobStats.getKicks() >= 0);
	}
	
	@Test
	public void shouldReturnNullIfStatsJobNotFound() {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		beanstalkClient.put("test");
		
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertEquals(1, tubeStats.getCurrentJobsReady());
		
		beanstalkClient.watch(JBEANSTALK_TUBE);
		BeanstalkJob job = beanstalkClient.reserve();
		assertEquals("test", job.getBody());
		
		JobStats jobStats = job.stats();
		assertEquals(job.getId(), jobStats.getId());
		assertEquals("reserved", jobStats.getState());
		assertEquals(JBEANSTALK_TUBE, jobStats.getTube());
		
		job.delete();
		
		jobStats = job.stats();
		assertNull(jobStats);
	}
	
	@Test
	public void shouldPeekJob() {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		String jobId = beanstalkClient.put("test");
		BeanstalkJob job = beanstalkClient.peekJob(jobId);
		assertNotNull(job);
		assertEquals(jobId, job.getId());
	}
	
	@Test
	public void shouldReturnNullIfNoJobPeekJob() {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		BeanstalkJob job = beanstalkClient.peekJob("12345678");
		assertNull(job);
	}
	
	@Test
	public void shouldPeekReadyJob() {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		String jobId = beanstalkClient.put("test");
		BeanstalkJob job = beanstalkClient.peekReady();
		assertNotNull(job);
		assertEquals(jobId, job.getId());
	}
	
	@Test
	public void shouldPeekDelayedJob() {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		String jobId = beanstalkClient.put("test", BeanstalkClient.DEFAULT_PUT_PRIORITY, 10, BeanstalkClient.DEFAULT_PUT_TIME_TO_RUN);
		BeanstalkJob job = beanstalkClient.peekDelayed();
		assertNotNull(job);
		assertEquals(jobId, job.getId());
		JobStats jobStats = job.stats();
		assertEquals("delayed", jobStats.getState());
	}
	
	@Test
	public void shouldPeekBuriedJob() {
		cleanDefaultTube();
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		String jobId = beanstalkClient.put("test");
		
		BeanstalkJob job = beanstalkClient.reserve();
		job.bury();
		
		job = beanstalkClient.peekBuried();
		assertNotNull(job);
		assertEquals(jobId, job.getId());
		JobStats jobStats = job.stats();
		assertEquals("buried", jobStats.getState());
	}
	
}
