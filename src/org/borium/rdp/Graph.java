package org.borium.rdp;

import static org.borium.rdp.GraphEdge.*;
import static org.borium.rdp.GraphNode.*;

public class Graph<NodeData extends GraphNode, EdgeData extends GraphEdge>
{
	/** The number of the next graph to be created */
	private static int graph_next_graph_count = 1;

	/** The list of active graph structures */
	static Graph<? extends GraphNode, ? extends GraphEdge> graph_list = new Graph<>();

	GraphNode root;
	Graph<? extends GraphNode, ? extends GraphEdge> next_graph;
	Graph<? extends GraphNode, ? extends GraphEdge> previous_graph;
	GraphNode next_node;
	String id;
	int atom_number;

	void insertGraph(String id)
	{
		atom_number = graph_next_graph_count++;
		next_node = null;
		graph_next_node_count = graph_next_edge_count = 1;
		// Now insert at destination of graph_list
		next_graph = graph_list.next_graph;
		graph_list.next_graph = this;
		previous_graph = graph_list;
		// if rest of list is non-null... point next node back at us
		if (next_graph != null)
		{
			next_graph.previous_graph = this;
		}
		this.id = id;
	}
}
