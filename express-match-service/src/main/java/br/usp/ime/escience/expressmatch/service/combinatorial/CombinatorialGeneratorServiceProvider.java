package br.usp.ime.escience.expressmatch.service.combinatorial;

import org.springframework.stereotype.Service;

import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifier;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierRequest;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierResponse;

@Service
public class CombinatorialGeneratorServiceProvider{

	
	public void generatePermutations(int[] permutation, int i, int j, int[] values, SymbolClassifier symbolClassifier){
		if (i < permutation.length) {
			
			for (int k = j ; k < values.length; k++) {
				permutation[i] = values[k];
				generatePermutations(permutation, i+1, k+1, values, symbolClassifier);
			}
			
		} else {
			//Permutation found.
			
			SymbolClassifierRequest request = new SymbolClassifierRequest();
			SymbolClassifierResponse response = null;
			
			response = symbolClassifier.match(request);
		}
	}
	
	
//	private static Integer[] copy(int[] array){
//		Integer[] res = new Integer[array.length];
//		
//		for (int i = 0; i < array.length; i++) {
//			res[i] = Integer.valueOf(array[i]);
//		}
//		return res;
//	}
	
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

	public String printPermutation(int[] i) {
		StringBuilder s = new StringBuilder();
		for (int aux : i) {
			s.append(aux + " ");
		}
		return s.toString();
	}
	
}
