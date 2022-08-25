package com.nohit.jira_project.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity(name = "don_hang")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class DonHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int idDonHang;
	
	@NonNull
	@Column(name = "ngay_dat")
	private String ngayDat;
	
	@Column(name = "ngay_giao")
	private String ngayGiao;
	
	@NonNull
	@Column(name = "tong_gio_hang")
	private int tongSoLuong;
	
	@NonNull
	@Column(name = "chi_phi_van_chuyen")
	private int phiVanChuyen;
	
	@NonNull
	@Column(name = "tong_don_hang")
	private int tongTien;
	
	@NonNull
	@Column(name = "phuong_thuc_thanh_toan")
	private String phuongThucTT;
	
	@NonNull
	@Column(name = "trang_thai")
	private String trangThai;
	
	@ManyToOne
	@JoinColumn(name = "id_khach_hang", referencedColumnName = "id", insertable = false, updatable = false)
	private KhachHang idKH;
	
	@ManyToOne
	@JoinColumn(name = "id_nguoi_nhan", referencedColumnName = "id", insertable = false, updatable = false)
	private NguoiNhan idNguoiNhan;
	
	@OneToMany(mappedBy = "donHangs")
	private List<ChiTietDonHang> chiTietDonHangs;
}
