package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.BinaryVariable;

public class DeviceHasAntivirusBinaryVariable extends BinaryVariable {

	public DeviceHasAntivirusBinaryVariable() {
		this.name = "device_has_antivirus";
		this.value = this.getRandomValue();
		this.connector = this.getRandomConnector();
		this.negation = this.getRandomNegation();
	}
	@Override
	public Object clone() {
		DeviceHasAntivirusBinaryVariable d = new DeviceHasAntivirusBinaryVariable();
		d.value = this.value;
		return d;
		
	}

}
