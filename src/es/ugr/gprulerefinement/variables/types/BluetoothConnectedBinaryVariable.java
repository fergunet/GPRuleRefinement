package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.BinaryVariable;

public class BluetoothConnectedBinaryVariable extends BinaryVariable {

	public BluetoothConnectedBinaryVariable() {
		
		this.name = "bluetoothConnected";
		this.value = this.getRandomValue();
	}

	@Override
	public Object clone() {
		
		BluetoothConnectedBinaryVariable d = new BluetoothConnectedBinaryVariable();
		d.value = this.value;
		
		return d;
	}

}
