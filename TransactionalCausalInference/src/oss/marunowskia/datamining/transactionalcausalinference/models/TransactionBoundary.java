package oss.marunowskia.datamining.transactionalcausalinference.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;


public class TransactionBoundary {
		
		 
		private Date date;
		private EventType eventType;
		private Transaction transaction;
		
		private TransactionBoundary previousTransactionBoundary;

		public TransactionBoundary() {

		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
		
		public EventType getEventType() {
			return eventType;
		}

		public void setEventType(EventType eventType) {
			this.eventType = eventType;
		}

		public Transaction getTransaction() {
			return transaction;
		}

		public void setTransaction(Transaction transaction) {
			this.transaction = transaction;
		}
		
		public boolean getDurationLongerThanNormal() {
			return transaction.isDurationLongerThanNormal();
		}

		public TransactionBoundary getPreviousTransactionBoundary() {
			return previousTransactionBoundary;
		}

		public void setPreviousTransactionBoundary(
				TransactionBoundary previousTransactionBoundary) {
			this.previousTransactionBoundary = previousTransactionBoundary;
		}

		public long getTypeId() {
			long result = transaction.getTransactionType();
			
			if(getEventType()==EventType.TRANSACTION_END) {
				result = -result;
			}
			
			return result;
		}
		
		public static enum EventType {
			TRANSACTION_START,
			TRANSACTION_END
		}
	}