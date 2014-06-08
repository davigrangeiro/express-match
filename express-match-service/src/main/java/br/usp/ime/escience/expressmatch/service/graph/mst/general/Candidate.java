package br.usp.ime.escience.expressmatch.service.graph.mst.general;

/**
 * Candidates class, this class is used into a priority queue in prim's algorithm 
 * 
 * @author davi
 */
public class Candidate implements Comparable<Candidate> {

	public int index;
	public double distance;

	public Candidate(int i, double d) {
		index = i;
		distance = d;
	}

	public int compareTo(Candidate v) {
		if (distance < v.distance)
			return -1;
		else if (distance > v.distance)
			return 1;
		else
			return 0;
	}
	
}
