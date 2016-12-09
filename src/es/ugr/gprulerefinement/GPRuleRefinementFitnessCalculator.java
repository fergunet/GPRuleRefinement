package es.ugr.gprulerefinement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import es.ugr.gprulerefinement.variables.Action;
import es.ugr.osgiliath.OsgiliathService;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.GenericTreeNode;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.TreeGenome;
import es.ugr.osgiliath.evolutionary.basiccomponents.individuals.DoubleFitness;
import es.ugr.osgiliath.evolutionary.elements.FitnessCalculator;
import es.ugr.osgiliath.evolutionary.individual.Fitness;
import es.ugr.osgiliath.evolutionary.individual.Individual;

public class GPRuleRefinementFitnessCalculator  extends OsgiliathService implements FitnessCalculator{

	private static Instances wekaInstances = null;
	
	public GPRuleRefinementFitnessCalculator(Instances data) {
		GPRuleRefinementFitnessCalculator.wekaInstances = data;
	}

	public GPRuleRefinementFitnessCalculator() {
		
	}

	@Override
	public Fitness calculateFitness(Individual ind) {
		TreeGenome tg = (TreeGenome) ind.getGenome();
		String treeString = writeTree(tg);
		
		System.out.println("INDIVIDUAL IS: "+treeString);
		System.out.println("END INDIVIDUAL");
		boolean toMaximize = true;
		double theFitness = 0;
		double theAccuracy = 0;
		double classifError = 0;
		
		String instancesForFitness = (String) this.getAlgorithmParameters().getParameter(GPRuleRefinementParameters.DATASET_TRAINING_FILE);
		String instancesForAccuracy = (String) this.getAlgorithmParameters().getParameter(GPRuleRefinementParameters.DATASET_VALIDATION_FILE);

		DataSource sourceTraining, sourceTest;
		Instances dataTraining = null;
		Instances dataTest = null;
		try {
			sourceTraining = new DataSource(instancesForFitness);
			dataTraining = sourceTraining.getDataSet();
			sourceTest = new DataSource(instancesForAccuracy);
			dataTest = sourceTest.getDataSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (dataTraining.classIndex() == -1) {
			dataTraining.setClassIndex(dataTraining.numAttributes() - 1);
		}
		if (dataTest.classIndex() == -1) {
			dataTest.setClassIndex(dataTest.numAttributes() - 1);
		}
		
		String[] rulesTree = treeString.split("\\s?\\n");
		List<String> rules = new ArrayList<String>(Arrays.asList(rulesTree));
		Instances initialInstances = new Instances(dataTraining);
		Instances validationInstances = new Instances(dataTest);
		
		int depth = tg.getDepth();
		int numNodes = tg.getNumberOfNodes();
		
		for(int i = 0; i < rules.size(); i++) {
			System.out.print("RULE "+i+" "+initialInstances.size()+" ");
			String[] arraySides = rules.get(i).split("\\sTHEN=");
			List<String> sidesRule = new ArrayList<String>(Arrays.asList(arraySides));
			
			List<Double> coveredInst = new ArrayList<Double>();
			coveredInst = coveredPatterns(sidesRule.get(0), sidesRule.get(1), initialInstances, "fitness");
			List<Double> accuracyInst = new ArrayList<Double>();
			accuracyInst = coveredPatterns(sidesRule.get(0), sidesRule.get(1), validationInstances, "validation");
			
			double fc = coveredInst.get(0) + coveredInst.get(1) - (coveredInst.get(2) + coveredInst.get(3));
			double fs = (double)depth + (double)numNodes;
			
			theFitness += ((double)initialInstances.size() - fc) + 0.5*fs;
			theAccuracy += accuracyInst.get(0);
			classifError += accuracyInst.get(4);
			
			
		}		
		
		((GPRuleRefinementIndividual)ind).setValidationScore(theAccuracy);
		((GPRuleRefinementIndividual)ind).setClassificationError(classifError);
		
		return new DoubleFitness(new Double(theFitness), toMaximize);
	}

	@Override
	public ArrayList<Fitness> calculateFitnessForAll(ArrayList<Individual> inds) {
		ArrayList<Fitness> fits = new ArrayList<Fitness>();

		for (Individual ind : inds) {
			Fitness f = this.calculateFitness(ind);
			fits.add(f);
		}
		return fits;
	}

	public static String writeTree(TreeGenome tg){

		System.out.println(tg.toStringWithDepth());

		List<GenericTreeNode> leafs = new ArrayList<GenericTreeNode>();
		for(GenericTreeNode n:tg.getNodeList()){
			if(!n.hasChildren())
				if(!isBrotherInside(leafs,n))
					leafs.add(n);
				
				
		}
		String arbol = "";
		for(GenericTreeNode v:leafs){
			Action action = (Action)v.getData();
			GenericTreeNode p = v.getParent();
			while(p.getParent()!=null){
				arbol = arbol + p.toString()+" ";
				p = p.getParent();
			}
			arbol = arbol + p.toString()+" ";
			arbol = arbol+action.toString();
			arbol = arbol+"\n";
		}
		
		return arbol;
		
	}
	
	public static List<Double> coveredPatterns(String conditionsRule, String label, Instances initialInstances, String process){
		
		List<Double> params = new ArrayList<Double>();
		
		String conditionFormat = "(AND|OR)\\s(NOT)?\\s?([\\w\\_]+)([\\=\\<\\>\\!\\s]+)(\\w+)\\s?";
		Pattern conditionPattern = Pattern.compile(conditionFormat);
		Matcher conditionMatcher = conditionPattern.matcher(conditionsRule);
		
		
		Instances auxInstances = initialInstances;
		int index = 0;
		
		for(int i = 0; i<initialInstances.size();i++){
			
			Instance patternInstance = null;
			patternInstance = initialInstances.get(i);
			int counter = 0;
			int fulfilled = 0;
			int TP, TN, FP, FN, CE;
			TP = TN = FP = FN = CE = 0;
			Attribute att = null;
			for (Enumeration<Attribute> nextAtt = patternInstance.enumerateAttributes(); nextAtt.hasMoreElements();) { // Got all pattern values separately
				att = nextAtt.nextElement();
				while (conditionMatcher.find()) { // Got a condition
					
					if (att.name().contentEquals(conditionMatcher.group(3))) {
						if (conditionMatcher.group(1).equalsIgnoreCase("AND")) {
							counter++;
						}
						
						if (isInteger(conditionMatcher.group(5))) {
							if (conditionMatcher.group(2) != null && conditionMatcher.group(2).equalsIgnoreCase("NOT")) {
								if (conditionMatcher.group(4).contentEquals("=") && 
										!conditionMatcher.group(5).contentEquals(patternInstance.toString(att))) {
									fulfilled++;
								} else if (conditionMatcher.group(4).contentEquals("<") && 
										Double.parseDouble(patternInstance.toString(att)) >= Double.parseDouble(conditionMatcher.group(5))) {
									fulfilled++;
								} else if (conditionMatcher.group(4).contentEquals(">") && 
										Double.parseDouble(patternInstance.toString(att)) <= Double.parseDouble(conditionMatcher.group(5))) {
									fulfilled++;
								}
							} else {
								if (conditionMatcher.group(4).contentEquals("=") && 
										conditionMatcher.group(5).contentEquals(patternInstance.toString(att))) {
									fulfilled++;
								} else if (conditionMatcher.group(4).contentEquals("<") && 
										Double.parseDouble(patternInstance.toString(att)) < Double.parseDouble(conditionMatcher.group(5))) {
									fulfilled++;
								} else if (conditionMatcher.group(4).contentEquals(">") && 
										Double.parseDouble(patternInstance.toString(att)) > Double.parseDouble(conditionMatcher.group(5))) {
									fulfilled++;
								}
							}
						}
						
						String ruleValue = null;
						if (conditionMatcher.group(5).contentEquals("true")) {
							ruleValue = "1";
						} else if (conditionMatcher.group(5).contentEquals("false")) {
							ruleValue = "0";
						} else {
							ruleValue = conditionMatcher.group(5);
						}
						
						if (conditionMatcher.group(2) != null && conditionMatcher.group(2).equalsIgnoreCase("NOT")) {
							if (!patternInstance.toString(att).contentEquals(ruleValue)) {
								fulfilled++;
							}
						} else {
							if (patternInstance.toString(att).contentEquals(ruleValue)) {
								fulfilled++;
							}
						}
					}
				}
				conditionMatcher.reset();
				if (fulfilled >= counter && att.name().contentEquals("label")) {
					// Correctly detected instance
					if (process.contentEquals("fitness")){
						if (patternInstance.toString(att).equalsIgnoreCase("deny")){ // Negatives
							if (patternInstance.toString(att).contentEquals(label)){
								TN++; // True Negatives
							} else {
								FP++; // False Positives
							}
						} else {
							if (patternInstance.toString(att).contentEquals(label)){ // Positives
								TP++; // True Positives
							} else {
								FN++; // False Negatives
							}
						}
					} else {
						if (patternInstance.toString(att).contentEquals(label)) {
							TP++;
						} else {
							CE++;
						}
					}
					auxInstances.remove(index);
				}
			}
			index++;
			params.add((double)TP);
			params.add((double)TN);
			params.add((double)FP);
			params.add((double)FN);
			params.add((double)CE);
		}
		initialInstances = new Instances(auxInstances);
		
		return params;
	}
	
	public static boolean isInteger(String str) {
		
		String format = "^\\d+\\.?\\d*";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(str);
		
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isBrotherInside(List<GenericTreeNode> list, GenericTreeNode node){
		GenericTreeNode p = node.getParent();
		List<GenericTreeNode > childs = p.getChildren();
		for(GenericTreeNode c:childs){
			for(GenericTreeNode cand:list)
				if(c == cand)
					return true;
		}
		
		return false;
	}
}
