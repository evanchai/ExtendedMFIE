package cn.edu.fudan.se.web.bean;

import java.util.ArrayList;
import java.util.List;

public class HistoryTree
{
	private List<HistoryNode> nodes;
	private HistoryNode currentNode;
	private static String startOfStyle = "<b><i>";
	private static String endOfStyle = "</i></b>";
	public HistoryTree()
	{
		nodes = new ArrayList<HistoryNode>();
		currentNode = null;
	}
	public void addNode(HistoryNode node, int parentID)
	{
		node.setParentID(parentID);
		nodes.add(node);
		currentNode = node;
	}
	
	public void addNode(HistoryNode node)
	{
		if(currentNode!= null)
			addNode(node, currentNode.getId());
		else
			addNode(node, -1);
	}
	
	public HistoryNode getCurrentNode(){
		return currentNode;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(HistoryNode node : nodes)
		{
			sb.append("{");
			sb.append("\"id\":\"" + node.getId() + "\",");
			sb.append("\"text\":\"" + startOfStyle + node.getType() + endOfStyle);
			if(node.getNumberOfElement() >1)
				sb.append("(" + startOfStyle + node.getNumberOfElement() + endOfStyle + " results): " );
			else
				sb.append("(" + startOfStyle + node.getNumberOfElement() + endOfStyle + " result): " );
			sb.append( startOfStyle + removeSpan( node.getParamater()) + endOfStyle);

		
			sb.append("\",");
			sb.append("\"pid\":\"" + node.getParentID() + "\",");
			sb.append("\"isexpand\":\"false\"");
			sb.append("}");
			sb.append(",");
		}
		if(sb.charAt(sb.length() -1) == ',')
			sb.deleteCharAt(sb.length() -1);
		sb.append("]");
		return sb.toString();
	}
	private String removeSpan(String text)
	{
		int indexOfSpan = text.indexOf("<span");
		while(indexOfSpan > -1)
		{
			int indexOfEndSpan = text.indexOf("</span>");
			String before = text.substring(0, indexOfSpan);
			String after = text.substring(indexOfEndSpan + 7);
			text = before + after;
			indexOfSpan = text.indexOf("<span");
		}
		return text;
	}
	public HistoryNode getNodeByID(String nodeID)
	{
		int id = Integer.parseInt(nodeID);
		for(HistoryNode node : nodes)
		{
			if(node.getId() == id)
				return node;
		}
		return null;
	}
	public void setCurrentNode(HistoryNode historyNode)
	{
		this.currentNode = historyNode;
		
	}
}
