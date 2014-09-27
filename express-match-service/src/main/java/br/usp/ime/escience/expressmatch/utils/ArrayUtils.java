package br.usp.ime.escience.expressmatch.utils;

public class ArrayUtils {

	public static int[] copy(int[] array){
		int[] res = new int[array.length];
		
		for (int i = 0; i < array.length; i++) {
			res[i] = array[i];
		}
		return res;
	}
	
	
	public static String printPermutation(int[] i) {
		StringBuilder s = new StringBuilder();
		for (int aux : i) {
			s.append(aux + " ");
		}
		return s.toString();
	}

}
