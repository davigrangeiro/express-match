
package br.usp.ime.escience.expressmatch.service.graph.cost;

import br.usp.ime.escience.expressmatch.model.graph.Graph;
import br.usp.ime.escience.expressmatch.model.graph.Vertex;
import br.usp.ime.escience.expressmatch.service.symbol.match.GraphMatching;
import br.usp.ime.escience.expressmatch.service.symbol.match.HungarianMatching;


/**
 *
 * @author Willian
 */
public class CostShapeContextSymbolMatch extends ShapeContextCost {
   
	GraphMatching gm;

    public float getCost(Vertex vm, Vertex vi){
        Graph graphModelSC = new Graph();
        Graph graphInputSC = new Graph();
        double[][] scModel = vm.getShapeContextSymbol();
        double[][] scInput = vi.getShapeContextSymbol();
        
        for (int i = 0; i < scModel.length; i++) {
            Vertex v = new Vertex(i, -1, -1);
            v.setShapeContextExpression(scModel[i]);
            graphModelSC.addVertex(v);
        }
        for (int i = 0; i < scInput.length; i++) {
            Vertex v = new Vertex(i, -1, -1);
            v.setShapeContextExpression(scInput[i]);
            graphInputSC.addVertex(v);
        }      
        this.gm = new HungarianMatching(graphModelSC, graphInputSC);
        this.gm.setCost(new ShapeContextCost());
        int[][] match = this.gm.getMatch();
        
        float totalCost = 0;
        Vertex[] vertexModel = graphModelSC.getIndexedVertexes();
        Vertex[] vertexInput = graphInputSC.getIndexedVertexes();
        
        for (int i = 0; i < match.length; i++) {
            totalCost += vertexModel[match[i][0]].compareShapeContextExpression(vertexInput[match[i][1]]);
        }
        return totalCost/match.length;
    }
}
