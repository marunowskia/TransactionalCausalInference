package oss.marunowskia.datamining.transactionalcausalinference.utilities.transactions;

import java.util.Comparator;

import oss.marunowskia.datamining.transactionalcausalinference.models.TransactionBoundary;

public class TransactionBoundaryComparator {

	public static final Comparator<TransactionBoundary> COMPARATOR = new Comparator<TransactionBoundary>() {	
		@Override
		public int compare(TransactionBoundary o1, TransactionBoundary o2) {
			long t1 = o1.getDate().getTime();
			long t2 = o2.getDate().getTime();
			if(t1<t2)
				return -1;
			else return (t1==t2)?0:1;
		}
	};
}
