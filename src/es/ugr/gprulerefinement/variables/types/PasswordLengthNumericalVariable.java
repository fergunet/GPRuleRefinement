package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.NumericalVariable;

public class PasswordLengthNumericalVariable extends NumericalVariable {

	public PasswordLengthNumericalVariable() {

		this.name = "password_length";
		this.min = 5;
		this.max = 7;
		this.value = this.getRandomValue();
	}

	@Override
	public Object clone() {
		PasswordLengthNumericalVariable a = new PasswordLengthNumericalVariable();
		a.value = this.value;
		return a;
	}

}
