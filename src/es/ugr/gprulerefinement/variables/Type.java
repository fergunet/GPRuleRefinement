package es.ugr.gprulerefinement.variables;

public abstract class Type {

	String name;
	
	public  String getName(){
		return this.name;
	}
	
	public abstract String getRandomValue();
}
