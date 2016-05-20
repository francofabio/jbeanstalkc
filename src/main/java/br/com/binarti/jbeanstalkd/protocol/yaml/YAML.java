package br.com.binarti.jbeanstalkd.protocol.yaml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import br.com.binarti.jbeanstalkd.BeanstalkException;

public class YAML {

	private static void parse(String yaml, Consumer<YAMLEntry> callback) {
		try (BufferedReader yamlReader = new BufferedReader(new StringReader(yaml))) {
			String line;
			while ((line = yamlReader.readLine()) != null) {
				if ("".equals(line) || "---".equals(line)) {
					continue;
				}
				String[] yamlEntry = line.split(":");
				String key = yamlEntry[0].trim();
				if (key.startsWith("-")) {
					key = key.substring(1).trim();
				}
				String value = null;
				if (yamlEntry.length > 1) {
					value = yamlEntry[1].trim();
				}
				callback.accept(new YAMLEntry(key, value));
			}
		} catch (IOException e) {
			throw new BeanstalkException(String.format("Error while parse YAML data: %s", yaml), e);
		}
	}
	
	public static Map<String, String> parseAsMap(String yaml) {
		final Map<String, String> data = new HashMap<>();
		parse(yaml, e -> data.put(e.getKey(), e.getValue()));
		return data;
	}
	
	public static List<String> parseAsList(String yaml) {
		final List<String> list = new ArrayList<>();
		parse(yaml, e -> list.add(e.getKey()));
		return list;
	}
	
}
