package es.ugr.gprulerefinement;


import es.ugr.gprulerefinement.variables.Action;
import es.ugr.gprulerefinement.variables.Variable;
import es.ugr.osgiliath.OsgiliathService;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.GenericTreeNode;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.TreeGenome;
import es.ugr.osgiliath.evolutionary.elements.Mutation;
import es.ugr.osgiliath.evolutionary.individual.Genome;



public class GPRuleRefinementMutation  extends OsgiliathService implements Mutation {

	@Override
	public Genome mutate(Genome genome) {
		TreeGenome tree = (TreeGenome) genome;
		//TODO clonar?
		
		GenericTreeNode node = tree.getRandomNode();
		//TODO confirmar que devuelve
		if(node.getData() instanceof Action && node.getChildren().size()!=0)
			System.out.println("NO DEBERIA TENER HIJOS!!!!");
		
		if(node.getData() instanceof Action){
			Action action = (Action) node.getData();
			action.mutate();
			
			
		
		}
		
		if(node.getData() instanceof Variable && node.getChildren().size()!=2)
			System.out.println("DECISION TIENE "+node.getChildren().size()+"!!!!");
		if(node.getData() instanceof Variable){
			Variable variable = (Variable) node.getData();
			if(Math.random()<0.5){
				//Change all
				variable = Variable.generateRandomVariable();
			}else{
				variable.mutateValue();
			}
			
		}
		
		return tree;
		
	}

}
