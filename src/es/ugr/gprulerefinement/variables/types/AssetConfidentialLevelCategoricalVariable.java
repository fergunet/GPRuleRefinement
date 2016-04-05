package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.CategoricalVariable;

public class AssetConfidentialLevelCategoricalVariable extends
		CategoricalVariable {

	public AssetConfidentialLevelCategoricalVariable() {
		
		this.name = "asset_confidential_level";
		
		this.possibleValues = new String[]{"PUBLIC",
				"CONFIDENTIAL"};
		
		this.value = this.getRandomValue();
	}

	@Override
	public Object clone() {
		
		AssetConfidentialLevelCategoricalVariable a = new AssetConfidentialLevelCategoricalVariable();
		a.value = this.value;
		
		return a;
	}

}
