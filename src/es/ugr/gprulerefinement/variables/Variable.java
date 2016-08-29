package es.ugr.gprulerefinement.variables;

import java.util.Random;

import es.ugr.osgiliath.evolutionary.individual.Gene;

public abstract class  Variable implements Gene {

	protected String name;
	protected String value;
	protected String connector = "";
	protected String negation = "";

	public static final String packageName = "es.ugr.gprulerefinement.variables.types";

	public static String availableTypes[] = new String[] { 
			"DeviceHasAntivirusBinaryVariable",
			"DeviceHasPassword",
			"DeviceOwnedByCategoricalVariable",
			"DeviceScreenTimeoutNumericalVariable",
			"UserRoleCategoricalVariable",
			"EventLevelCategoricalVariable",
			"EventTypeCategoricalVariable",
			"PasswordLengthNumericalVariable",
			"DeviceTypeCategoricalVariable",
			"DeviceOSCategoricalVariable",
			"AssetConfidentialLevelCategoricalVariable",
			"WifiEncryptionCategoricalVariable",
			"DeviceHasAccessibilityBinaryVariable",
			"DeviceIsRootedBinaryVariable",
			"MailHasAttachmentBinaryVariable",
			"WifiEnabledBinaryVariable",
			"WifiConnectedBinaryVariable",
			"BluetoothConnectedBinaryVariable"

	};

	
	public  String getName(){
		return this.name;
	}
	
	public  String getValue(){
		return this.value;
	}

	public String getConnector() {
		return this.connector;
	}

	public String getNegation() {
		return this.negation;
	}

	public  void mutateValue() {
		this.value = this.getRandomValue();
	}

	public abstract String getRandomValue();
	
	public String getRandomNegation(){
		String[] possibleValues = new String[2];
		possibleValues[0] = "NOT";
		possibleValues[1] = "";
		int idx = new Random().nextInt(possibleValues.length);
		return possibleValues[idx];
	}
	
	public String getRandomConnector(){
		String[] possibleValues = new String[2];
		possibleValues[0] = "AND";
		possibleValues[1] = "OR";
		int idx = new Random().nextInt(possibleValues.length);
		return possibleValues[idx];		
	}

	public static Variable generateRandomVariable() {
		int idx = new Random().nextInt(availableTypes.length);
		Variable var = null;
		Class ct;
		try {
			ct = Class.forName(packageName + "." + availableTypes[idx]);

			Object o = ct.newInstance();
			var = (Variable) o;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return var;

	}

    public abstract Object clone();

}
