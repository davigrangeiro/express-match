package br.usp.ime.escience.expressmatch.service.combinatorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map; 
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import br.usp.ime.escience.expressmatch.model.Stroke;
import br.usp.ime.escience.expressmatch.model.Symbol;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierRequest;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierResponse;
import br.usp.ime.escience.expressmatch.service.symbol.classifier.SymbolClassifierService;

/**
 * The Class CombinatorialGeneratorServiceProvider.
 * 
 * @author Davi Grangeiro
 */
@Service
public class CombinatorialGeneratorServiceProvider{
	
	
	/**
	 * Gets the permutations result.
	 * 
	 * @param values the values
	 * @param permutationSize the permutation size
	 * @param symbolClassifier the symbol classifier
	 * @return the permutations result
	 *
	 */
	public List<SymbolClassifierResponse> getPermutationsResult(int[] values, int permutationSize, 
																SymbolClassifierService symbolClassifier, 
																Map<Integer, Stroke> strokeMap,
																Set<Symbol> modelSymbols,
																Stroke root){
		
		List<SymbolClassifierResponse> response = new ArrayList<>();

		int[] permutationArray = new int[permutationSize];
		generatePermutations(permutationArray, 0, 0, values, symbolClassifier, strokeMap, modelSymbols, response, root);
		
		return response;
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
	private void generatePermutations(int[] permutation, int i, int j, int[] values, 
									  SymbolClassifierService symbolClassifier, 
									  Map<Integer, Stroke> strokeMap,
									  Set<Symbol> modelSymbols,
									  List<SymbolClassifierResponse> response,
									  Stroke root){
		
		if (i < permutation.length && i < values.length) {
			
			for (int k = j ; k < values.length; k++) {
				permutation[i] = values[k];
				generatePermutations(permutation, i+1, k+1, values, symbolClassifier, strokeMap, modelSymbols, response, root);
			}
			
		} else {
			//Permutation found.
			
			if (strokeBelongToPermutation(permutation, root)) {
				
				//getting the list of strokes found by the permutation.
				List<Stroke> strokesForPermutation = new ArrayList<Stroke>();
				for (int strokeIndex : permutation) {
					strokesForPermutation.add(strokeMap.get(strokeIndex));
				}
				
				for (Symbol modelSymbol : modelSymbols) {
					
					//setting the attributes and send it to classifier.
					SymbolClassifierRequest<List<Stroke>> request = new SymbolClassifierRequest<>();
					request.setsTranscription(strokesForPermutation);
					request.setsModel(modelSymbol);
					
					SymbolClassifierResponse res = symbolClassifier.matchTranscription(request);
					//keep the permutation that was used to constitute the transcription hypothesis
					res.setPermutation(ArrayUtils.clone(permutation));
					res.setUsedSymbol(modelSymbol);
					
					//adding the answer to response
					response.add(res);
				}
			}
		}
	}


	private boolean strokeBelongToPermutation(int[] permutation, Stroke root) {
		//testing if the root stroke belong to permutation found.
		boolean rootStrokeBelongToPermutation = false;
		for (int strokeIndex = 0; strokeIndex < permutation.length && !rootStrokeBelongToPermutation ; strokeIndex++) {
			if (root.getStrokeId().intValue() == permutation[strokeIndex]) {
				rootStrokeBelongToPermutation = true;
			}
		}
		return rootStrokeBelongToPermutation;
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
