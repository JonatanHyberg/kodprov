package com.saabtech.server;

import java.io.IOException;
import java.net.ServerSocket;

public class SAABServer {

	private static int serverPort = 5463; // DEFAULT

	public static void main(String[] args) {

		ServerSocket serverSocket = null;

		ServerConfig config = ServerConfig.getInstance();
		serverPort = (config.get("SERVERPORT")!=null?Integer.parseInt(config.get("SERVERPORT")):serverPort);

		try {
			System.out.print("Setting up server socket at port " + serverPort);
			serverSocket = new ServerSocket(serverPort);
			System.out.println(" ... DONE!");
			while (true) {
				System.out.print("Waiting for client to connect ");
				new SAABServerWorkerThread(serverSocket.accept()).start();
				System.out.println("... CONNECTED!");
			}
		} catch (IOException e) {
			System.out.println("... FAILED! Error: " + e.getMessage());
			System.exit(1);
		}
		try {
			System.out.print("Closing down server socket ");
			serverSocket.close();
			System.out.println("... DONE!");
		} catch (IOException e) {
			System.out.println("... FAILED! Error: " + e.getMessage());
		}


	}

}
