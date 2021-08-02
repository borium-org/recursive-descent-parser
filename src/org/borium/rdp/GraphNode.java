package org.borium.rdp;

public abstract class GraphNode extends GraphBase
{
	/** The number of the next node to be created */
	static int graph_next_node_count = 1;

	GraphNode next_node;
	GraphNode previous_node;

	void deleteNode()
	{
		// delete out edges
		while (next_out_edge != null)
		{
			next_out_edge.deleteEdge();
		}
		// delete in edges
		while (next_in_edge != null)
		{
			next_in_edge.deleteEdge();
		}
		// now unlink this node
		if (next_node != null)
		{
			next_node.previous_node = previous_node;
		}
		// point in node at our out
		previous_node.next_node = next_node;
	}

	abstract int getId();
}
