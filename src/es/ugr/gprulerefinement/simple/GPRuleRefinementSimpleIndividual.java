package es.ugr.gprulerefinement.simple;

import es.ugr.gprulerefinement.variables.Action;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.TreeGenome;
import es.ugr.osgiliath.evolutionary.basiccomponents.individuals.BasicIndividual;

public class GPRuleRefinementSimpleIndividual extends BasicIndividual{
	
	private double validationScore;
	private double classificationError;
	
	private Action a;
	public void setValidationScore(double score){
		this.validationScore=score;
	}
	
	public double getValidationScore(){
		return this.validationScore;
	}
	
	public double getClassificationError() {
		return classificationError;
	}
	
	public void setClassificationError(double classificationError) {
		this.classificationError = classificationError;
	}
	
	public void setAction(Action a){
		this.a = a;
	}
	
	public Action getAction(Action a){
		return this.a;
	}

}
