package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.CategoricalVariable;

public class DeviceOSCategoricalVariable extends CategoricalVariable {

	public DeviceOSCategoricalVariable() {
		
		this.name = "device_OS";
		
		this.possibleValues = new String[]{"Android4.4.4",
				"Android4.2.1",
				"Android4.4.2",
				"Android4.2.2",
				"Android4.2",
				"Android4.1.2",
				"Android4.4",
				"Android5.1",
				"Android4.5.1",
				"Android4.3",
				"Android4.1.1",
				"Android0",
				"Windows8.1",
				"Windows7 Professional",
				"Windows8.1 Professional",
				"Windows7 Home Edition"};
	}

	@Override
	public Object clone() {
		
		DeviceOSCategoricalVariable a = new DeviceOSCategoricalVariable();
		a.value = this.value;
		
		return a;
	}

}
