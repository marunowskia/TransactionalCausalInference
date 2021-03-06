package oss.marunowskia.datamining.transactionalcausalinference.utilities.sequences;

import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.TreeSet;



import org.apache.log4j.Logger;

import oss.marunowskia.datamining.transactionalcausalinference.models.AssociationRuleNode;

public class AssociationRuleReportingUtility {

	private static Logger logger = Logger.getLogger(AssociationRuleReportingUtility.class);
	
	public static void reportAssociationRuleStatistics(Hashtable<Long, AssociationRuleNode> associationRuleRootNodes, int supportThreshold) {
//		reportMostFrequentTransactionTypes(associationRuleRootNodes, 2);
//		reportLeastFrequentTransactionTyoesAboveSupportThreashold(associationRuleRootNodes, 2);
//		reportHighestPositiveSupportAssociationRules(associationRuleRootNodes, 2);
		reportHighestConfidenceAssociationRules(associationRuleRootNodes, 100, supportThreshold);
	}

	private static void reportHighestConfidenceAssociationRules(
			Hashtable<Long, AssociationRuleNode> associationRuleRootNodes, int numberToReport, int supportThreshold) {

		Comparator<AssociationRuleNode> confidenceComparator = new Comparator<AssociationRuleNode>() {
			@Override
			public int compare(AssociationRuleNode associationRuleNode1, AssociationRuleNode associationRuleNode2) {
//				logger.info("Comparing " + associationRuleNode1. + " with " + associationRuleNode2);
				int result = Double.compare(associationRuleNode1.getConfidence(), associationRuleNode2.getConfidence()); 
				if(result == 0) {
					return associationRuleNode1.compareTo(associationRuleNode2);
				}
				return result;
			}
		};
		// TODO: Find a more efficient way to sort these
		TreeSet<AssociationRuleNode> rulesSortedByConfidence = new TreeSet<AssociationRuleNode>(confidenceComparator);
		loadAssociationRulesRecursively(rulesSortedByConfidence, associationRuleRootNodes.values());
		
		for(int a=0; a<numberToReport; a++) {
			AssociationRuleNode nextAssociationRuleNode = rulesSortedByConfidence.pollLast();
			if(nextAssociationRuleNode == null) {
				break;
			}
			
			if(nextAssociationRuleNode.getSupport() < supportThreshold) {
				a--;
				continue;
			}
			logger.info("Confidence Rank = " + a + " confidence = " + nextAssociationRuleNode.getConfidence() + " " +  nextAssociationRuleNode.toString() );
		}
	}

	private static void reportHighestPositiveSupportAssociationRules(
			Hashtable<Long, AssociationRuleNode> associationRuleRootNodes, int numberToReport) {

		
	}

	private static void reportLeastFrequentTransactionTyoesAboveSupportThreashold(
			Hashtable<Long, AssociationRuleNode> associationRuleRootNodes, int numberToReport) {
		
	}

	private static void reportMostFrequentTransactionTypes(Hashtable<Long, AssociationRuleNode> associationRuleRootNodes, int numberToReport) {
		Comparator<AssociationRuleNode> supportComparator = new Comparator<AssociationRuleNode>() {
			@Override
			public int compare(AssociationRuleNode associationRuleNode1, AssociationRuleNode associationRuleNode2) {
				return Double.compare(associationRuleNode1.getSupport(), associationRuleNode2.getSupport());
			}
		};
		// TODO: Find a more efficient way to sort these
		TreeSet<AssociationRuleNode> rulesSortedByConfidence = new TreeSet<AssociationRuleNode>(supportComparator);
		loadAssociationRulesRecursively(rulesSortedByConfidence, associationRuleRootNodes.values());
		
		for(int a=0; a<numberToReport; a++) {
			AssociationRuleNode nextAssociationRuleNode = rulesSortedByConfidence.pollLast();
			if(nextAssociationRuleNode == null) {
				break;
			}
			
			logger.info("Support Rank = " + a + " support = " + nextAssociationRuleNode.getSupport() + " " +  nextAssociationRuleNode.toString() );
		}
	}
	/**
	 * Traverse the tree formed by the AssociationRuleNodes and add each node independently to the destionation.<br> 
	 * Typically, the destination will include a comparator, so that adding the rules will sort them nicely
	 * @param destination
	 * @param rootAssociationRuleNodes
	 */
	private static void loadAssociationRulesRecursively(Collection<AssociationRuleNode> destination, Collection<AssociationRuleNode> rootAssociationRuleNodes) {
		
		// TODO: Could use a hashtable here to efficiently keep track of which AssociationRuleNodes have been loaded into the destination and which ones have not, 
		// rather than using the contains method on the TreeSet, which might not be very efficient if the "entropy/information content" of the compareTo method is very low.
		
		if(rootAssociationRuleNodes == null || destination == null) {
			return;
		}
		
		for(AssociationRuleNode associationRuleNode : rootAssociationRuleNodes) {
			
			if(destination.contains(associationRuleNode)) {
				// This prevents cycles in the recursion.
				continue;
			}
			
			destination.add(associationRuleNode);
			Hashtable<Long, AssociationRuleNode> childRuleHashtable = associationRuleNode.getSubsequences();
			
			if(childRuleHashtable == null) {
				// This ends the recursion when it gets to the leaf nodes in the AssociationRuleNode tree
				continue;
			}
			loadAssociationRulesRecursively(destination, childRuleHashtable.values());
		}
	}
}
