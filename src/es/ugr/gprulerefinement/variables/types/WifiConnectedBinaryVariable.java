package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.BinaryVariable;

public class WifiConnectedBinaryVariable extends BinaryVariable {

	public WifiConnectedBinaryVariable() {
		
		this.name = "wifiConnected";
		this.value = this.getRandomValue();
	}

	@Override
	public Object clone() {
		
		WifiConnectedBinaryVariable d = new WifiConnectedBinaryVariable();
		d.value = this.value;
		
		return d;
	}

}
