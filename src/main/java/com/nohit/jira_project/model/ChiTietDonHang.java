package com.nohit.jira_project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private int maDonHang;
	private int maSP;
	
	@NonNull
	@Column(name = "so_luong_san_pham")
	private int soLuong;
	
	@NonNull
	@Column(name = "gia_ban")
	private int giaBan;
}
