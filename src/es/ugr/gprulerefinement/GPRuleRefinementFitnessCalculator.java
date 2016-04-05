package es.ugr.gprulerefinement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import es.ugr.gprulerefinement.variables.Action;
import es.ugr.gprulerefinement.variables.Variable;
import es.ugr.osgiliath.OsgiliathService;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.GenericTreeNode;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.TreeGenome;
import es.ugr.osgiliath.evolutionary.basiccomponents.individuals.DoubleFitness;
import es.ugr.osgiliath.evolutionary.elements.FitnessCalculator;
import es.ugr.osgiliath.evolutionary.individual.Fitness;
import es.ugr.osgiliath.evolutionary.individual.Individual;
import eu.musesproject.server.db.handler.DBManager;
import eu.musesproject.server.entity.PatternsKrs;
import eu.musesproject.server.scheduler.ModuleType;

public class GPRuleRefinementFitnessCalculator  extends OsgiliathService implements FitnessCalculator{

	private static DBManager dbManager = new DBManager(ModuleType.KRS);
	
	@Override
	public Fitness calculateFitness(Individual ind) {
		TreeGenome tg = (TreeGenome) ind.getGenome();
		String treeString = writeTree(tg);
		
		/* Step 1: To retrieve patterns from DB
		 * Step 2: To look for conditions and compare values
		 * Step 3: To mark pattern as filtered by the rule or not
		 * */
		
		/* 1 */
		List<PatternsKrs> patternList = dbManager.getPatternsKRS();
		if (patternList.size()>0){
			
			
		} else {
			System.out.println("There are not patterns in the database");
		}
		
		System.out.println("INDIVIDUAL IS: "+treeString);
		System.out.println("END INDIVIDUAL");
		double theFitness = 0;
		boolean toMaximize = true;
		//Write the magic here
		
		
		
		
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
}
