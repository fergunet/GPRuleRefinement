package es.ugr.gprulerefinement.variables;

import java.util.Random;

public abstract class BinaryVariable extends Variable{

	@Override
	public String getRandomValue() {
		return Boolean.toString(new Random().nextBoolean());
		 
	}

	@Override
	public abstract Object clone();
	
	public String toString(){
		return this.name+"="+this.value;
	}

	
	
}
