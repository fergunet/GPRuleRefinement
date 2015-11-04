package es.ugr.gprulerefinement.variables;

import java.util.Random;

import es.ugr.osgiliath.evolutionary.individual.Gene;

public class Variable implements Gene{
	
	public Type type;
	public String value;
	public static final String packageName = "es.ugr.gpurulerefinement.variables";
	
	public static String availableTypes[] = new String[]{
			"EventCategoricalType",
			"PasswordLengthNumericalType"
	};
	
	private Variable(Type type, String value){
		this.type = type;
		this.value = value;
	}
	
	public void mutateType(){}
	
	public void mutateValue(){
		this.value = this.type.getRandomValue();
	}
	
	public static Variable generateRandomVariable() throws Exception{
		int idx = new Random().nextInt(availableTypes.length);
		Class ct = Class.forName(packageName+"."+availableTypes[idx]);
		Type t = (Type) ct.newInstance();
		String v = t.getRandomValue();
		return new Variable(t, v);
	   
	}
	
	
	public String toString(){
		return type.getName()+" "+this.value;
	}
	
	public Object clone(){
		return new Variable(this.type, this.value); //In principle Variables are inmutable (as Strings are)
	}
	

}
