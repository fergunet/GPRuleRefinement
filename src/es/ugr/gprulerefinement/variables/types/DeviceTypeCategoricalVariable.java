package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.CategoricalVariable;

public class DeviceTypeCategoricalVariable extends CategoricalVariable{

	public DeviceTypeCategoricalVariable(){
	
		this.name = "device_type";
		
		this.possibleValues = new String[]{"Motorola Moto G",
		    "'BQ Aquaris E5HD'",
		    "'Samsung Galaxy Grand Neo (GT-I'",
		    "'Samsung Galaxy S4 mini GT-I919'",
		    "'Samsung Galaxy S4 GT-I9506'",
		    "'Samsung Galaxy S3'",
		    "'Samsung Galaxy S4'",
		    "'LG-P760'",
		    "'Nexus 5'",
		    "'LG-L70'",
		    "'OnePlus One'",
		    "'Sony Xperia T3'",
		    "'LG-G3'",
		    "'Sony Xperia T'",
		    "'BQ Aquaris E4.5'",
		    "'Samsung Galaxy Trend Plus'",
		    "'PC'",
		    "1222"};
		
		this.value = this.getRandomValue();
		this.connector = this.getRandomConnector();
		this.negation = this.getRandomNegation();
	}

	@Override
	public Object clone() {
		
		DeviceTypeCategoricalVariable a = new DeviceTypeCategoricalVariable();
		a.value = this.value;
		
		return a;
	}




}