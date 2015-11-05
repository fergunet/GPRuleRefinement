package es.ugr.gprulerefinement.variables;

import java.util.Random;

public abstract class CategoricalType extends Type{

	String[] possibleValues;
	
	public String getRandomValue(){
		int idx = new Random().nextInt(possibleValues.length);
		return possibleValues[idx];
	}
	


}
