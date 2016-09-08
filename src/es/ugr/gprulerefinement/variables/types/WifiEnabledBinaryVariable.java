package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.BinaryVariable;

public class WifiEnabledBinaryVariable extends BinaryVariable {

	public WifiEnabledBinaryVariable() {
		
		this.name = "wifiEnabled";
		this.value = this.getRandomValue();
		this.connector = this.getRandomConnector();
		this.negation = this.getRandomNegation();
	}

	@Override
	public Object clone() {
		
		WifiEnabledBinaryVariable d = new WifiEnabledBinaryVariable();
		d.value = this.value;
		
		return d;
	}

}
