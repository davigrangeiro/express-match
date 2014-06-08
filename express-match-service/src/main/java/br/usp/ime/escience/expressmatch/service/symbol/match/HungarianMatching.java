package br.usp.ime.escience.expressmatch.service.symbol.match;

import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.service.symbol.match.assignment.AssignmentProblem;
import br.usp.ime.escience.expressmatch.service.symbol.match.assignment.HungarianAlgorithm;


public class HungarianMatching extends GraphMatching {

    public HungarianMatching(Graph model, Graph input) {
        super(model, input);
    }

    public int[][] getMatch() {
        AssignmentProblem ap = new AssignmentProblem(this.getCostMatrix());
        return ap.solve(new HungarianAlgorithm());
        /*int result[] = new int[match.length];
        for (int i = 0; i < result.length; i++) {
            result[match[i][1]] = match[i][0];
        }
        return result;*/

        /*Vertex[] modelVertex = model.getIndexedVertexes();
        Vertex[] inputVertex = input.getIndexedVertexes();

        medianX = (int) this.median(inputVertex, modelVertex, match, true);
        medianY = (int) this.median(inputVertex, modelVertex, match, false);*/
    }



    /*
    private float median(Vertex[] inputVertex, Vertex[] modelVertex, int[][] match, boolean isXDistance) {
        float mediana[] = new float[modelVertex.length];
        if (inputVertex.length < modelVertex.length) {
            mediana = new float[inputVertex.length];
        }

        int i = 0;
        for (int j = 0; j < match.length; j++) {
            if (match[j][0] < modelVertex.length && match[j][1] < inputVertex.length) {
                if (isXDistance) {
                    mediana[i] = (float) this.distanceX(inputVertex[match[j][1]], modelVertex[match[j][0]]);
                } else {
                    mediana[i] = (float) this.distanceY(inputVertex[match[j][1]], modelVertex[match[j][0]]);
                }
                i++;
            }
        }
        for (int k = 0; k < mediana.length; k++) {
            for (int j = k + 1; j < mediana.length; j++) {
                if (mediana[k] > mediana[j]) {
                    float temp = mediana[k];
                    mediana[k] = mediana[j];
                    mediana[j] = temp;
                }
            }
        }
        if (mediana.length % 2 == 0) {
            return (mediana[(mediana.length - 1) / 2] + mediana[((mediana.length - 1) / 2) + 1]) / 2;
        }
        return mediana[mediana.length / 2];
    }

    private double distanceX(Vertex input, Vertex model) {
        return input.getX() - model.getX();
    }

    private double distanceY(Vertex input, Vertex model) {
        return input.getY() - model.getY();
    }*/
}
