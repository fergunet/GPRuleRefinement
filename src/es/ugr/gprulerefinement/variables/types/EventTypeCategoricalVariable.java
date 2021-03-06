package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.CategoricalVariable;

public class EventTypeCategoricalVariable extends CategoricalVariable{

	public EventTypeCategoricalVariable(){
	
		this.name = "event_type";
		
		this.possibleValues = new String[]{"LOG_IN",
		    "SECURITY_PROPERTY_CHANGED",
			"CONTEXT_SENSOR_DEVICE_PROTECTION",
			"CONTEXT_SENSOR_CONNECTIVITY",
			"ACTION_APP_OPEN",
			"CONTEXT_SENSOR_PACKAGE",
			"CONTEXT_SENSOR_APP",
			"LOG_OUT",
			"user_entered_password_field",
			"USER_BEHAVIOR",
			"ACTION_REMOTE_FILE_ACCESS",
			"CONTEXT_SENSOR_PERIPHERAL"};
		
		this.value = this.getRandomValue();
		this.connector = this.getRandomConnector();
		this.negation = this.getRandomNegation();
	}

	@Override
	public Object clone() {
		
		EventTypeCategoricalVariable a = new EventTypeCategoricalVariable();
		a.value = this.value;
		
		return a;
	}




}
