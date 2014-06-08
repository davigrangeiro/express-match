/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.usp.ime.escience.expressmatch.service.graph.cost;

import br.usp.ime.escience.expressmatch.model.graph.Vertex;


/**
 *
 * @author Willian
 */
public class ShapeContextCost {
	
     public float getCost(Vertex vm, Vertex vi){
         return vm.compareShapeContextExpression(vi);
     }
     
}
