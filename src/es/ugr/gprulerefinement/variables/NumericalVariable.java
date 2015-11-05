package es.ugr.gprulerefinement.variables;

import java.util.Random;

public abstract class  NumericalVariable extends Variable {
	protected int min;
	protected int max;
	
	public String getRandomValue(){
	
		int randomNum = new Random().nextInt((max - min) + 1) + min;
		return Integer.toString(randomNum);
	}

	@Override
	public abstract Object clone();
	
	public String toString(){
		return this.name+">"+this.value;
	}
	
	

}
