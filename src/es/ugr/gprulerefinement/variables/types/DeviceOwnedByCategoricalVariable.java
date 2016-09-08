package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.CategoricalVariable;

public class DeviceOwnedByCategoricalVariable extends CategoricalVariable{

	public DeviceOwnedByCategoricalVariable(){
	
		this.name = "device_owned_by";
		
		this.possibleValues = new String[]{"USER",
		    "COMPANY"};
		
		this.value = this.getRandomValue();
		this.connector = this.getRandomConnector();
		this.negation = this.getRandomNegation();
	}

	@Override
	public Object clone() {
		
		DeviceOwnedByCategoricalVariable a = new DeviceOwnedByCategoricalVariable();
		a.value = this.value;
		
		return a;
	}




}