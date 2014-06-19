package br.usp.ime.escience.expressmatch.service.graph.mst;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;

import br.usp.ime.escience.expressmatch.model.graph.Edge;
import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Node;
import br.usp.ime.escience.expressmatch.model.graph.Vertex;
import br.usp.ime.escience.expressmatch.service.graph.cost.Cost;
import br.usp.ime.escience.expressmatch.service.graph.cost.EuclideanDistance;
import br.usp.ime.escience.expressmatch.service.graph.mst.general.Candidate;
import br.usp.ime.escience.expressmatch.service.graph.mst.general.CompleteGraphBuilder;
import br.usp.ime.escience.expressmatch.service.graph.mst.general.MinimumSpanningTree;

public class PrimMST implements MinimumSpanningTree {

    Node [] nodeList;

    //It should be an edge list of the graph (two-way edges !!!)
    ArrayList<Edge>[] neighbours;

	@Override
	public Node[] getMST(Graph in) {
		return getMST(in, new EuclideanDistance());
	}
    
	@Override
	public Node[] getMST(Graph in, Cost<Vertex, Double> cost) {
		
		PriorityQueue<Candidate> pq = new PriorityQueue<Candidate> ();
		Node currentVertex, 
			 nextVertex;
		Candidate c;
		createBaseGraph(in, cost);
		
        nodeList[0].setValue(0.0);
        pq.add (new Candidate (0, 0));
        
        while (!pq.isEmpty ()) {
        	
        	c = pq.remove();
            currentVertex = nodeList[c.index];
                
            if (currentVertex.isVisited()) {
            	continue;
            }
            
            currentVertex.setVisited(Boolean.TRUE);
            
            for (Edge currentEdge : neighbours[c.index]){
            	
                nextVertex = (Node) currentEdge.getTo();
                if (nextVertex.isVisited() || nextVertex.getValue() < currentEdge.getCost()) {
                        continue;
                }
                nextVertex.setPrevious(currentVertex);
                nextVertex.setValue(currentEdge.getCost());
                pq.add(new Candidate(nextVertex.getId(), nextVertex.getValue()));
            }
        }
        
        
        for (int i = 0; i < nodeList.length; i++) {
			Node nodeI = nodeList[i];
			
			if (null != nodeI.getPrevious()) {
				nodeList[nodeI.getPrevious().getNodeId()].getNext().add(nodeI);
			}
			
		}
        
        return nodeList;
	}

	private void createBaseGraph(Graph in, Cost<Vertex, Double> cost) {
		int i = 0;
		nodeList = new Node[in.getVertexSize()];
		
		for (Vertex v : in.getIndexedVertexes()) {
			nodeList[i] = new Node(v.getId(), v.getX(), v.getY(), i);
			nodeList[i].setValue(Double.MAX_VALUE);
			nodeList[i].setVisited(Boolean.FALSE);
			i++;
		}
		
		neighbours = CompleteGraphBuilder.getCompleteGraphEdges(nodeList, cost);
	}
	
	public static void main(String[] args) {
		Graph g = new Graph();

		g.addVertex(new Vertex(0, 1, 1));
		g.addVertex(new Vertex(1, 3, 3));
		g.addVertex(new Vertex(2, 4, 1));
		g.addVertex(new Vertex(3, 5, 3.5));
		g.addVertex(new Vertex(4, 7, 2));
		g.addVertex(new Vertex(5, 9, 1));
		g.addVertex(new Vertex(6, 9, 2.5));
		g.addVertex(new Vertex(7, 10, 2.5));
		g.addVertex(new Vertex(8, 12, 1));
		g.addVertex(new Vertex(9, 3, 3.5));
		
		MinimumSpanningTree mst = new PrimMST();
		Node[] nodes = mst.getMST(g);
		
		for (Node node : nodes) {
			System.out.println(MessageFormat.format("id:{0}, x:{1}, y:{2}, Previous:{3}", node.getNodeId(), node.getX(), node.getY(), node.getPrevious()!= null ? node.getPrevious().getNodeId():"null"));
		}
		
	}
		
}
