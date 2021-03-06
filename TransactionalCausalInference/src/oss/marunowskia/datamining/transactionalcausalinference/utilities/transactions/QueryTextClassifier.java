package oss.marunowskia.datamining.transactionalcausalinference.utilities.transactions;

public class QueryTextClassifier {

	/**
	 * Removes all literal values from the input string. This will replace all numbers with 0, and all string-literals with an empty string.
	 * @param queryText
	 * @return
	 */
	public static String stripLiteralValues(String queryText) {
		StringBuffer result = new StringBuffer();
		boolean inStringLiteral = false;
		boolean inNumberSequence = false;
		for(Character c: queryText.toCharArray()) {
			if(c.equals('\'')) {
				inStringLiteral = !inStringLiteral;
				continue;
			}
			if(inStringLiteral) {
				continue;
			}
			if(Character.isDigit(c)) {
				inNumberSequence = true;
			}
			else {
				if(inNumberSequence) {
					// Each number sequence gets replaced with a single '0'
					result.append(0);
					inNumberSequence = false;
				}
				result.append(c);
			}
		}
		return result.toString();
	}
	/**
	 * Attempts to produce a reasonable numerical categorization of the input queryText, ignoring variables and literals in the query itself.
	 * @param queryText
	 * @return
	 */
	public static long classifyQuery(String queryText) {
		return stripLiteralValues(queryText).hashCode();
	}
}
