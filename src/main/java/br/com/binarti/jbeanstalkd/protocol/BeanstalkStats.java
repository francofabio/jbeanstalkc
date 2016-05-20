package br.com.binarti.jbeanstalkd.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.binarti.jbeanstalkd.BeanstalkClientHelper;
import br.com.binarti.jbeanstalkd.protocol.yaml.YAML;

/**
 * An object that map beanstalk stats response. The stats dictionary is mapped as a key in this object.
 * 
 * @author francofabio
 *
 */
public class BeanstalkStats {
	
	protected Map<String, String> data;
	
	{
		data = new HashMap<String, String>();
	}
	
	public BeanstalkStats(String yaml) {
		fillFromYAML(yaml);
	}
	
	private void fillFromYAML(String yaml) {
		data.clear();
		data.putAll(YAML.parseAsMap(yaml));
	}

	/**
	 * Gets a value from stats dictionary identified by <code>key</code> parameter.
	 * @param key The stats key name
	 * @return Value if present or <code>null</code> otherwise.
	 */
	public String get(String key) {
		return data.get(key);
	}
	
	/**
	 * Returns {@link java.util.Set Set} view of the keys contained in stats Dictionary returned by beanstalkd.
	 * @return A {@link java.util.Set Set} view of the keys contained in stats Dictionary returned by beanstalkd.
	 */
	public Set<String> keySet() {
		return data.keySet();
	}
	
	/**
	 * Gets the stats key value in dictionary and convert the value to target type.
	 * 
	 * @param key The stats key name
	 * @param targetType The target type to convert
	 * @return
	 */
	public <T> T getAs(String key, Class<T> targetType) {
		String val = data.get(key);
		if (val == null) {
			return null;
		}
		return BeanstalkClientHelper.simpleConverter(val, targetType);
	}
	
}
