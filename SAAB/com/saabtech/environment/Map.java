package com.saabtech.environment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.saabtech.server.ServerConfig;

public class Map {
	
	private int[][] _map;
	private int intWidth = 0;
	private int intHeight = 0;
	private static Map instance = null;
	
	private Map() {

		try {
			
			BufferedImage image = ImageIO.read(new File(ServerConfig.getInstance().get("MAP")));
			intWidth = image.getWidth();
			intHeight = image.getHeight();
			_map = new int[intWidth][intHeight];
			for (int x = 0; x < intWidth; x++) {
				for (int y = 0; y < intHeight; y++) {
					if (image.getRGB(x, y) != -1) {
						_map[x][y] = 1;
					} else {
						_map[x][y] = 0;
					}
				}
			}

		} catch (IOException e) {
			intWidth = 0;
			intHeight = 0;
			e.printStackTrace();
		}
	};

	public synchronized static Map getInstance() {
		if (instance == null) {
			instance = new Map();
		}
		return instance;
	}

	public int getValue(int x, int y) {
		if ((x >= 0 && x <= intWidth) && (y >= 0 && y <= intHeight)) {
			return _map[x][y];
		} else
			return -1;
	}

	public int getXHigh() {
		return intWidth;
	}

	public int getYHigh() {
		return intHeight;
	}

	public boolean isOpen(int x, int y) {
		if (getValue(x, y) == 0)
			return true;
		else
			return false;
	}

	public void setClosed(int x, int y) {
		if (x <= getXHigh() && x >= 0 && y <= getYHigh() && y >= 0) {
			_map[x][y] = 2;
		}
	}
}