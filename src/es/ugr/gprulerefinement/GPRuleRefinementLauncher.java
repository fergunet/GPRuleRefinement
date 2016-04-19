package es.ugr.gprulerefinement;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import javax.xml.crypto.Data;

import weka.core.Instances;
import es.osgiliath.evolutionary.basicimplementations.combinators.BasicOrderRecombinator;
import es.osgiliath.evolutionary.basicimplementations.fitnesscalculators.BasicDistributedFitnessCalculator;
import es.osgiliath.evolutionary.basicimplementations.mutators.BasicOrderMutator;
import es.osgiliath.evolutionary.basicimplementations.populations.ListPopulation;
import es.osgiliath.evolutionary.basicimplementations.selectors.DeterministicTournamentSelection;
import es.ugr.osgiliath.algorithms.AlgorithmParameters;
import es.ugr.osgiliath.evolutionary.EvolutionaryAlgorithm;
import es.ugr.osgiliath.evolutionary.basicimplementations.stopcriterions.NGenerationsStopCriterion;
import es.ugr.osgiliath.evolutionary.elements.Mutator;
import es.ugr.osgiliath.evolutionary.elements.ParentSelector;
import es.ugr.osgiliath.evolutionary.elements.Population;
import es.ugr.osgiliath.evolutionary.elements.Recombinator;
import es.ugr.osgiliath.evolutionary.elements.Replacer;
import es.ugr.osgiliath.evolutionary.elements.StopCriterion;
import es.ugr.osgiliath.evolutionary.individual.Initializer;
import es.ugr.osgiliath.problem.Problem;
import es.ugr.osgiliath.problem.ProblemParameters;
import es.ugr.osgiliath.util.impl.HashMapParameters;
import es.ugr.osgiliath.utils.OsgiliathConfiguration;
import es.ugr.osgiliath.utils.Stopwatch;
import eu.musesproject.server.dataminer.DataMiner;
import eu.musesproject.server.db.handler.DBManager;
import eu.musesproject.server.entity.PatternsKrs;
import eu.musesproject.server.scheduler.ModuleType;

public class GPRuleRefinementLauncher {
	
	private static DBManager dbManager = new DBManager(ModuleType.KRS);
	private static DataMiner dm = new DataMiner();

	public GPRuleRefinementLauncher() {

	}

	public void launchAlgorithm(String fileprops, String datasetTrainingFile) {
		Stopwatch sw = new Stopwatch();
		sw.start();
		// Algorithm and parameters
		EvolutionaryAlgorithm algo = new EvolutionaryAlgorithm();
		AlgorithmParameters params = new HashMapParameters();
		Properties defaultProps = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(fileprops);
			defaultProps.load(in);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		params.setup(defaultProps);
		params.updateParameter(GPRuleRefinementParameters.DATASET_TRAINING_FILE, datasetTrainingFile);
		/*
		 * String library = (String)
		 * params.getParameter("parameters.osgiliart.library.opencv");
		 * System.load(library);
		 */

		Problem problem = new GPRuleRefinementProblem();
		ProblemParameters problemParams = new HashMapParameters();
		problem.setProblemParameters(problemParams);

		// LOGGER
		GPRuleRefinementLogger logger = new GPRuleRefinementLogger();
		logger.setAlgorithmParameters(params);
		logger.setup(null);

		BasicDistributedFitnessCalculator fitnessCalculator;

		// FITNESS CALCULATOR

		fitnessCalculator = new BasicDistributedFitnessCalculator();		
		List<PatternsKrs> patternList = dbManager.getPatternsKRS();
		Instances wekaInstances = dm.buildInstancesFromPatterns(patternList);
		int numThreads = (Integer) params.getParameter(OsgiliathConfiguration.NUM_THREADS);
		for (int i = 0; i < numThreads; i++) {
			GPRuleRefinementFitnessCalculator pwfc = new GPRuleRefinementFitnessCalculator(wekaInstances);
			pwfc.setAlgorithmParameters(params);
			fitnessCalculator.addFitnessCalculator(pwfc);
		}
		// Population and Initializer
		Population pop = new ListPopulation();
		Initializer init = new GPRuleRefinementRandomInitializer();
		((GPRuleRefinementRandomInitializer) init).setAlgorithmParameters(params);
		((GPRuleRefinementRandomInitializer) init).setProblem(problem);
		((GPRuleRefinementRandomInitializer) init).setFitnessCalculator(fitnessCalculator);
		((ListPopulation) pop).setInitializer(init);
		((ListPopulation) pop).setAlgorithmParameters(params);
		((ListPopulation) pop).setProblem(problem);
		algo.setPopulation(pop);

		// PARENT SELECTOR
		ParentSelector parentSelector = new DeterministicTournamentSelection();

		((DeterministicTournamentSelection) parentSelector).setAlgorithmParameters(params);
		((DeterministicTournamentSelection) parentSelector).setProblem(problem);
		algo.setParentSelector(parentSelector);

		// CROSSOVER
		GPRuleRefinementCrossover crossover = new GPRuleRefinementCrossover();
		crossover.setAlgorithmParameters(params);

		// RECOMBINATOR
		Recombinator recombinator = new BasicOrderRecombinator();
		((BasicOrderRecombinator) recombinator).setProblem(problem);
		((BasicOrderRecombinator) recombinator).setAlgorithmParameters(params);
		((BasicOrderRecombinator) recombinator).setCrossover(crossover);
		algo.setRecombinator(recombinator);

		// MUTATION
		GPRuleRefinementMutation mutation = new GPRuleRefinementMutation();
		mutation.setAlgorithmParameters(params);

		// MUTATOR
		Mutator mutator = new BasicOrderMutator();
		((BasicOrderMutator) mutator).setAlgorithmParameters(params);
		((BasicOrderMutator) mutator).setMutation(mutation);
		algo.setMutator(mutator);

		// STOP CRITERION
		StopCriterion stopCriterion = new NGenerationsStopCriterion();
		((NGenerationsStopCriterion) stopCriterion).setAlgorithmParameters(params);
		((NGenerationsStopCriterion) stopCriterion).setProblem(problem);
		algo.setStopCriterion(stopCriterion);

		// REPLACER
		Replacer replacer = new GPRuleRefinementReplacer();
		((GPRuleRefinementReplacer) replacer).setFitnessCalculator(fitnessCalculator);
		((GPRuleRefinementReplacer) replacer).setLogger(logger);
		algo.setReplacer(replacer);

		algo.setLogger(logger);
		// problem.getParameters().setup(null);
		sw.stop();
		String time = sw.toString();
		sw.start();
		algo.start();
		System.out.println("[" + algo.getObtainedSolution() + "]");
		sw.stop();
		time = time + ":" + sw.toString();
		System.out.println(time);

	}

	public static void main(String[] args) {
		GPRuleRefinementLauncher pwl = new GPRuleRefinementLauncher();
		String fileProps = args[0];
		int numTimes = Integer.parseInt(args[1]);
		String dataset = args[2];
		for (int i = 0; i < numTimes; i++) {
			pwl.launchAlgorithm(fileProps,dataset);
		}

		System.out.println("EXIT");
		System.exit(0);

	}

}
