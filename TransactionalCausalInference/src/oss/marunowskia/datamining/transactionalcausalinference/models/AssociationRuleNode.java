package oss.marunowskia.datamining.transactionalcausalinference.models;

import java.util.Hashtable;
import java.util.UUID;

public class AssociationRuleNode implements Comparable {

	private Hashtable<Long, AssociationRuleNode> subsequences = new Hashtable<Long, AssociationRuleNode>();
	private AssociationRuleNode parentAssociationRule;
	private long eventType;

	private long positiveSupport;
	private long negativeSupport;

	private long support;
	private long correctGuesses;
	private long incorrectGuesses;

	private int depth = 0;

	private String id = UUID.randomUUID().toString();
	private Object lastObservedObject;

	public long getEventType() {
		return eventType;
	}

	public void setEventType(long eventType) {
		this.eventType = eventType;
	}

	public Hashtable<Long, AssociationRuleNode> getSubsequences() {
		return subsequences;
	}

	public void setSubsequences(
			Hashtable<Long, AssociationRuleNode> subsequences) {
		this.subsequences = subsequences;
	}

	public long getSupport() {
		return support;
	}

	public void setSupport(long support) {
		this.support = support;
	}

	public long getPositiveSupport() {
		return positiveSupport;
	}

	public void setPositiveSupport(long positiveSupport) {
		this.positiveSupport = positiveSupport;
	}

	public long getNegativeSupport() {
		return negativeSupport;
	}

	public void setNegativeSupport(long negativeSupport) {
		this.negativeSupport = negativeSupport;
	}

	public long getCorrectGuesses() {
		return correctGuesses;
	}

	public void setCorrectGuesses(long correctGuesses) {
		this.correctGuesses = correctGuesses;
	}

	public long getIncorrectGuesses() {
		return incorrectGuesses;
	}

	public void setIncorrectGuesses(long incorrectGuesses) {
		this.incorrectGuesses = incorrectGuesses;
	}

	private long incrementNegativeSupport() {
		support++;
		return ++negativeSupport;
	}

	private long incrementPositiveSupport() {
		support++;
		return ++positiveSupport;
	}

	public long incrementSupport(boolean positiveExample, Object evidence) {

		if (lastObservedObject == evidence) {
			return -1;
		}

		lastObservedObject = evidence;

		if (positiveSupport > negativeSupport && support > 200) {
			// System.out.println("Interesting!" + depth);
		}

		if (positiveExample) {
			positiveSupport++;
		} else {
			negativeSupport++;
		}
		return ++support;
	}

	public void recordPrediction(boolean positiveExample) {
		boolean predictionType = (positiveSupport > negativeSupport);
		if (positiveExample == predictionType) {
			correctGuesses++;
		} else {
			incorrectGuesses++;
		}
	}

	public Object getLastObservedObject() {
		return lastObservedObject;
	}

	public void setLastObservedObject(Object lastObservedTransation) {
		this.lastObservedObject = lastObservedTransation;
	}

	public double getConfidence() {
		return (double) positiveSupport / (double) (support);
	}

	public static class DuplicateEvidenceException extends Exception {
		public DuplicateEvidenceException() {
			super(
					"Not allowed to use the same transaction consecutively to update the support of a rule");
		}
	}	

	public AssociationRuleNode getParentAssociationRule() {
		return parentAssociationRule;
	}

	public void setParentAssociationRule(AssociationRuleNode parentAssociationRule) {
		this.parentAssociationRule = parentAssociationRule;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public String getEventSequence() {
		if(parentAssociationRule!=null) {
			return parentAssociationRule.getEventSequence() + ", " + eventType; 
		}
		else {
			return String.valueOf(eventType);
		}
	}

	@Override
	public String toString() {
		return "positiveSupport=" + positiveSupport + ", support="
				+ support + ", depth=" + depth + ", sequence: " + getEventSequence(); 
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println("Checking equality of two association rule nodes");
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssociationRuleNode other = (AssociationRuleNode) obj;
		return this.id.equals(other.getId());
	}

	public String getId() {
		return id;
	}

	/**
	 * Note: an ID is auto-assigned during object construction. Only call this
	 * method for purposes of persistence and equality checks
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int compareTo(Object obj) {

		if (this == obj)
			return 0;
		if (obj == null)
			return -1;
		if (getClass() != obj.getClass())
			return getClass().getCanonicalName().compareTo(
					obj.getClass().getCanonicalName());

		AssociationRuleNode other = (AssociationRuleNode) obj;
		return this.getId().compareTo(other.getId());
	}

}
