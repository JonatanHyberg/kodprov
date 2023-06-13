package com.saabtech.pathplanning;

import java.util.Iterator;
import java.util.Stack;
import java.util.Calendar;

import com.saabtech.environment.Map;

public class AStar {
	private Map map;
	private Stack<AStarNode> openNodes;
	private Stack<AStarNode> closedNodes;
	private Stack<AStarNode> path;
	private AStarNode startNode, goalNode;
	private int stepCounter;
	private int calcTimeout = 2000; //Give two sec for path-finding
	private boolean go = false;
	private long elapsedTime = 0;
	private long startTime = 0;

	public AStar() {
		openNodes = new Stack<AStarNode>();
		closedNodes = new Stack<AStarNode>();
		path = new Stack<AStarNode>();
	}

	private void clear() {
		openNodes = new Stack<AStarNode>();
		closedNodes = new Stack<AStarNode>();
		path = new Stack<AStarNode>();
		elapsedTime = 0;
		startTime = 0;
		go = false;
		map = null;
		startNode = null;
		goalNode = null;
		stepCounter = 0;
	}

	public boolean findPath(final int startX, final int startY,
			final int goalX, final int goalY) {
		clear();
		stepInitialize(startX, startY, goalX, goalY);

		go = true;
		boolean ok = false;
		if (go == true) {
			startTime = Calendar.getInstance().getTimeInMillis();
			ok = generatePath();
			if (ok) {
				updatePath();
			}
			elapsedTime = Calendar.getInstance().getTimeInMillis() - startTime;
			go = false;
		}
		return ok;
	}

	private void stepInitialize(int startX, int startY, int goalX, int goalY) {
		map = Map.getInstance();
		stepCounter = 0;

		goalNode = new AStarNode(goalX, goalY);
		goalNode.m_Number = ((int) map.getXHigh() * goalNode.m_Y)
				+ goalNode.m_X;

		startNode = new AStarNode(startX, startY);
		startNode.m_G = 0;
		startNode.m_H = manhattanDistance(startNode, goalNode); //Manhattan distance
		startNode.m_F = startNode.m_G + startNode.m_H;
		startNode.m_Number = ((int) map.getXHigh() * startNode.m_Y)
				+ startNode.m_X;

		openNodes.push(startNode);
	}

	private int manhattanDistance(AStarNode n1, AStarNode n2) {
		return (Math.abs(n1.m_X - n2.m_X) + Math.abs(n1.m_Y - n2.m_Y));
	}

	private boolean generatePath() {
		int ret = 0;
		while (ret == 0) {
			ret = step();
			if (stepCounter % 10 == 0) {
				elapsedTime = Calendar.getInstance().getTimeInMillis()
						- startTime;
				if (elapsedTime > calcTimeout) {
					ret = -1;
				}
			}

		}
		elapsedTime = Calendar.getInstance().getTimeInMillis() - startTime;
		if (ret == -1) {
			return false;
		}

		return true;
	}

	private int step() {

		stepCounter++;
		AStarNode best = getBest();

		if (best == null) {
				return -1;
		}

		if (best.m_Number == goalNode.m_Number) {
	
			return 1;
		}
	
		createChildren(best);
		return 0;
	}

	private AStarNode getBest() {
		AStarNode best = null;
		int cnt = 0;

		Iterator iter = openNodes.iterator();
		while (iter.hasNext()) {
			AStarNode tmpNode = (AStarNode) iter.next();
			if (cnt == 0) {
				best = tmpNode;
			}

			if (tmpNode.m_F < best.m_F) {
				best = tmpNode;
			}
			cnt++;
		}

		return best;
	}

	private void createChildren(AStarNode node) {
		int x = node.m_X;
		int y = node.m_Y;
		if (map.isOpen(x + 1, y)) {
			linkChild(node, new AStarNode(x + 1, y));
		}
		if (map.isOpen(x - 1, y)) {
			linkChild(node, new AStarNode(x - 1, y));
		}
		if (map.isOpen(x, y + 1)) {
			linkChild(node, new AStarNode(x, y + 1));
		}
		if (map.isOpen(x, y - 1)) {
			linkChild(node, new AStarNode(x, y - 1));
		}
		if (map.isOpen(x - 1, y + 1)) {
			linkChild(node, new AStarNode(x - 1, y + 1));
		}
		if (map.isOpen(x + 1, y + 1)) {
			linkChild(node, new AStarNode(x + 1, y + 1));
		}
		if (map.isOpen(x + 1, y - 1)) {
			linkChild(node, new AStarNode(x + 1, y - 1));
		}
		if (map.isOpen(x - 1, y - 1)) {
			linkChild(node, new AStarNode(x - 1, y - 1));
		}
		closedNodes.push(node);
		openNodes.remove(node);
	}

