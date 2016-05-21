package br.com.binarti.jbeanstalkc;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BeanstalkdURL {

	private static final String BEANSTALK_URL_SCHEMA = "beanstalkd";
	private static final Pattern BEANSTALK_URL_PATTERN = Pattern.compile("^(.*)://(.*):([0-9]{1,})[?]{0,1}(.*)$");
	
	private String schema;
	private String host;
	private int port;
	private Map<String, String> params;

	public BeanstalkdURL(String url) {
		parseURL(url);
	}

	private void parseURL(String url) {
		Matcher matcher = BEANSTALK_URL_PATTERN.matcher(url);
		if (!matcher.find() || matcher.groupCount() != 4) {
			throw new IllegalArgumentException("Invalid URL " + url);
		}
		this.schema = matcher.group(1);
		if (!schema.equals(BEANSTALK_URL_SCHEMA)) {
			throw new IllegalArgumentException("Invalid schema " + schema);
		}
		this.host = matcher.group(2).trim();
		if (host.isEmpty()) {
			throw new IllegalArgumentException("Host is required");
		}
		this.port = Integer.parseInt(matcher.group(3).trim());
		this.params = new HashMap<>();
		String params = matcher.group(4).trim();
		if (!params.isEmpty()) {
			String[] paramsPairs = params.split("&");
			for (String paramPair : paramsPairs) {
				String[] keyValue = paramPair.split("=");
				if (keyValue.length != 2) {
					throw new IllegalArgumentException("Invalid URL " + url);
				}
				String key = keyValue[0];
				String value = keyValue[1];
				this.params.put(key, value);
			}
		}
	}

	public String getSchema() {
		return schema;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public Map<String, String> getParams() {
		return params;
	}
	
}
