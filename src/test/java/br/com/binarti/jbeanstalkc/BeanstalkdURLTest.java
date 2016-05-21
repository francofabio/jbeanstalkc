package br.com.binarti.jbeanstalkc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BeanstalkdURLTest {

	@Test
	public void shouldParseBeanstalkdURL() {
		String url = "beanstalkd://localhost:15000";
		BeanstalkdURL beanstalkdURL = new BeanstalkdURL(url);
		assertEquals("beanstalkd", beanstalkdURL.getSchema());
		assertEquals("localhost", beanstalkdURL.getHost());
		assertEquals(15000, beanstalkdURL.getPort());
	}
	
	@Test
	public void shouldParseBeanstalkdURLWithParameters() {
		String url = "beanstalkd://localhost:15000?param1=value1&param2=value2";
		BeanstalkdURL beanstalkdURL = new BeanstalkdURL(url);
		assertEquals("beanstalkd", beanstalkdURL.getSchema());
		assertEquals("localhost", beanstalkdURL.getHost());
		assertEquals(15000, beanstalkdURL.getPort());
		assertEquals("value1", beanstalkdURL.getParams().get("param1"));
		assertEquals("value2", beanstalkdURL.getParams().get("param2"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldNotAllowURLWithoutSchema() {
		String url = "localhost:15000?param1=value1&param2=value2";
		new BeanstalkdURL(url);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldNotAllowURLWithoutHost() {
		String url = "beanstalkd://:15000?param1=value1&param2=value2";
		new BeanstalkdURL(url);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldNotAllowURLWithoutPort() {
		String url = "beanstalkd://localhost?param1=value1&param2=value2";
		new BeanstalkdURL(url);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldNotParseURLWithWrongSchema() {
		String url = "beanstalk://localhost:15000?param1=value1&param2=value2";
		new BeanstalkdURL(url);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldNotAllowInvalidURL() {
		new BeanstalkdURL("my_wrong_url");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldNotAllowIncompleteParameterInURL() {
		new BeanstalkdURL("beanstalkd://localhost:15000?param1=value1&param2");
	}
	
}
