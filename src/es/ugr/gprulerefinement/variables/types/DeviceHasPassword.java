package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.BinaryVariable;

public class DeviceHasPassword extends BinaryVariable {

	public DeviceHasPassword() {
		this.name = "device_has_password";
		this.value = this.getRandomValue();
		this.connector = this.getRandomConnector();
		this.negation = this.getRandomNegation();
	}
	@Override
	public Object clone() {
		DeviceHasPassword d = new DeviceHasPassword();
		d.value = this.value;
		return d;
		
	}

}
