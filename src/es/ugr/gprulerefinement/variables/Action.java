package es.ugr.gprulerefinement.variables;

import java.util.Random;

import es.ugr.osgiliath.evolutionary.individual.Gene;

public class Action implements Gene{
	public int value;
	public static final String[] possibleValues = new String[]{"GRANTED","STRONGDENY"}; 
	

	private Action(int value){
		this.value = value;
	}
	//TODO deal with the Random() stuff
	public static Action generateRandomAction(){
		int v = (new Random().nextBoolean())?1:0;
			
		return new Action(v);
	}
	public String getValue(){
		return possibleValues[value];
	}
	public String toString(){
		return ("THEN="+this.getValue());
	}
	
	public void mutate(){
		value= (value==0)?1:0;
	}
	public Object clone(){
		return new Action(this.value);
	}
}
