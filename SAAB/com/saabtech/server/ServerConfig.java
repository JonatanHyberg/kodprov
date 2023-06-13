package com.saabtech.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerConfig {

	private Properties serverProps = new Properties();
	private static ServerConfig instance = null;
	
	private ServerConfig() {
		try {
			System.out.print("Loading server config " + new File("server.properties").getAbsolutePath());
			serverProps.load(new FileInputStream(new File("server.properties")));
			System.out.println("... DONE!");
		} catch (IOException e) {
			System.out.println("... FAILED! Error: " + e.getMessage());
		}
	}
	
	public static ServerConfig getInstance() {
		if (instance == null)
			instance = new ServerConfig();
		
		return instance;
	}
	
	public String get(Object key) {
		if (serverProps.containsKey(key))
			return serverProps.get(key).toString();
		else return null;
	}
	
	public void set(Object key, Object value) {
		serverProps.put(key, value);
	}
	
}
