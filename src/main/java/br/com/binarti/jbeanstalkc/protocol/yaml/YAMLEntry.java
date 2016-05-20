package br.com.binarti.jbeanstalkc.protocol.yaml;

public class YAMLEntry {

	private String key;
	private String value;

	public YAMLEntry(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}

}
