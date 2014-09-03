package br.usp.ime.escience.expressmatch.service.symbol.match;

import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Vertex;
import br.usp.ime.escience.expressmatch.service.graph.cost.ShapeContextCost;


public abstract class GraphMatching {

    protected Graph input;
    protected Graph model;
    
    protected Vertex[] modelVertex;
    protected Vertex[] inputVertex;
    
    protected ShapeContextCost cost;

    public GraphMatching() {
        this(null, null);
    }

    public GraphMatching(Graph model, Graph input) {
        this.model = model;
        this.input = input;
        
    }

    public abstract int[][] getMatch();

    protected float[][] getCostMatrix() {
        int tamanho = this.model.getVertexSize();
        if (tamanho < this.input.getVertexSize()) {
            tamanho = this.input.getVertexSize();
        }
        float[][] costMatrix = new float[tamanho][tamanho];
        modelVertex = model.getIndexedVertexes();
        inputVertex = input.getIndexedVertexes();
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (i < modelVertex.length && j < inputVertex.length) {
                    costMatrix[i][j] = this.cost.getCost(modelVertex[i], inputVertex[j]);
                } else {
                    costMatrix[i][j] = Float.MAX_VALUE;
                }
            }
        }
        return costMatrix;
    }

    public Graph getInput() {
        return input;
    }

    public void setInput(Graph input) {
        this.input = input;
    }

    public Graph getModel() {
        return model;
    }

    public void setModel(Graph model) {
        this.model = model;
    }

    public ShapeContextCost getCost() {
        return cost;
    }

    public void setCost(ShapeContextCost cost) {
        this.cost = cost;
    }
}
