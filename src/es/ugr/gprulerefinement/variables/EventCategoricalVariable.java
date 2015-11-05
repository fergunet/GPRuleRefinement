package es.ugr.gprulerefinement.variables;

public class EventCategoricalVariable extends CategoricalVariable{

	EventCategoricalVariable(){
		
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
	}
}
