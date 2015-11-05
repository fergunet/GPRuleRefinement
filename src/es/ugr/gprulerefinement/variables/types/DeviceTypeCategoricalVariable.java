package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.CategoricalVariable;

public class DeviceTypeCategoricalVariable extends CategoricalVariable {

	public DeviceTypeCategoricalVariable() {

		this.name = "device_type";

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

		DeviceTypeCategoricalVariable a = new DeviceTypeCategoricalVariable();
		a.value = this.value;

		return a;
	}

}