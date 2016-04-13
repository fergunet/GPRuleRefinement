package es.ugr.gprulerefinement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import es.ugr.gprulerefinement.variables.Action;
import es.ugr.gprulerefinement.variables.Variable;
import es.ugr.osgiliath.OsgiliathService;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.GenericTreeNode;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.TreeGenome;
import es.ugr.osgiliath.evolutionary.basiccomponents.individuals.DoubleFitness;
import es.ugr.osgiliath.evolutionary.elements.FitnessCalculator;
import es.ugr.osgiliath.evolutionary.individual.Fitness;
import es.ugr.osgiliath.evolutionary.individual.Individual;
import eu.musesproject.server.entity.PatternsKrs;

public class GPRuleRefinementFitnessCalculator  extends OsgiliathService implements FitnessCalculator{

	private static Instances wekaInstances = null;
	
	public GPRuleRefinementFitnessCalculator(Instances data) {
		GPRuleRefinementFitnessCalculator.wekaInstances = data;
	}

	@Override
	public Fitness calculateFitness(Individual ind) {
		TreeGenome tg = (TreeGenome) ind.getGenome();
		String treeString = writeTree(tg);
		
		System.out.println("INDIVIDUAL IS: "+treeString);
		System.out.println("END INDIVIDUAL");
		boolean toMaximize = true;
		double theFitness = 0;
		
		/* Step 1: To retrieve patterns from DB
		 * Step 2: To look for conditions and compare values
		 * Step 3: To mark pattern as filtered by the rule or not
		 * */
		
		String[] rulesTree = treeString.split("\\s?\\n");
		List<String> rules = new ArrayList<String>(Arrays.asList(rulesTree));
		
		for(int i = 0; i < rules.size(); i++) {
			String[] arraySides = rules.get(i).split("\\sTHEN=");
			List<String> sidesRule = new ArrayList<String>(Arrays.asList(arraySides));
			
			if ((double)coveredPatterns(sidesRule.get(0)) > theFitness) {
				theFitness += (double)coveredPatterns(sidesRule.get(0));
			}
			
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
	
	public static int coveredPatterns(String conditionsRule){
		
		int covered = 0;
		
		String conditionFormat = "([\\w\\_]+)([\\=\\<\\>\\!\\s]+)(\\w+)\\s?A?N?D?";
		Pattern conditionPattern = Pattern.compile(conditionFormat);
		Matcher conditionMatcher = conditionPattern.matcher(conditionsRule);
		
		Iterator<Instance> it = wekaInstances.iterator(); // Got the patterns
		while(it.hasNext()){
			Instance patternInstance = it.next(); // Got one pattern
			int counter = 0;
			int fulfilled = 0;
			Attribute a = null;
			for (Enumeration<Attribute> e = patternInstance.enumerateAttributes(); e.hasMoreElements();) { // Got all pattern values separately
				a = e.nextElement();
				while (conditionMatcher.find()) { // Got a condition
					if (a.name().contentEquals(conditionMatcher.group(1))) {
						counter++;					
						String ruleValue = null;
						if (conditionMatcher.group(3).contentEquals("true")) {
							ruleValue = "1";
						} else if (conditionMatcher.group(3).contentEquals("false")) {
							ruleValue = "0";
						} else {
							ruleValue = conditionMatcher.group(3);
						}
						String patternValue = patternInstance.toString(a);
						if (patternValue.contentEquals(ruleValue)) {
							fulfilled++;
						}
					}
				}
				conditionMatcher.reset();
			}
			if (fulfilled == counter) {
				covered++;
			}
		}
		
		return covered;
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
