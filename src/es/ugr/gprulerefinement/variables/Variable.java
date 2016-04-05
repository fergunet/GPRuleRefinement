package es.ugr.gprulerefinement.variables;

import java.util.Random;

import es.ugr.osgiliath.evolutionary.individual.Gene;

public abstract class  Variable implements Gene {

	protected String name;
	protected String value;

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
			"WifiConnectedBinaryVariable"

	};

	
	public  String getName(){
		return this.name;
	}
	
	public  String getValue(){
		return this.value;
	}

	public  void mutateValue() {
		this.value = this.getRandomValue();
	}

	public abstract String getRandomValue();
	

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
