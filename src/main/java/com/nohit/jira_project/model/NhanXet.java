package com.nohit.jira_project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity(name = "nhan_xet")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class NhanXet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int idNhanXet;
	
	@NonNull
	@Column(name = "danh_gia")
	private int danhGia;
	
	@Column(name = "binh_luan")
	private String binhLuan;
	
	@JoinColumn(name = "id_san_pham", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne
	private SanPham idSP;
	
	
}
