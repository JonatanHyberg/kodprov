package com.saabtech.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

public class SAABServerWorkerThread extends Thread {

	private Socket socket = null;
	private Vector<SAABObject> objects = new Vector<SAABObject>();
	
	public SAABServerWorkerThread(Socket s) {
		super("SAABServerWorkerThread");
		socket = s;
		Random rand = new Random();
		int numOfObjects = 3 + rand.nextInt(8);
	
		for (int i = 0 ; i < numOfObjects; i++) {
			objects.add(new SAABObject(rand.nextInt(3)+1));
		}
	 
	}
	
	public void run() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("Creating output stream failed! Error: " + e.getMessage());
		}
		while (true) {
			for (Enumeration<SAABObject> objEnum = objects.elements(); objEnum.hasMoreElements();) {
				SAABObject obj = objEnum.nextElement();
				obj.update();
				out.println(obj.toString());
			}
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
