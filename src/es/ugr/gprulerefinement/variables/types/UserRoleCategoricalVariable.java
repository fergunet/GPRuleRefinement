package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.CategoricalVariable;

public class UserRoleCategoricalVariable extends CategoricalVariable {

	public UserRoleCategoricalVariable() {

		this.name = "user_role";

		this.possibleValues = new String[] { "Employee", 
				"Consultancy", 
				"Development", 
				"Purchases", 
				"Security",
				"Administration", 
				"International" };

		this.value = this.getRandomValue();
	}

	@Override
	public Object clone() {

		UserRoleCategoricalVariable a = new UserRoleCategoricalVariable();
		a.value = this.value;

		return a;
	}

}