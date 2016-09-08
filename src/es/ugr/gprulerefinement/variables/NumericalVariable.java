package es.ugr.gprulerefinement.variables;

import java.util.Random;

public abstract class  NumericalVariable extends Variable {
	protected int min;
	protected int max;
	protected int sepId;
	protected String[]  seps = {"<",">","<=",">="};
	public String getRandomValue(){
	
		int randomNum = new Random().nextInt((max - min) + 1) + min;
		int index = new Random().nextInt(seps.length);
        return seps[index]+Integer.toString(randomNum);
		
	}


	@Override
	public abstract Object clone();
	
	public String toString(){
		return this.connector+" "+this.negation+" "+this.name+this.value;
	}
	
	

}
