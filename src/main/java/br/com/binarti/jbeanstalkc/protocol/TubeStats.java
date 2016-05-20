package br.com.binarti.jbeanstalkc.protocol;

/**
 * An object that represents a Dictionary returned by <tt>stats-tube</tt>
 * command.
 * 
 * @author francofabio
 *
 */
public class TubeStats extends BeanstalkStats {

	public TubeStats(String yaml) {
		super(yaml);
	}

	/**
	 * "name" is the tube's name.
	 */
	public String getName() {
		return getAs("name", String.class);
	}

	/**
	 * "current-jobs-urgent" is the number of ready jobs with priority < 1024 in this tube.
	 */
	public long getCurrentJobsUrgent() {
		return getAs("current-jobs-urgent", Long.class);
	}

	/**
	 * "current-jobs-ready" is the number of jobs in the ready queue in this
	 * tube.
	 */
	public long getCurrentJobsReady() {
		return getAs("current-jobs-ready", Long.class);
	}

	/**
	 * "current-jobs-reserved" is the number of jobs reserved by all clients in
	 * this tube.
	 */
	public long getCurrentJobsReserved() {
		return getAs("current-jobs-reserved", Long.class);
	}

	/**
	 * "current-jobs-delayed" is the number of delayed jobs in this tube.
	 */
	public long getCurrentJobsDelayed() {
		return getAs("current-jobs-delayed", Long.class);
	}

	/**
	 * "current-jobs-buried" is the number of buried jobs in this tube.
	 */
	public long getCurrentJobsBuried() {
		return getAs("current-jobs-buried", Long.class);
	}

	/**
	 * "total-jobs" is the cumulative count of jobs created in this tube in the current beanstalkd process.
	 */
	public long getTotalJobs() {
		return getAs("total-jobs", Long.class);
	}

	/**
	 * "current-using" is the number of open connections that are currently
	 * using this tube.
	 */
	public long getCurrentUsing() {
		return getAs("current-using", Long.class);
	}

	/**
	 * "current-waiting" is the number of open connections that have issued a 
	 * reserve command while watching this tube but not yet received a response.
	 */
	public long getCurrentWaiting() {
		return getAs("current-waiting", Long.class);
	}

	/**
	 * "current-watching" is the number of open connections that are currently watching this tube.
	 */
	public long getCurrentWatching() {
		return getAs("current-watching", Long.class);
	}

	/**
	 * "pause" is the number of seconds the tube has been paused for.
	 */
	public long getPausedTime() {
		return getAs("pause", Long.class);
	}

	/**
	 * "cmd-delete" is the cumulative number of delete commands for this tube
	 */
	public long getCmdDelete() {
		return getAs("cmd-delete", Long.class);
	}

	/**
	 * "cmd-pause-tube" is the cumulative number of pause-tube commands for this tube.
	 */
	public long getTotalPauseTubeCommandExecuted() {
		return getAs("cmd-pause-tube", Long.class);
	}

	/**
	 * "pause-time-left" is the number of seconds until the tube is un-paused.
	 */
	public long getPauseTimeLeft() {
		return getAs("pause-time-left", Long.class);
	}

}
