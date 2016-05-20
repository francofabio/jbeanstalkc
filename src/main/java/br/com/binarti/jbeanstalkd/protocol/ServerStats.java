package br.com.binarti.jbeanstalkd.protocol;

/**
 * An object that represents a Dictionary returned by <tt>stats</tt> command.
 * 
 * @author francofabio
 *
 */
public class ServerStats extends BeanstalkStats {
	
	public ServerStats(String yaml) {
		super(yaml);
	}

	/**
	 * "current-jobs-urgent" is the number of ready jobs with priority < 1024.
	 */
	public long getCurrentJobsUrgent() {
		return getAs("current-jobs-urgent", Long.class);
	}

	/**
	 * "current-jobs-ready" is the number of jobs in the ready queue.
	 */
	public long getCurrentJobsReady() {
		return getAs("current-jobs-ready", Long.class);
	}

	/**
	 * "current-jobs-reserved" is the number of jobs reserved by all clients.
	 */
	public long getCurrentJobsReserved() {
		return getAs("current-jobs-reserved", Long.class);
	}

	/**
	 * "current-jobs-delayed" is the number of delayed jobs.
	 */
	public long getCurrentJobsDelayed() {
		return getAs("current-jobs-delayed", Long.class);
	}

	/**
	 * "current-jobs-buried" is the number of buried jobs.
	 */
	public long getCurrentJobsBuried() {
		return getAs("current-jobs-buried", Long.class);
	}

	/**
	 * "cmd-put" is the cumulative number of put commands.
	 */
	public long getCmdPut() {
		return getAs("cmd-put", Long.class);
	}

	/**
	 * "cmd-peek" is the cumulative number of peek commands.
	 */
	public long getCmdPeek() {
		return getAs("cmd-peek", Long.class);
	}

	/**
	 * "cmd-peek-ready" is the cumulative number of peek-ready commands.
	 */
	public long getCmdPeekReady() {
		return getAs("cmd-peek-ready", Long.class);
	}

	/**
	 * "cmd-peek-delayed" is the cumulative number of peek-delayed commands.
	 */
	public long getCmdPeekDelayed() {
		return getAs("cmd-peek-delayed", Long.class);
	}

	/**
	 * "cmd-peek-buried" is the cumulative number of peek-buried commands.
	 */
	public long getCmdPeekBuried() {
		return getAs("cmd-peek-buried", Long.class);
	}

	/**
	 * "cmd-reserve" is the cumulative number of reserve commands.
	 */
	public long getCmdReserve() {
		return getAs("cmd-reserve", Long.class);
	}

	/**
	 * "cmd-use" is the cumulative number of use commands.
	 */
	public long getCmdUse() {
		return getAs("cmd-use", Long.class);
	}

	/**
	 * "cmd-watch" is the cumulative number of watch commands.
	 */
	public long getCmdWatch() {
		return getAs("cmd-watch", Long.class);
	}

	/**
	 * "cmd-ignore" is the cumulative number of ignore commands.
	 */
	public long getCmdIgnore() {
		return getAs("cmd-ignore", Long.class);
	}

	/**
	 * "cmd-delete" is the cumulative number of delete commands.
	 */
	public long getCmdDelete() {
		return getAs("cmd-delete", Long.class);
	}

	/**
	 * "cmd-release" is the cumulative number of release commands.
	 */
	public long getCmdRelease() {
		return getAs("cmd-release", Long.class);
	}

	/**
	 * "cmd-bury" is the cumulative number of bury commands.
	 */
	public long getCmdBury() {
		return getAs("cmd-bury", Long.class);
	}

	/**
	 * "cmd-kick" is the cumulative number of kick commands.
	 */
	public long getCmdKick() {
		return getAs("cmd-kick", Long.class);
	}

	/**
	 * "cmd-stats" is the cumulative number of stats commands.
	 */
	public long getCmdStats() {
		return getAs("cmd-stats", Long.class);
	}

	/**
	 * "cmd-stats-job" is the cumulative number of stats-job commands.
	 */
	public long getCmdStatsJob() {
		return getAs("cmd-stats-job", Long.class);
	}

	/**
	 * "cmd-stats-tube" is the cumulative number of stats-tube commands.
	 */
	public long getCmdStatsTube() {
		return getAs("cmd-stats-tube", Long.class);
	}

	/**
	 * "cmd-list-tubes" is the cumulative number of list-tubes commands.
	 */
	public long getCmdListTubes() {
		return getAs("cmd-list-tubes", Long.class);
	}

