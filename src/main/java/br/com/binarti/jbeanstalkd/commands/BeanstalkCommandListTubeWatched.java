package br.com.binarti.jbeanstalkd.commands;

import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandCode.LIST_TUBES_WATCHED;
import static br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandReason.OK;
import static java.util.Arrays.asList;

import java.net.Socket;
import java.util.Collections;
import java.util.List;

import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandDataBuilder;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkCommandInterface;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkRequest;
import br.com.binarti.jbeanstalkd.protocol.BeanstalkResponse;
import br.com.binarti.jbeanstalkd.protocol.yaml.YAML;

public class BeanstalkCommandListTubeWatched extends BeanstalkCommandInterface {

	public BeanstalkCommandListTubeWatched(Socket socket) {
		super(socket);
	}

	public List<String> perform() {
		BeanstalkRequest command = new BeanstalkCommandDataBuilder(LIST_TUBES_WATCHED).build();
		BeanstalkResponse response = performChunkCommand(command, 0, asList(OK), OK);
		if (response.getParamAs(0, Integer.class) > 0) {
			return YAML.parseAsList(response.getData());
		}
		return Collections.emptyList();
	}
	
}
