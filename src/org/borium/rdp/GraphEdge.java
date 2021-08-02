package org.borium.rdp;

public class GraphEdge extends GraphBase
{
	/** The number of the next edge to be created */
	static int graph_next_edge_count = 1;

	GraphNode destination;
	GraphNode source;
	GraphBase previous_in_edge;
	GraphBase previous_out_edge;

	void deleteEdge()
	{
		/* unlink this edge from the out list */
		/* make next node point back to our in */
		if (next_out_edge != null)
		{
			next_out_edge.previous_out_edge = previous_out_edge;
		}
		/* point in node at our out */
		previous_out_edge.next_out_edge = next_out_edge;
		/* unlink this edge from the in list */
		/* make next node point back to our in */
		if (next_in_edge != null)
		{
			next_in_edge.previous_in_edge = previous_in_edge;
		}
		/* point in node at our out */
		if (previous_in_edge != null)
		{
			previous_in_edge.next_in_edge = next_in_edge;
		}
	}
}
