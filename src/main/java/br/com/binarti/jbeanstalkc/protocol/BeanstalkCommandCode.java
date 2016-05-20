package br.com.binarti.jbeanstalkc.protocol;

public enum BeanstalkCommandCode {

	USE("use"), PUT("put"), LIST_TUBE_USED("list-tube-used"), STATS_TUBE("stats-tube"), 
	STATS("stats"), RESERVE("reserve"), RESERVE_WITH_TIMEOUT("reserve-with-timeout"),
	WATCH("watch"), IGNORE("ignore"), LIST_TUBES("list-tubes"), PEEK("peek"), PEEK_READY("peek-ready"),
	PEEK_DELAYED("peek-delayed"), PEEK_BURIED("peek-buried"), LIST_TUBES_WATCHED("list-tubes-watched"),
	DELETE("delete"), RELEASE("release"), BURY("bury"), TOUCH("touch"), STATS_JOB("stats-job");

	private final String code;

	private BeanstalkCommandCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
