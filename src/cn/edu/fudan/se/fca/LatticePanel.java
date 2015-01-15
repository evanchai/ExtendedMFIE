package cn.edu.fudan.se.fca;

import java.util.ArrayList;
import java.util.List;
//
// 2001-07-10; Holger Zahnleiter    
//
// This class implements the visualisation of a Formal Concept Lattice.
//      

public class LatticePanel

{

	private int nnodes;
	private LatticeNode nodes[] = new LatticeNode[100];
	private int nedges;
	private LatticeEdge edges[] = new LatticeEdge[200];

	// ---- Construction --------------------------------------------------

	LatticePanel(Context context)
	{
		context.buildLattice(this);
	}

	//
	// How many nodes/Concepts does this Lattice contain?
	//
	int getNumberOfNodes()
	{
		return nnodes;
	}

	//
	// Add a new node to the Lattce and set its captions to
	// the Object names and Attribute names.
	//
	int addNode(String objectNames, String attributeNames)
	{
		LatticeNode n = new LatticeNode();
		n.x = 10 + 500 * Math.random();
		n.y = 10 + 200 * Math.random();
		n.objectNames = objectNames;
		n.attributeNames = attributeNames;
		nodes[nnodes] = n;
		return nnodes++;
	}

	
	//
	// Offers access to a node by using its position/index in the array.
	//
	LatticeNode getNode(int i)
	{
		if (i >= 0 && i < nnodes)
			return nodes[i];
		else
			return null;
	}

	//
	// Finds a node by looking at the Objects and Attributes
	// it does contain. Attention: it compares the objects/attributes
	// by looking at the node labels. It works in this example.
	// In general a node should contain two sets. One representing
	// all Object names and one representing all Attribute names contained
	// in this node/Concept caption.
	//
	int findNode(String objectNames, String attributeNames)
	{
		for (int i = 0; i < nnodes; i++)
		{
			if (nodes[i].objectNames.equals(objectNames) && nodes[i].attributeNames.equals(attributeNames))
			{
				return i;
			}
		}
		return -1;
	}

	public List<LatticeNode> getChildren(LatticeNode node)
	{
		List<LatticeNode> result = new ArrayList<LatticeNode>();
		int index = -1;
		for (int i = 0; i < nodes.length; i++)
		{
			if (nodes[i].equals(node))
			{
				index = i;
				break;
			}
		}
		if (index > -1)
		{
			for (int i = 0; i < nedges; i++)
			{
				if (edges[i].from == index)
				{
					result.add(nodes[edges[i].to]);
				}
			}

		}
		return result;
	}

	public LatticeNode getNodeByID(String id)
	{
		for (LatticeNode node : nodes)
		{
			if (node.id.equals(id))
				return node;
		}
		return null;
	}

	//
	// Adds an edge which 'connects' two nodes. The nodes are identified
	// by their position/index in the array holding all nodes.
	//
	int addEdge(int from, int to)
	{
		LatticeEdge e;
		if (edgeExists(from, to))
			return findEdge(from, to);
		e = new LatticeEdge();
		e.from = from;
		e.to = to;
		edges[nedges] = e;
		return nedges++;
	}

	//
	// Returns the number of edges which are part of this Lattice.
	//
	int getNumberOfEdges()
	{
		return nedges;
	}

	//
	// Offers access to an edge by using its position/index in the array.
	//
	LatticeEdge getEdge(int i)
	{
		if (i >= 0 && i < nedges)
			return edges[i];
		else
			return null;
	}

	//
	// Checks if there is an edge connecting two nodes. The nodes are
	// identified by their position/index in the array.
	//
	boolean edgeExists(int from, int to)
	{
		for (int i = 0; i < nedges; i++)
			if (edges[i].from == from && edges[i].to == to)
				return true;
		return false;
	}

	//
	// Returns an edge connecting two nodes. The nodes are
	// identified by their position/index in the array.
	//
	int findEdge(int from, int to)
	{
		for (int i = 0; i < nedges; i++)
			if (edges[i].from == from && edges[i].to == to)
				return i;
		return -1;
	}

	//
	// Returns a set of integers representing all nodes where the
	// given node (from) is pointing at.
	//
	IntegerSet findEdgeDestinations(int from)
	{
		IntegerSet Result = new IntegerSet();
		for (int i = 0; i < nedges; i++)
		{
			if (edges[i].from == from)
				Result.addElement(edges[i].to);
		}
		return Result;
	}

	public LatticeNode getSupreNode()
	{
		
		try
		{
			for (LatticeNode node : nodes)
			{
				
				if (node.objectNames.equals(LatticeNode.SUPRE_NODE_NAME))
					return node;
			}
		}
		catch (Exception e)
		{
			
			System.out.println(e.getMessage());
		}
		return null;
	}

}