	/**
	 * "cmd-list-tube-used" is the cumulative number of list-tube-used commands.
	 */
	public long getCmdListTubeUsed() {
		return getAs("cmd-list-tube-used", Long.class);
	}

	/**
	 * "cmd-list-tubes-watched" is the cumulative number of list-tubes-watched commands.
	 */
	public long getCmdListTubesWatched() {
		return getAs("cmd-list-tubes-watched", Long.class);
	}

	/**
	 * "cmd-pause-tube" is the cumulative number of pause-tube commands.
	 */
	public long getCmdPauseTube() {
		return getAs("cmd-pause-tube", Long.class);
	}

	/**
	 * "job-timeouts" is the cumulative count of times a job has timed out.
	 */
	public long getJobTimeouts() {
		return getAs("job-timeouts", Long.class);
	}

	/**
	 * "total-jobs" is the cumulative count of jobs created.
	 */
	public long getTotalJobs() {
		return getAs("total-jobs", Long.class);
	}

	/**
	 * "max-job-size" is the maximum number of bytes in a job.
	 */
	public long getMaxJobSize() {
		return getAs("max-job-size", Long.class);
	}

	/**
	 * "current-tubes" is the number of currently-existing tubes.
	 */
	public long getCurrentTubes() {
		return getAs("current-tubes", Long.class);
	}

	/**
	 * "current-connections" is the number of currently open connections.
	 */
	public long getCurrentConnections() {
		return getAs("current-connections", Long.class);
	}

	/**
	 * "current-producers" is the number of open connections that have each issued at least one put command.
	 */
	public long getCurrentProducers() {
		return getAs("current-producers", Long.class);
	}

	/**
	 * "current-workers" is the number of open connections that have each issued at least one reserve command.
	 */
	public long getCurrentWorkers() {
		return getAs("current-workers", Long.class);
	}

	/**
	 * "current-waiting" is the number of open connections that have issued a reserve command but not yet received a response.
	 */
	public long getCurrentWaiting() {
		return getAs("current-waiting", Long.class);
	}

	/**
	 * "total-connections" is the cumulative count of connections.
	 */
	public long getTotalConnections() {
		return getAs("total-connections", Long.class);
	}

	/**
	 * "pid" is the process id of the server.
	 */
	public long getPid() {
		return getAs("pid", Long.class);
	}

	/**
	 * "version" is the version string of the server
	 */
	public String getVersion() {
		return getAs("version", String.class);
	}

	/**
	 * "rusage-utime" is the cumulative user CPU time of this process in seconds and microseconds.
	 */
	public Double getRusageUtime() {
		return getAs("rusage-utime", Double.class);
	}

	/**
	 * "rusage-stime" is the cumulative system CPU time of this process in seconds and microseconds.
	 */
	public Double getRusageStime() {
		return getAs("rusage-stime", Double.class);
	}

	/**
	 * "uptime" is the number of seconds since this server process started running.
	 */
	public long getUptime() {
		return getAs("uptime", Long.class);
	}

	/**
	 * "binlog-oldest-index" is the index of the oldest binlog file needed to store the current jobs.
	 */
	public long getBinlogOldestIndex() {
		return getAs("binlog-oldest-index", Long.class);
	}

	/**
	 * "binlog-current-index" is the index of the current binlog file being written to. If binlog is not active this value will be 0.
	 */
	public long getBinlogCurrentIndex() {
		return getAs("binlog-current-index", Long.class);
	}

	/**
	 * "binlog-max-size" is the maximum size in bytes a binlog file is allowed to get before a new binlog file is opened.
	 */
	public long getBinlogMaxSize() {
		return getAs("binlog-max-size", Long.class);
	}

	/**
	 * "binlog-records-written" is the cumulative number of records written to the binlog.
	 */
	public long getBinlogRecordsWritten() {
		return getAs("binlog-records-written", Long.class);
	}

	/**
	 * "binlog-records-migrated" is the cumulative number of records written as part of compaction.
	 */
	public long getBinlogRecordsMigrated() {
		return getAs("binlog-records-migrated", Long.class);
	}

	/**
	 * "id" is a random id string for this server process, generated when each beanstalkd process starts.
	 */
	public String getId() {
		return getAs("id", String.class);
	}

	/**
	 * "hostname" the hostname of the machine as determined by uname.
	 */
	public String getHostname() {
		return getAs("hostname", String.class);
	}
	
}
