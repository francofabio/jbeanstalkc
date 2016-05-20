package br.com.binarti.jbeanstalkd.protocol;

import static br.com.binarti.jbeanstalkd.BeanstalkClientHelper.simpleConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanstalkPackage {

	protected List<String> params;
	protected String data;
	protected boolean chunk;

	protected BeanstalkPackage(List<String> params, String data) {
		this.params = new ArrayList<>(params);
		this.data = data;
		this.chunk = (data != null && !data.isEmpty());
	}
	
	public List<String> getParams() {
		return Collections.unmodifiableList(params);
	}
	
	public String getData() {
		return data;
	}
	
	void setData(String data) {
		this.data = data;
	}
	
	public String getParam(int index) {
		if (index >= params.size()) {
			return null;
		}
		return params.get(index);
	}
	
	public <T> T getParamAs(int index, Class<T> targetType) {
		String val = getParam(index);
		return simpleConverter(val, targetType);
	}
	
	public boolean isChunk() {
		return chunk;
	}
	
	void setChunk(boolean val) {
		this.chunk = val;
	}
	
}
