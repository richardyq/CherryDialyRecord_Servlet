package com.yinq.situation.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yinq.datamodel.JsonModel;

@Entity
@Table(name="sleep_situation_table")
public class SleepSituationModel extends JsonModel {
	private String id;
	private String date;
	private int code;
	private int status;
	private int score;
	
	public SleepSituationModel() {
		// TODO Auto-generated constructor stub
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		id = uuid;
	}
	
	public SleepSituationModel(String date, int code, int status) {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		id = uuid;
		this.date = date;
		this.code = code;
		this.status = status;
		this.score = 3 + status;
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

	@Column(name="date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
