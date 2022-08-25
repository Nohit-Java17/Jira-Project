package com.nohit.jira_project.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ChiTietDonHangId implements Serializable{
	
	@Column(name = "id_don_hang")
	private long donHangId;
	
	@Column(name = "id_san_pham")
	private long sanPhamId;
	
	public ChiTietDonHangId(long donHangId, long sanPhamId) {
		this.donHangId = donHangId;
		this.sanPhamId = sanPhamId;
	}
	
	public ChiTietDonHangId() {
		
	}
}
