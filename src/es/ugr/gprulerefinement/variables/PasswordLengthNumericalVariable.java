package es.ugr.gprulerefinement.variables;

public class PasswordLengthNumericalType extends NumericalType{
	
	

	public PasswordLengthNumericalType() {
		this.name = "password_length";
		this.min = 5;
		this.max = 7;
	}
	
	
}
