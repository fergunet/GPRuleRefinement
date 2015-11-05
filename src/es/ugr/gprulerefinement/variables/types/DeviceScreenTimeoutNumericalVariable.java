package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.NumericalVariable;

public class DeviceScreenTimeoutNumericalVariable extends NumericalVariable {

	public DeviceScreenTimeoutNumericalVariable() {

		this.name = "device_screen_timeout";
		this.min = 0;
		this.max = 1800;
		this.value = this.getRandomValue();
	}

	@Override
	public Object clone() {
		DeviceScreenTimeoutNumericalVariable a = new DeviceScreenTimeoutNumericalVariable();
		a.value = this.value;
		return a;
	}

}