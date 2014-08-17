package oss.marunowskia.datamining.transactionalcausalinference.models;
/**
 * A SeqeunceNode is essentially a node in a singly-linked-list. It allows associating an event-type with a corresponding event-object.
 * @author Alex
 *
 */
public class SequenceNode {

	private long eventType;
	private SequenceNode previousNode;
	private Object eventSource;
	
	public long getEventType() {
		return eventType;
	}
	public void setNodeType(long nodeType) {
		this.eventType = nodeType;
	}
	public SequenceNode getPreviousNode() {
		return previousNode;
	}
	public void setPreviousNode(SequenceNode previousNode) {
		this.previousNode = previousNode;
	}
	public Object getEventSource() {
		return eventSource;
	}
	public void setEventSource(Object eventSource) {
		this.eventSource = eventSource;
	}
}
