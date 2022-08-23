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

@Entity(name = "nguoi_nhan")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class NguoiNhan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma")
	private int maNguoiNhan;
	
	@NonNull
	@Column(name = "ho_ten")
	private String hoTen;
	
	@NonNull
	@Column(name = "so_dien_thoai")
	private String soDienThoai;
	
	@NonNull
	@Column(name = "dia_chi")
	private String diaChi;
	
	@NonNull
	@Column(name = "xa_phuong")
	private String xaPhuong;
	
	@NonNull
	@Column(name = "quan_huyen")
	private String quanHuyen;
	
	@NonNull
	@Column(name = "tinh_thanh")
	private String tinhThanh;
	
	@Column(name = "ghi_chu")
	private String ghiChu;
}
