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

@Entity(name = "san_pham")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class SanPham {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma")
	private int maSP;
	
	@NonNull
	@Column(name = "ten")
	private String tenSP;
	
	@NonNull
	@Column(name = "hinh_anh")
	private String hinhAnh;
	
	@NonNull
	@Column(name = "mo_ta")
	private String moTa;
	
	@NonNull
	@Column(name = "gia_goc")
	private int giaGoc;
	
	@Column(name = "giam_gia")
	private int giamGia;
	
	@NonNull
	@Column(name = "so_luong")
	private int soLuong;
	
	@NonNull
	@Column(name = "ngay_nhap")
	private String ngayNhap;
	
	@NonNull
	@Column(name = "ton_kho")
	private int tonKho;
	
	@Column(name = "danh_gia")
	private int danhGia;
	
	@NonNull
	@Column(name = "phan_loai")
	private String phanLoai;
	
	@NonNull
	@Column(name = "thuong_hieu")
	private String thuongHieu;
}
