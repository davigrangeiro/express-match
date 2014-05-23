package br.usp.ime.escience.expressmatch.utils;

import java.util.List;

import br.usp.ime.escience.expressmatch.model.Expression;

public class StringUtils {

	public static String getArrayContents(List<? extends Expression> contents){
		StringBuilder res = new StringBuilder("{");
		for (int i = 0; i < contents.size()-1; i++) {
			res.append(contents.get(i)).append(",");
		}
		res.append(contents.get(contents.size()-1)).append("}");
		
		return res.toString();
	}
	
}
