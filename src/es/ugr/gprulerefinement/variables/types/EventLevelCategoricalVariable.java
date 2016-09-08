package es.ugr.gprulerefinement.variables.types;

import es.ugr.gprulerefinement.variables.CategoricalVariable;

public class EventLevelCategoricalVariable extends CategoricalVariable{

	public EventLevelCategoricalVariable(){
	
		this.name = "event_level";
		
		this.possibleValues = new String[]{"SIMPLE_EVENT",
		    "COMPLEX_EVENT"};
		
		this.value = this.getRandomValue();
		this.connector = this.getRandomConnector();
		this.negation = this.getRandomNegation();
	}

	@Override
	public Object clone() {
		
		EventLevelCategoricalVariable a = new EventLevelCategoricalVariable();
		a.value = this.value;
		
		return a;
	}




}