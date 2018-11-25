package com.yinq.situation.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="sleep_situation_table")
public class SleepSituationModel extends SituationDetModel {
	private String id;
	
	private int code;
	private int status;
	private int score;
	
	
	public SleepSituationModel() {
		// TODO Auto-generated constructor stub
		
	}
	
	public SleepSituationModel(SleepSituationParam param) {
		
		this.code = param.getCode();
		this.status = param.getStatus();
		this.score = 3 + status;
		
		
	}
	
	public SleepSituationModel(SleepSituationModel model) {
		id = model.getId();
		
		code = model.getCode();
		status = model.getStatus();
		score = model.getScore();
	}
	
	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="code")
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	@Column(name="status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name="score")
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	
}
