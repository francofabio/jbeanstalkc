package br.com.binarti.jbeanstalkc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import br.com.binarti.jbeanstalkc.protocol.ServerStats;
import br.com.binarti.jbeanstalkc.protocol.TubeStats;

public class BeanstalkClientTest extends BaseBeanstalkClientTest {

	@Test(expected=BeanstalkException.class)
	public void shouldThrowExceptionIfNoConnectionInitialized() throws Exception {
		try (BeanstalkClient client = factory.createClient()) {
			client.close();
			client.serverStats();
		}
	}
	
	@Test(expected=BeanstalkException.class)
	public void shouldThrowExceptionIfConnectionIsClosed() throws Exception {
		beanstalkClient.close();
		beanstalkClient.serverStats();
	}
	
	@Test
	public void shoudTestConnection() throws IOException {
		try (BeanstalkClient beanstalkClient = factory.createClient()) {
			assertTrue(beanstalkClient.isConnected());
		}
	}
	
	@Test
	public void shouldServerStatsCommand() throws IOException {
		ServerStats serverStats = beanstalkClient.serverStats();
		assertNotNull(serverStats);
		assertTrue(serverStats.getCurrentJobsUrgent() >= 0);
		assertTrue(serverStats.getCurrentJobsReady() >= 0);
		assertTrue(serverStats.getCurrentJobsReserved() >= 0);
		assertTrue(serverStats.getCurrentJobsDelayed() >= 0);
		assertTrue(serverStats.getCurrentJobsBuried() >= 0);
		assertTrue(serverStats.getCmdPut() >= 0);
		assertTrue(serverStats.getCmdPeek() >= 0);
		assertTrue(serverStats.getCmdPeekReady() >= 0);
		assertTrue(serverStats.getCmdPeekDelayed() >= 0);
		assertTrue(serverStats.getCmdPeekBuried() >= 0);
		assertTrue(serverStats.getCmdReserve() >= 0);
		assertTrue(serverStats.getCmdUse() >= 0);
		assertTrue(serverStats.getCmdWatch() >= 0);
		assertTrue(serverStats.getCmdIgnore() >= 0);
		assertTrue(serverStats.getCmdDelete() >= 0);
		assertTrue(serverStats.getCmdRelease() >= 0);
		assertTrue(serverStats.getCmdBury() >= 0);
		assertTrue(serverStats.getCmdKick() >= 0);
		assertTrue(serverStats.getCmdStats() >= 0);
		assertTrue(serverStats.getCmdStatsJob() >= 0);
		assertTrue(serverStats.getCmdStatsTube() >= 0);
		assertTrue(serverStats.getCmdListTubes() >= 0);
		assertTrue(serverStats.getCmdListTubeUsed() >= 0);
		assertTrue(serverStats.getCmdListTubesWatched() >= 0);
		assertTrue(serverStats.getCmdPauseTube() >= 0);
		assertTrue(serverStats.getJobTimeouts() >= 0);
		assertTrue(serverStats.getTotalJobs() >= 0);
		assertTrue(serverStats.getMaxJobSize() >= 0);
		assertTrue(serverStats.getCurrentTubes() >= 0);
		assertTrue(serverStats.getCurrentConnections() >= 0);
		assertTrue(serverStats.getCurrentProducers() >= 0);
		assertTrue(serverStats.getCurrentWorkers() >= 0);
		assertTrue(serverStats.getCurrentWaiting() >= 0);
		assertTrue(serverStats.getTotalConnections() >= 0);
		assertTrue(serverStats.getPid() >= 0);
		assertNotNull(serverStats.getVersion());
		assertTrue(serverStats.getRusageUtime() >= 0d);
		assertTrue(serverStats.getRusageStime() >= 0d);
		assertTrue(serverStats.getUptime() >= 0);
		assertTrue(serverStats.getBinlogOldestIndex() >= 0);
		assertTrue(serverStats.getBinlogCurrentIndex() >= 0);
		assertTrue(serverStats.getBinlogMaxSize() >= 0);
		assertTrue(serverStats.getBinlogRecordsWritten() >= 0);
		assertTrue(serverStats.getBinlogRecordsMigrated() >= 0);
		assertNotNull(serverStats.getId());
		assertNotNull(serverStats.getHostname());
	}
	
