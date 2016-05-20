package br.com.binarti.jbeanstalkd.protocol;

public class JobStats extends BeanstalkStats {

	public JobStats(String yaml) {
		super(yaml);
	}

	/**
	 * "id" is the job id
	 */
	public String getId() {
		return get("id");
	}

	/**
	 * "tube" is the name of the tube that contains this job
	 */
	public String getTube() {
		return get("tube");
	}

	/**
	 * "state" is "ready" or "delayed" or "reserved" or "buried"
	 */
	public String getState() {
		return get("state");
	}

	/**
	 * "pri" is the priority value set by the put, release, or bury commands.
	 */
	public int getPriority() {
		return getAs("pri", Integer.class);
	}

	/**
	 * "age" is the time in seconds since the put command that created this job.
	 */
	public double getAge() {
		return getAs("age", Double.class);
	}

	/**
	 * "delay" is the integer number of seconds to wait before putting this job
	 * in the ready queue.
	 */
	public long getDelay() {
		return getAs("delay", Long.class);
	}

	/**
	 * "ttr" -- time to run -- is the integer number of seconds a worker is
	 * allowed to run this job.
	 */
	public long getTimeToRun() {
		return getAs("ttr", Long.class);
	}

	/**
	 * "time-left" is the number of seconds left until the server puts this job
	 * into the ready queue. This number is only meaningful if the job is
	 * reserved or delayed. If the job is reserved and this amount of time
	 * elapses before its state changes, it is considered to have timed out.
	 */
	public long getTimeLeft() {
		return getAs("time-left", Long.class);
	}

	/**
	 * "file" is the number of the earliest binlog file containing this job. If
	 * -b wasn't used, this will be 0.
	 */
	public int getFile() {
		return getAs("file", Integer.class);
	}

	/**
	 * "reserves" is the number of times this job has been reserved.
	 */
	public long getReserves() {
		return getAs("reserves", Long.class);
	}

	/**
	 * "timeouts" is the number of times this job has timed out during a
	 * reservation.
	 */
	public long getTimeouts() {
		return getAs("timeouts", Long.class);
	}

	/**
	 * "releases" is the number of times a client has released this job from a
	 * reservation.
	 */
	public long getReleases() {
		return getAs("releases", Long.class);
	}

	/**
	 * "buries" is the number of times this job has been buried.
	 */
	public long getBuries() {
		return getAs("buries", Long.class);
	}

	/**
	 * "kicks" is the number of times this job has been kicked.
	 */
	public long getKicks() {
		return getAs("kicks", Long.class);
	}

}
