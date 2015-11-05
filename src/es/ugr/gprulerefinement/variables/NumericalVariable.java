package es.ugr.gprulerefinement.variables;

import java.util.Random;

public abstract class  NumericalType extends Type {
	int min;
	int max;
	
	public String getRandomValue(){
	
		int randomNum = new Random().nextInt((max - min) + 1) + min;
		return Integer.toString(randomNum);
	}
	
	
	

}
