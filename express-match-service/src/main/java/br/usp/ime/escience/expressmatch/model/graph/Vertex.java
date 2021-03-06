/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.ime.escience.expressmatch.model.graph;

import java.awt.geom.Point2D;

/**
 * @author Willian Honda
 */
public class Vertex {

	Point2D p;
	private int id; // indice no grafo
	private int strokeId; // indice do stroke, pode ser nulo
	private double[] shapeContextExpression; // cada vertices tem um vetor de 60 medidas (shape context)
	private double[][] shapeContextSymbol;
	private double vertexSize;
	
	public Vertex(double[][] shapeContextSymbol) {
		super();
		this.shapeContextSymbol = shapeContextSymbol;
	}

	private double value;
	
	public Vertex(Point2D p) {
		this.p = p;
	}

	public Vertex(int id, double x, double y) {
		this(new Point2D.Double(x, y));
		this.id = id;
	}
	
	public Vertex(int id, int strokeId, double x, double y) {
		this(new Point2D.Double(x, y));
		this.strokeId = strokeId;
		this.id = id;
	}

	public float compareShapeContextExpression(Vertex other) {
		double[] localHistogram = this.getShapeContextExpression();
		double[] externalHistogram = other.getShapeContextExpression();
		return this.calculateShapeContext(localHistogram, externalHistogram);
	}

	private float calculateShapeContext(double[] localHistogram,
			double[] externalHistogram) {
		double sum = 0.0;
		for (int i = 0; i < localHistogram.length; i++) {
			double d1 = localHistogram[i] - externalHistogram[i];
			double d2 = localHistogram[i] + externalHistogram[i];
			if (d2 > 0) {
				sum = sum + (d1 * d1) / d2;
			}
		}
		return (float) (sum / 2.0);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public double getX() {
		return this.p.getX();
	}

	public double getY() {
		return this.p.getY();
	}

	public void setX(double x) {
		this.p.setLocation(x, this.p.getY());
	}

	public void setY(double y) {
		this.p.setLocation(this.p.getX(), y);
	}

	public double[] getShapeContextExpression() {
		return this.shapeContextExpression;
	}

	public void setShapeContextExpression(double[] shapeContext) {
		this.shapeContextExpression = shapeContext;
	}

	public double[][] getShapeContextSymbol() {
		return shapeContextSymbol;
	}

	public void setShapeContextSymbol(double[][] shapeContextSymbol) {
		this.shapeContextSymbol = shapeContextSymbol;
	}


	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getVertexSize() {
		return vertexSize;
	}

	public void setVertexSize(double vertexSize) {
		this.vertexSize = vertexSize;
	}

	public int getStrokeId() {
		return strokeId;
	}

	public void setStrokeId(int strokeId) {
		this.strokeId = strokeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + strokeId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (strokeId != other.strokeId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vertex [p=" + p + ", id=" + id + ", strokeId=" + strokeId + "]";
	}

}