	@Test
	public void shouldRetrieveTubeStatusFromDefaultTubeIfNoTubeIsCurrently() {
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertNotNull(tubeStats);
		assertEquals("default", tubeStats.getName());
		assertTrue(tubeStats.getCurrentJobsUrgent() >= 0);
		assertTrue(tubeStats.getCurrentJobsReady() >= 0);
		assertTrue(tubeStats.getCurrentJobsReserved() >= 0);
		assertTrue(tubeStats.getCurrentJobsDelayed() >= 0);
		assertTrue(tubeStats.getCurrentJobsBuried() >= 0);
		assertTrue(tubeStats.getTotalJobs() >= 0);
		assertTrue(tubeStats.getCurrentUsing() >= 0);
		assertTrue(tubeStats.getCurrentWaiting() >= 0);
		assertTrue(tubeStats.getCurrentWatching() >= 0);
		assertTrue(tubeStats.getPausedTime() >= 0);
		assertTrue(tubeStats.getCmdDelete() >= 0);
		assertTrue(tubeStats.getTotalPauseTubeCommandExecuted() >= 0);
		assertTrue(tubeStats.getPauseTimeLeft() >= 0);
	}
	
	@Test
	public void shouldRetrieveTubeStatsFromCurrentTube() throws IOException {
		beanstalkClient.useTube(JBEANSTALK_TUBE);
		TubeStats tubeStats = beanstalkClient.currentTubeStats();
		assertNotNull(tubeStats);
		assertEquals(JBEANSTALK_TUBE, tubeStats.getName());
		assertTrue(tubeStats.getCurrentJobsUrgent() >= 0);
		assertTrue(tubeStats.getCurrentJobsReady() >= 0);
		assertTrue(tubeStats.getCurrentJobsReserved() >= 0);
		assertTrue(tubeStats.getCurrentJobsDelayed() >= 0);
		assertTrue(tubeStats.getCurrentJobsBuried() >= 0);
		assertTrue(tubeStats.getTotalJobs() >= 0);
		assertTrue(tubeStats.getCurrentUsing() >= 0);
		assertTrue(tubeStats.getCurrentWaiting() >= 0);
		assertTrue(tubeStats.getCurrentWatching() >= 0);
		assertTrue(tubeStats.getPausedTime() >= 0);
		assertTrue(tubeStats.getCmdDelete() >= 0);
		assertTrue(tubeStats.getTotalPauseTubeCommandExecuted() >= 0);
		assertTrue(tubeStats.getPauseTimeLeft() >= 0);
	}
	
	@Test
	public void shouldRetrieveTubeStatsFromOtherTube() throws IOException {
		TubeStats tubeStats = beanstalkClient.tubeStats("default");
		assertNotNull(tubeStats);
		assertEquals("default", tubeStats.getName());
		assertTrue(tubeStats.getCurrentJobsUrgent() >= 0);
		assertTrue(tubeStats.getCurrentJobsReady() >= 0);
		assertTrue(tubeStats.getCurrentJobsReserved() >= 0);
		assertTrue(tubeStats.getCurrentJobsDelayed() >= 0);
		assertTrue(tubeStats.getCurrentJobsBuried() >= 0);
		assertTrue(tubeStats.getTotalJobs() >= 0);
		assertTrue(tubeStats.getCurrentUsing() >= 0);
		assertTrue(tubeStats.getCurrentWaiting() >= 0);
		assertTrue(tubeStats.getCurrentWatching() >= 0);
		assertTrue(tubeStats.getPausedTime() >= 0);
		assertTrue(tubeStats.getCmdDelete() >= 0);
		assertTrue(tubeStats.getTotalPauseTubeCommandExecuted() >= 0);
		assertTrue(tubeStats.getPauseTimeLeft() >= 0);
	}
	
	@Test
	public void shouldReturnNullIfTubeNotExistsWhenRetrieveTubeStats() throws IOException {
		TubeStats tubeStats = beanstalkClient.tubeStats("mytube");
		assertNull(tubeStats);
	}
	
	@Test
	public void shouldListExistingTubes() {
		List<String> tubes = beanstalkClient.listTubes();
		assertTrue(tubes.size() >= 1);
		assertTrue(tubes.contains("default"));
	}
	
}
