package com.nohit.jira_project.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity(name = "chi_tiet_don_hang")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ChiTietDonHang {
	
	@EmbeddedId
	private ChiTietDonHangId id;
	
	@ManyToOne
	@MapsId("id_don_hang")
	@JoinColumn(name = "id_don_hang")
	private DonHang donHangs;
	
	@ManyToOne
	@MapsId("id_san_pham")
	@JoinColumn(name = "id_san_pham")
	private SanPham sanPhams;
	
	@NonNull
	@Column(name = "so_luong_san_pham")
	private int soLuong;
	
	@NonNull
	@Column(name = "gia_ban")
	private int giaBan;
}
