package es.ugr.gprulerefinement.variables;

import java.util.Random;

public abstract class CategoricalVariable extends Variable{

	protected String[] possibleValues;
	
	public String getRandomValue(){
		int idx = new Random().nextInt(possibleValues.length);
		return possibleValues[idx];
	}
	
	public String toString(){
		return this.getName()+"=>"+this.getValue();
	}
	

	




}
