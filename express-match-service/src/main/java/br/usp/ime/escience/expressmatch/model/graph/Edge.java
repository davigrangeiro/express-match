/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.ime.escience.expressmatch.model.graph;

/**
 *
 * @author Willian
 * @author davi
 */
public class Edge {
    
	private Vertex from, to;
    private double cost;
    
	public Edge() {
		super();
	}
	
	public Edge(Vertex from, Vertex to, double cost) {
		super();
		this.from = from;
		this.to = to;
		this.cost = cost;
	}

	public Edge(Vertex from, Vertex to) {
		super();
		this.from = from;
		this.to = to;
	}

	public Vertex getFrom() {
		return from;
	}

	public void setFrom(Vertex from) {
		this.from = from;
	}

	public Vertex getTo() {
		return to;
	}

	public void setTo(Vertex to) {
		this.to = to;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	

    
    
}
