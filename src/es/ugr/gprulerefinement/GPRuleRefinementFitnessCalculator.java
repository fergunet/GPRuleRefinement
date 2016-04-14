package es.ugr.gprulerefinement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
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

	@Override
	public Fitness calculateFitness(Individual ind) {
		TreeGenome tg = (TreeGenome) ind.getGenome();
		String treeString = writeTree(tg);
		
		boolean toMaximize = true;
		double theFitness = 0;
		
		/* Step 1: To retrieve patterns from DB
		 * Step 2: To look for conditions and compare values
		 * Step 3: To mark pattern as filtered by the rule or not
		 * */
		
		String[] rulesTree = treeString.split("\\s?\\n");
		List<String> rules = new ArrayList<String>(Arrays.asList(rulesTree));
		Instances initialInstances = new Instances(wekaInstances);
		
		for(int i = 0; i < rules.size(); i++) {
			System.out.println("RULE "+i+" "+initialInstances.size());
			String[] arraySides = rules.get(i).split("\\sTHEN=");
			List<String> sidesRule = new ArrayList<String>(Arrays.asList(arraySides));
			
			theFitness += (double)coveredPatterns(sidesRule.get(0), sidesRule.get(1), initialInstances);
			
		}	
		
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
				arbol = arbol + p.toString()+" AND ";
				p = p.getParent();
			}
			arbol = arbol + p.toString()+" ";
			arbol = arbol+action.toString();
			arbol = arbol+"\n";
		}
		
		return arbol;
		
	}
	
	public static int coveredPatterns(String conditionsRule, String label, Instances initialInstances){
		
		int covered = 0;
		
		String conditionFormat = "([\\w\\_]+)([\\=\\<\\>\\!\\s]+)(\\w+)\\s?A?N?D?";
		Pattern conditionPattern = Pattern.compile(conditionFormat);
		Matcher conditionMatcher = conditionPattern.matcher(conditionsRule);
		
		
		Instances auxInstances = initialInstances;
		int index = 0;
		
		for(int i = 0; i<initialInstances.size();i++){
			
			Instance patternInstance = null;
			patternInstance = initialInstances.get(i);
			int counter = 0;
			int fulfilled = 0;
			Attribute a = null;
			for (Enumeration<Attribute> e = patternInstance.enumerateAttributes(); e.hasMoreElements();) { // Got all pattern values separately
				a = e.nextElement();
				while (conditionMatcher.find()) { // Got a condition
					
					if (a.name().contentEquals(conditionMatcher.group(1))) {
						counter++;
						
						if (isInteger(conditionMatcher.group(3))) {
							if (conditionMatcher.group(2).contentEquals("=") && 
									conditionMatcher.group(3).contentEquals(patternInstance.toString(a))) {
								fulfilled++;
							} else if (conditionMatcher.group(2).contentEquals("<") && 
									Double.parseDouble(patternInstance.toString(a)) < Double.parseDouble(conditionMatcher.group(3))) {
								fulfilled++;
							} else if (conditionMatcher.group(2).contentEquals(">") && 
									Double.parseDouble(patternInstance.toString(a)) > Double.parseDouble(conditionMatcher.group(3))) {
								fulfilled++;
							}
						}
						
						String ruleValue = null;
						if (conditionMatcher.group(3).contentEquals("true")) {
							ruleValue = "1";
						} else if (conditionMatcher.group(3).contentEquals("false")) {
							ruleValue = "0";
						} else {
							ruleValue = conditionMatcher.group(3);
						}
						if (patternInstance.toString(a).contentEquals(ruleValue)) {
							fulfilled++;
						}
					}
				}
				conditionMatcher.reset();
				if (a.name().contentEquals("label")) {
					counter++;
					if (patternInstance.toString(a).contentEquals(label)) {
						fulfilled++;
					}
				}
			}
			if (fulfilled == counter) {
				covered++;
				auxInstances.remove(index);
			}
			index++;
		}
		initialInstances = new Instances(auxInstances);
		
		return covered;
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
