package br.com.binarti.jbeanstalkc;

public class BeanstalkClientHelper {

	public static final String COMMAND_LINE_SEPARATOR = "\r\n";
	
	public static <T> T simpleConverter(String val, Class<T> targetType) {
		if (val == null) {
			return null;
		}
		if (String.class.isAssignableFrom(targetType)) {
			return targetType.cast(val);
		} else if (Number.class.isAssignableFrom(targetType)) {
			try {
				return targetType.getConstructor(String.class).newInstance(val);
			} catch (Exception e) {
				throw new BeanstalkException("Could not create an instance of the class " + targetType.getName(), e);
			}
		} else {
			throw new BeanstalkException("Unsupported type " + targetType.getName());
		}
	}
	
}
