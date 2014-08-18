package br.usp.ime.escience.expressmatch.model.graph;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Node extends Vertex {

	private List<Node> next;
	private Node previous;
	private int nodeId;
	private boolean visited;
	
	public Node(int id, double x, double y) {
		super(id, x, y);
		next = new ArrayList<>();
		previous = null;
	}

	public Node(int id, double x, double y, int nodeId) {
		super(id, x, y);
		this.nodeId = nodeId;
		next = new ArrayList<>();
		previous = null;
	}


	public Node(Point2D p) {
		super(p);
	}

	public List<Node> getNext() {
		return next;
	}

	public void setNext(List<Node> next) {
		this.next = next;
	}
	
	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

}
