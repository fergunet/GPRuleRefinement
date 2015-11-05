package es.ugr.gprulerefinement.variables;

import java.util.Random;

import es.ugr.osgiliath.evolutionary.individual.Gene;

public abstract class  Variable implements Gene {

	private String name;
	private String value;

	public static final String packageName = "es.ugr.gprulerefinement.variables";

	public static String availableTypes[] = new String[] { "EventCategoricalVariable",
			"PasswordLengthNumericalVariable" };

	/*private Variable(String name, String value) {
		this.name = name;
		this.value = value;
	}*/
	
	public  String getName(){
		return this.name;
	}
	
	public  String getValue(){
		return this.value;
	}

	public  void mutateValue() {
		this.value = this.getRandomValue();
	}

	public abstract String getRandomValue();

	public static Variable generateRandomVariable() {
		int idx = new Random().nextInt(availableTypes.length);
		Variable var = null;
		Class ct;
		try {
			ct = Class.forName(packageName + "." + availableTypes[idx]);

			Variable t = (Variable) ct.newInstance();
			t.mutateValue();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return var;

	}

	public default Object clone() {
		return new this.new (this.name, this.value); // In principle Variables
													// are inmutable (as Strings
													// are)
	}

}
