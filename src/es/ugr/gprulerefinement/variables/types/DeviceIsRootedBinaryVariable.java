package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.BinaryVariable;

public class DeviceIsRootedBinaryVariable extends BinaryVariable {

	public DeviceIsRootedBinaryVariable() {
		
		this.name = "device_is_rooted";
		this.value = this.getRandomValue();
		
	}

	@Override
	public Object clone() {
		
		DeviceIsRootedBinaryVariable d = new DeviceIsRootedBinaryVariable();
		d.value = this.value;
		
		return d;
	}

}
