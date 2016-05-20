package br.com.binarti.jbeanstalkc.protocol;

import static br.com.binarti.jbeanstalkc.BeanstalkClientHelper.COMMAND_LINE_SEPARATOR;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.binarti.jbeanstalkc.BeanstalkException;

public class BeanstalkCommandInterface {

	private Logger logger = Logger.getLogger(BeanstalkCommandInterface.class);
	
	protected Socket socket;
	
	public BeanstalkCommandInterface(Socket socket) {
		this.socket = socket;
	}
	
	/**
	 * Send a given command to beanstalk server
	 * 
	 * @param command Command to send
	 * @throws BeanstalkException
	 */
	protected void sendCommand(BeanstalkRequest command) throws BeanstalkException {
		try {
			String encodedCommand = command.encode();
			logger.debug("Send data to server: " + encodedCommand);
			
			OutputStream output = socket.getOutputStream();
			output.write(encodedCommand.getBytes(StandardCharsets.ISO_8859_1));
			output.flush();
		} catch (IOException e) {
			throw new BeanstalkException("Unable to send command from server", e);
		}
	}
	
	/**
	 * Receive command response from beanstalk server.
	 * 
	 * @return Response data
	 * @throws BeanstalkException
	 */
	protected BeanstalkResponse receiveCommandResponse() throws BeanstalkException {
		try {
			logger.debug("Receiving response from server");
			InputStream input = socket.getInputStream();
			StringBuilder receivedData = new StringBuilder();
			char data;
			char prevData = '\0';
			while ((data = (char) input.read()) != -1) {
				receivedData.append(data);
				if (prevData == '\r' && data == '\n') {
					break;
				}
				prevData = data;
			}
			logger.debug("Response received: " + receivedData.toString());
			return BeanstalkResponse.decode(receivedData.toString());
		} catch (IOException e) {
			throw new BeanstalkException("Unable to receive command response from server", e);
		}
	}
	
	/**
	 * Receive command response from beanstalk server
	 * @param sizeParamIndex The parameter index that contains size of the received data.
	 * @param chunksResponse Valid responses to receive chunks
	 * @return Response data
	 * @throws BeanstalkException
	 * @throws BeanstalkCommandException
	 */
	protected BeanstalkResponse receiveChunkCommandResponse(int sizeParamIndex, List<BeanstalkCommandReason> chunksResponse, BeanstalkCommandReason...expectedReasons) throws BeanstalkException, BeanstalkCommandException {
		BeanstalkResponse response = receiveCommandResponse();
		try {
			if (chunksResponse.contains(response.getReason())) {
				int bytes = response.getParamAs(sizeParamIndex, Integer.class);
				logger.debug(String.format("Receiving bytes from server: %d bytes", bytes));
				byte[] buf = new byte[bytes + 2];
				InputStream input = socket.getInputStream();
				input.read(buf);
				String data = new String(buf, 0, buf.length-2, StandardCharsets.ISO_8859_1);
				String commandEnd = new String(buf, buf.length-2, 2, StandardCharsets.ISO_8859_1);
				if (!commandEnd.equals(COMMAND_LINE_SEPARATOR)) {
					throw new BeanstalkCommandException("Invalid server response. Expected \\r\\n as end of command");
				}
				response.setChunk(true);
				response.setData(data);
				logger.debug(String.format("Chunks received from server: " + data));
			}
			return response;
		} catch (IOException e) {
			throw new BeanstalkException("Unable to receive chunk command response from server", e);
		}
	}
	
	/**
	 * Submit a given command and receive response from beanstalk server
	 * 
	 * @param command The command submit to server
	 * @return
	 * @throws BeanstalkException
	 * @throws BeanstalkCommandException
	 */
	protected BeanstalkResponse perform(BeanstalkRequest command, BeanstalkCommandReason...expectedReasons) throws BeanstalkException, BeanstalkCommandException {
		sendCommand(command);
		BeanstalkResponse response = receiveCommandResponse();
		checkResponse(response, expectedReasons);
		return response;
	}
	
	/**
	 * Submit a given command and receive a chunk response from beanstalk server
	 * 
	 * @param sizeParamIndex The parameter index that contains size of the received data.
	 * @param chunksResponse Valid responses to receive chunks
	 * @return
	 * @throws BeanstalkException
	 * @throws BeanstalkCommandException
	 */
	protected BeanstalkResponse performChunkCommand(BeanstalkRequest command, int sizeParamIndex, List<BeanstalkCommandReason> chunksResponse, BeanstalkCommandReason...expectedReasons) throws BeanstalkException, BeanstalkCommandException {
		sendCommand(command);
		BeanstalkResponse response = receiveChunkCommandResponse(sizeParamIndex, chunksResponse);
		checkResponse(response, expectedReasons);
		return response;
	}
	
	private void checkResponse(BeanstalkResponse response, BeanstalkCommandReason...expectedReasons) throws BeanstalkCommandException {
		List<BeanstalkCommandReason> listExpectedReasons = Arrays.asList(expectedReasons);
		if (!listExpectedReasons.contains(response.getReason())) {
			throw new BeanstalkCommandException(response.getReason().name(), expectedReasons);
		}
	}
	
}
