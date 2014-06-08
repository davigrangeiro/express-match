package br.usp.ime.escience.expressmatch.service.graph.cost;

public interface Cost<Type, ReturnType> {

	ReturnType getCost(Type ti, Type tj);
	
}
