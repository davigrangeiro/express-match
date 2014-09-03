/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.ime.escience.expressmatch.model.graph;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.usp.ime.escience.expressmatch.model.UserParameter;
import br.usp.ime.escience.expressmatch.service.graph.cost.ShapeContext;

/**
 *
 * @author Willian Honda
 */
public class Graph {

    protected LinkedList<Vertex> vertices;
    protected LinkedList<Edge> edges;

    public Graph() {
        this.vertices = new LinkedList<Vertex>();
        this.edges = new LinkedList<Edge>();
    }

    public Vertex addVertex(int id, float x, float y) {
        Vertex v = new Vertex(id, x, y);
        this.addVertex(v);
        return v;
    }
    
    public Vertex addVertex(int id, int strokeId, float x, float y) {
        Vertex v = new Vertex(id,strokeId, x, y);
        this.addVertex(v);
        return v;
    }

    public Vertex addVertex(Vertex v) {
        this.vertices.addLast(v);
        return v;
    }

    public Edge addEdge(Vertex from, Vertex to) {
        Edge e = new Edge(from, to);
        this.edges.addLast(e);
        return e;
    }

    public void replaceVertex(Vertex from, Vertex to) {
        this.vertices.remove(from);
        this.vertices.add(to);
    }

    public Vertex[] getIndexedVertexes() {
        Iterator<Vertex> it = this.vertices.iterator();
        Vertex[] refv = new Vertex[this.getVertexSize()];
        while (it.hasNext()) {
            Vertex v = (Vertex) it.next();
            refv[v.getId()] = v;
        }
        return refv;
    }

    public void updateShapeContextExpression(UserParameter parameter){
        double[][] sc = this.calculateShapeContextExpression(parameter);
        Vertex[] vertex = this.getIndexedVertexes();
        for (int i = 0; i < vertex.length; i++) {
            vertex[i].setShapeContextExpression(sc[i]);
        }
    }

    private double[][] calculateShapeContextExpression(UserParameter parameters) {
        float diagonal = (float)Math.sqrt(Math.pow(this.getHeight(), 2)
                    + Math.pow(this.getWidth(), 2));
        ShapeContext sc = new ShapeContext(diagonal, this, parameters.getPolarGlobalRegions(), parameters.getAngularGlobalRegions());
        return sc.getSC();
    }

    public double getWidth() {
        double minX = 0, maxX = 0;
        Iterator<Vertex> it = this.vertices.iterator();
        if (it.hasNext()) {
            Vertex v = (Vertex) it.next();
            minX = maxX = v.getX();
        }
        while (it.hasNext()) {
            Vertex v = (Vertex) it.next();
            if (v.getX() < minX) {
                minX = v.getX();
            }
            if (v.getX() > maxX) {
                maxX = v.getX();
            }
        }
        return maxX - minX;
    }

    public double getHeight() {
        double minY = 0, maxY = 0;
        Iterator<Vertex> it = this.vertices.iterator();
        if (it.hasNext()) {
            Vertex v = (Vertex) it.next();
            minY = maxY = v.getY();
        }
        while (it.hasNext()) {
            Vertex v = (Vertex) it.next();
            if (v.getY() < minY) {
                minY = v.getY();
            }
            if (v.getY() > maxY) {
                maxY = v.getY();
            }
        }
        return maxY - minY;
    }

    public Point2D getMinPositions() {
        double minY = 0, minX = 0;
        Iterator<Vertex> it = this.vertices.iterator();
        if (it.hasNext()) {
            Vertex v = (Vertex) it.next();
            minY = v.getY();
            minX = v.getX();
        }
        while (it.hasNext()) {
            Vertex v = (Vertex) it.next();
            if (v.getX() < minX) {
                minX = v.getX();
            }
            if (v.getY() < minY) {
                minY = v.getY();
            }
        }
        return new Point2D.Double(minX, minY);
    }

    public Point2D getCentroid() {
        double width = this.getWidth();
        double height = this.getHeight();
        Point2D min = this.getMinPositions();
        return new Point2D.Double(min.getX() + (width / 2), min.getY() + (height / 2));
    }

    @SuppressWarnings("unchecked")
	public List<Edge>[] getNeighboursList() {
        List<Edge>[] neighbours = new ArrayList[this.getVertexSize()];
        
        for (int i = 0; i < neighbours.length; i++) {
            neighbours[i] = new LinkedList<Edge>();
        }

        Iterator<Edge> it = this.edges.iterator();
        while (it.hasNext()) {
            Edge e = (Edge) it.next();
            Vertex from = e.getFrom();
            Vertex to = e.getTo();
            if (from != to) {
                neighbours[from.getId()].add(e);
                neighbours[to.getId()].add(e);
            }
        }
        return neighbours;
    }

    public LinkedList<Edge> getEdges() {
        return this.edges;
    }

    public int getVertexSize() {
        return this.vertices.size();
    }

    public int getEdgeSize() {
        return this.edges.size();
    }
}