	private void linkChild(AStarNode node, AStarNode temp) {
		int x = temp.m_X;
		int y = temp.m_Y;
		int num = (int) map.getXHigh() * y + x;

		double g = node.m_G + 1; //Cost is applied by simply adding 1 to the parents cost

		AStarNode checkOpen = checkList(openNodes, num);
		AStarNode checkClosed = checkList(closedNodes, num);

		if (checkOpen != null) {
			node.m_Children[node.m_NumChildren++] = checkOpen;
			if (g < checkOpen.m_G) {
				checkOpen.m_Parent = node;
				checkOpen.m_G = g;
				checkOpen.m_F = g + checkOpen.m_H;
			}
		} else if (checkClosed != null) {
			node.m_Children[node.m_NumChildren++] = checkClosed;
			if (g < checkClosed.m_G) {
				checkClosed.m_Parent = node;
				checkClosed.m_G = g;
				checkClosed.m_F = g + checkClosed.m_H;
				updateParents(checkClosed);
			}
		} else {
			AStarNode newNode = new AStarNode(x, y);
			newNode.m_Parent = node;
			newNode.m_G = g;
			newNode.m_H = manhattanDistance(newNode, goalNode);//(Math.abs(x - goal.x) + Math.abs(y - goal.y));
			newNode.m_F = newNode.m_G + newNode.m_H;
			newNode.m_Number = ((int) map.getXHigh()) * y + x;
			openNodes.push(newNode);
			node.m_Children[node.m_NumChildren++] = newNode;
		}
	}

	private void updatePath() {
		AStarNode best = getBest();
		path.push(best);

		while (best.m_Number != startNode.m_Number) {
			AStarNode parent = best.m_Parent;
			path.push(parent);
			best = parent;
		}

	}

	private void updateParents(AStarNode node) {
		double g = node.m_G;
		int c = node.m_NumChildren;
		AStarNode kid = null;
		
		for (int i = 0; i < c; i++) {
			kid = node.m_Children[i];
			if (g + 1 < kid.m_G) {
			
				kid.m_G = g + 1;
				kid.m_F = kid.m_G + kid.m_H;
				kid.m_Parent = node;
				path.push(kid);
			}
		}

		AStarNode parent;
		while (path.size() > 0) {
			
			parent = (AStarNode) path.pop();
			c = parent.m_NumChildren;
			for (int i = 0; i < c; i++) {
				kid = parent.m_Children[i];
				if (parent.m_G + 1 < kid.m_G) {
			
					kid.m_G = parent.m_G + 1;
					kid.m_F = kid.m_G + kid.m_H;
					kid.m_Parent = parent;
					path.push(kid);
				}
			}
		}

	}

	private AStarNode checkList(Stack list, int num) {
		AStarNode ret = null;
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			AStarNode tmpNode = (AStarNode) iter.next();

			if (tmpNode.m_Number == num) {
			
				ret = tmpNode;
				break;
			}
		}
		return ret;
	}

	public Stack getClosed() {
		return closedNodes;
	}

	public AStarNode getGoal() {
		return goalNode;
	}

	public Stack getOpen() {
		return openNodes;
	}

	public Stack<AStarNode> getPath() {
		return path;
	}

	public AStarNode getStart() {
		return startNode;
	}

	public double getG() {
		if (path != null) {
			if (!path.isEmpty()) {
				AStarNode node = (AStarNode) path.firstElement();
				return node.m_G;
			}
		}
		return -1;
	}

	public double getF() {
		if (path != null) {
			if (!path.isEmpty()) {
				AStarNode node = (AStarNode) path.firstElement();
				return node.m_F;
			}
		}
		return -1;
	}

	public double getH() {
		if (path != null) {
			if (!path.isEmpty()) {
				AStarNode node = (AStarNode) path.firstElement();
				return node.m_H;
			}
		}
		return -1;
	}
}
