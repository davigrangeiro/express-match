package br.usp.ime.escience.expressmatch.service.combinatorial;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierRequest;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierResponse;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierService;

/**
 * The Class CombinatorialGeneratorServiceProvider.
 * 
 * @author Davi Grangeiro
 */
public class CombinatorialGeneratorServiceProvider{
	
	/** The response. */
	List<SymbolClassifierResponse> response;
	
	/**
	 * Gets the permutations result.
	 * 
	 * @param values the values
	 * @param permutationSize the permutation size
	 * @param symbolClassifier the symbol classifier
	 * @return the permutations result
	 */
	public List<SymbolClassifierResponse> getPermutationsResult(int[] values, int permutationSize, SymbolClassifierService symbolClassifier){
		
		//cleanning response before returning the list of responses
		this.response = new ArrayList<>();

		int[] permutationArray = new int[permutationSize];
		generatePermutations(permutationArray, 0, 0, values, symbolClassifier);
		
		return this.response;
	}
	
	
	/**
	 * Generate permutations.
	 *
	 * @param permutation the permutation
	 * @param i the i
	 * @param j the j
	 * @param values the values
	 * @param symbolClassifier the symbol classifier
	 */
	private void generatePermutations(int[] permutation, int i, int j, int[] values, SymbolClassifierService symbolClassifier){
		if (i < permutation.length) {
			
			for (int k = j ; k < values.length; k++) {
				permutation[i] = values[k];
				generatePermutations(permutation, i+1, k+1, values, symbolClassifier);
			}
			
		} else {
			//Permutation found.
			
			SymbolClassifierRequest request = new SymbolClassifierRequest();
			SymbolClassifierResponse res = symbolClassifier.matchTranscription(request);
			res.setPermutation(ArrayUtils.clone(permutation));
			
			this.response.add(res);
		}
	}
	
	

	
//	public static void main(String[] args) throws InterruptedException {
//		List results = new ArrayList<>();
//		
//		for (int j = 1; j <= 6; j++) {
//		Long n = System.nanoTime();
//		int[] res = new int[j];
//		int[] values = new int[60];
//		
//		for (int i = 1; i < 31; i++) {
//			values[i-1] = i;
//		}
//		
//		generatePermutations(results, res, 0, 0, values);
//		System.out.println(String.format("C(60, %d): levou  %d nSeconds para gerar.",j, (System.nanoTime() - n)));
//		}
//		
////		for (int[] i : results) {
////			printPermutation(i);
////		}
//		
//	}
//
//	public String printPermutation(int[] i) {
//		StringBuilder s = new StringBuilder();
//		for (int aux : i) {
//			s.append(aux + " ");
//		}
//		return s.toString();
//	}
	
}
