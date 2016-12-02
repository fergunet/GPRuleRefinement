package es.ugr.gprulerefinement;

import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.TreeGenome;
import es.ugr.osgiliath.evolutionary.basiccomponents.individuals.BasicIndividual;

public class GPRuleRefinementIndividual extends BasicIndividual{
	
	private double validationScore;
	private double classificationError;
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

}
