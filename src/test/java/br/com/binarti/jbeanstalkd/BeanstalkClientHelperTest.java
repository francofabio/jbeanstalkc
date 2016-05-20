package br.com.binarti.jbeanstalkd;

import static br.com.binarti.jbeanstalkd.BeanstalkClientHelper.simpleConverter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;

public class BeanstalkClientHelperTest {

	@Test
	public void shouldConvertToByte() {
		assertEquals(new Byte("1"), BeanstalkClientHelper.simpleConverter("1", Byte.class));
	}
	
	@Test
	public void shouldConvertToShort() {
		assertEquals(new Short("2"), BeanstalkClientHelper.simpleConverter("2", Short.class));
	}
	
	@Test
	public void shouldConvertToInteger() {
		assertEquals(new Integer("2"), BeanstalkClientHelper.simpleConverter("2", Integer.class));
	}
	
	@Test
	public void shouldConvertToLong() {
		assertEquals(new Long(2L), BeanstalkClientHelper.simpleConverter("2", Long.class));
	}
	
	@Test
	public void shouldConvertToFloat() {
		assertEquals(new Float("2"), BeanstalkClientHelper.simpleConverter("2", Float.class));
	}
	
	@Test
	public void shouldConvertToDouble() {
		assertEquals(new Double(2d), simpleConverter("2", Double.class), 2);
	}
	
	@Test
	public void shouldReturnNullIfInputIsNull() {
		assertNull(simpleConverter(null, Integer.class));
	}
	
	@Test(expected=BeanstalkException.class)
	public void shouldNotAllowConvertionToNumbers() {
		assertNull(simpleConverter("2016-03-15", Date.class));
	}
	
	@Test(expected=BeanstalkException.class)
	public void shouldNotConvertNonNumericStrToNumer() {
		simpleConverter("12s1", Integer.class);
	}
	
}
