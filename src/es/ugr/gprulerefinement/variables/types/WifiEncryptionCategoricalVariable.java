package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.CategoricalVariable;

public class WifiEncryptionCategoricalVariable extends CategoricalVariable {

	public WifiEncryptionCategoricalVariable() {
		
		this.name = "wifiEncryption";
		
		this.possibleValues = new String[]{"WPA2-PSK-CCMP+TKIPWPSESS",
				"WPA2-PSK-CCMPESS",
				"WPA-PSK-CCMP+TKIPWPA2-PSK-CCMP+TKIP-preauthWPSESS",
				"WPA2-PSK-CCMP+TKIPESS",
				"WPA-PSK-TKIP+CCMPWPA2-PSK-TKIP+CCMPESS",
				"WPA-PSK-CCMPESS",
				"WPA2-PSK-CCMP-preauthESS",
				"WPA-PSK-CCMP+TKIPWPSESS",
				"WPA2-PSK-TKIP+CCMPWPSESS",
				"WPA-PSK-CCMPWPA2-PSK-CCMPESS",
				"ESS",
				"WPA2-PSK-CCMPWPSESS",
				"WPA-PSK-TKIPWPA2-PSK-CCMP-preauthESS",
				"WPA-PSK-TKIP+CCMPESS",
				"WPA-PSK-TKIP+CCMPWPSESS",
				"WPA2-PSK-TKIP+CCMPESS"};
		
		this.value = this.getRandomValue();
		this.connector = this.getRandomConnector();
		this.negation = this.getRandomNegation();
	}

	@Override
	public Object clone() {
		
		WifiEncryptionCategoricalVariable a = new WifiEncryptionCategoricalVariable();
		a.value = this.value;
		
		return a;
	}

}
