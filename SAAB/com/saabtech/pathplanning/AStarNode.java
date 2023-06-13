package com.saabtech.pathplanning;

public class AStarNode {
	public int m_X;
	public int m_Y;
	public double m_F;
	public double m_G;
	public int m_H;
	public int m_NumChildren;
	public int m_Number;
	public AStarNode m_Parent;
	public AStarNode m_Next;
	public AStarNode m_Children[];
	public Object m_Data;	
	
	public AStarNode(int x, int y)
	{
		m_X = x;
		m_Y = y;
		m_F = 0;
		m_G = 0;
		m_H = 0;
		m_Number = 0;
 		m_Children = new AStarNode[8];
		m_NumChildren = 0;
		m_Parent = null;
		m_Next = null;
		m_Data = null;
	}
	
	public AStarNode()
	{
		m_X = -1;
		m_Y = -1;
		m_F = 0;
		m_G = 0;
		m_H = 0;
		m_Number = 0;
 		m_Children = new AStarNode[8];
		m_NumChildren = 0;
		m_Parent = null;
		m_Next = null;
		m_Data = null;
	}
	
	public String toString()
	{
		String ret = "(astarnode " +  "x=" + m_X + " y=" + m_Y + " f=" + m_F + " g=" + m_G + " h=" + m_H + " children=" + m_NumChildren;
		return ret;
	}
}
