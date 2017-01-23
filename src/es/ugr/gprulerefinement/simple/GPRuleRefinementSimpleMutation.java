package es.ugr.gprulerefinement.simple;


import java.util.ArrayList;
import java.util.Random;

import es.ugr.gprulerefinement.variables.Action;
import es.ugr.gprulerefinement.variables.Variable;
import es.ugr.osgiliath.OsgiliathService;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.GenericTreeNode;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.ListGenome;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.TreeGenome;
import es.ugr.osgiliath.evolutionary.elements.Mutation;
import es.ugr.osgiliath.evolutionary.individual.Gene;
import es.ugr.osgiliath.evolutionary.individual.Genome;



public class GPRuleRefinementSimpleMutation  extends OsgiliathService implements Mutation {

	@Override
	public Genome mutate(Genome genome) {
		ListGenome listgenome = (ListGenome) genome;
		//TODO clonar?
		
		ArrayList<Gene> genelist = listgenome.getGeneList();
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(genelist.size());
        Gene chosen = genelist.get(index);

		


			Variable variable = (Variable) chosen;
			if(Math.random()<0.5){
				//Change all
				variable = Variable.generateRandomVariable();
			}else{
				variable.mutateValue();
			}
			
		
		
		return listgenome;
		
	}

}
