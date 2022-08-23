package com.nohit.jira_project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ChiTietDonHangId implements Serializable{
	
	@Column(name = "role_id")
	private long roleId;
	
	@Column(name = "user_id")
	private long userId;
	
	public ChiTietDonHangId(long role_id, long user_id) {
		this.roleId = role_id;
		this.userId = user_id;
	}
	
	public ChiTietDonHangId() {
		
	}
}
