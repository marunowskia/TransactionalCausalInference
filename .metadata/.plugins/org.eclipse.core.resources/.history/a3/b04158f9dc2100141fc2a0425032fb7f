package oss.marunowskia.datamining.transactionalcausalinference.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TraceData {

	private long rowNumber;
	private Date startTime;
	private Date endTime;
	private long duration;
	private String textData;
	
	@Id
	@Column(name="RowNumber")
	public long getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(long rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	@Column(name="StartTime")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Column(name="EndTime")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@Column(name="TextData")
	public String getTextData() {
		return textData;
	}
	public void setTextData(String textData) {
		this.textData = textData;
	}
	
	@Column(name="Duration")
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	
	
	
}
