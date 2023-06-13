package com.saabtech.server;

import java.util.Random;
import java.util.Stack;

import com.saabtech.environment.Map;
import com.saabtech.pathplanning.AStar;
import com.saabtech.pathplanning.AStarNode;

public class SAABObject {

	private long id = System.nanoTime();
	private int xCurrentPos = 0;
	private int yCurrentPos = 0;
	private int type = 0;
	private Random rand;
	private Stack<AStarNode> path = null;
	
	public final static int SHIP = 1;
	public final static int TANK = 2;
	public final static int AIRFIGHTER = 3;
	
	public SAABObject(int type) {
		rand = new Random();
		this.type = type;
		int[] randomPosition = getRandomOpenPosition();
		xCurrentPos = randomPosition[0];
		yCurrentPos = randomPosition[1];
	}
	
	public void update() {
		if (!hasGoal()) {
			int[] randomGoal = getRandomOpenPosition(xCurrentPos, yCurrentPos, 100, 100);
			AStar pathplanner = new AStar();
			if (!pathplanner.findPath(xCurrentPos, yCurrentPos, randomGoal[0], randomGoal[1]))
				return;
			path = pathplanner.getPath();
		}
		
		AStarNode tmpNode = path.pop();
		xCurrentPos = tmpNode.m_X;
		yCurrentPos = tmpNode.m_Y;
	}
		
	private int[] getRandomOpenPosition() {
		Map map = Map.getInstance();
		int tmpPos[] = new int[2];
		boolean isOpen = false;
		while (!isOpen) {
			tmpPos[0] = rand.nextInt(map.getXHigh());
			tmpPos[1] = rand.nextInt(map.getYHigh());
			if (map.isOpen(tmpPos[0], tmpPos[1]))
				isOpen = true;
		}
		return tmpPos;
	}
	
	private int[] getRandomOpenPosition(int xcurr, int ycurr, int xoffset, int yoffset) {
		Map map = Map.getInstance();
		int tmpPos[] = new int[2];
		boolean isOpen = false;
		while (!isOpen) {
			tmpPos[0] = rand.nextInt(xoffset);
			tmpPos[1] = rand.nextInt(yoffset);
			tmpPos[0] = (tmpPos[0]>(xoffset/2)?xcurr+(tmpPos[0]/2):xcurr-tmpPos[0]);
			tmpPos[1] = (tmpPos[1]>(yoffset/2)?ycurr+(tmpPos[1]/2):ycurr-tmpPos[1]);
			if (tmpPos[0] >= 0 && tmpPos[1] >= 0 && tmpPos[0] < map.getXHigh() && tmpPos[1] < map.getYHigh() && map.isOpen(tmpPos[0], tmpPos[1]))
				isOpen = true;
		}
		return tmpPos;
	}
	
	private boolean hasGoal() {
		if (path == null || path.empty())
			return false;
		else return true;
	}
	public String toString() {
		return "ID=" + id + ";X=" + xCurrentPos + ";Y=" + yCurrentPos + ";TYPE=" + type;
	}
}
