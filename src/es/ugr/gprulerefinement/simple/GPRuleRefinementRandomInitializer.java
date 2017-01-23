package es.ugr.gprulerefinement.simple;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import es.ugr.gprulerefinement.GPRuleRefinementIndividual;
import es.ugr.gprulerefinement.variables.Action;
import es.ugr.gprulerefinement.variables.Variable;
import es.ugr.osgiliath.OsgiliathService;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.GenericTreeNode;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.ListGenome;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.TreeGenome;
import es.ugr.osgiliath.evolutionary.basiccomponents.individuals.BasicIndividual;
import es.ugr.osgiliath.evolutionary.elements.FitnessCalculator;
import es.ugr.osgiliath.evolutionary.individual.Fitness;
import es.ugr.osgiliath.evolutionary.individual.Gene;
import es.ugr.osgiliath.evolutionary.individual.Individual;
import es.ugr.osgiliath.evolutionary.individual.Initializer;

public class GPRuleRefinementRandomInitializer extends OsgiliathService implements Initializer{

		FitnessCalculator fc;
		@Override
		public ArrayList<Individual> initializeIndividuals(int size) {
			ArrayList<Individual> pop = new ArrayList<Individual>();
			
			for(int i = 0; i< size;i++){
				Individual ind = new GPRuleRefinementSimpleIndividual();
				ind.setGenome(generateRandomList());
				//((GPRuleRefinementSimpleIndividual)ind).setAction(Action.generateRandomAction()); //ESTO PARA EL FUTURO
				pop.add(ind);
			}
			
			System.out.println("CALCULANdO FITNESS DE LOS INIcIALES:"+pop.size());
			List<Fitness> fits = this.fc.calculateFitnessForAll(pop);
			System.out.println("CALCULADOS LOS FITNESS: "+fits.size());
			int w = 0;
			for(Fitness f:fits){
				pop.get(w).setFitness(f);
				w++;
			}
			return pop;
		}
		
		private ListGenome generateRandomList(){
			ListGenome lg = new ListGenome();
			
			ArrayList<Gene> genes = new ArrayList<Gene>();
			for(int i= 0; i<10; i++){
				genes.add(Variable.generateRandomVariable());
			}
			lg.setGenes(genes);
			return lg;
		}
		
		

		public void setFitnessCalculator(FitnessCalculator fitnessCalculator) {
			this.fc = fitnessCalculator;
			
		}

	}



