package com.nohit.jira_project.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NonNull;

@Entity(name = "chi_tiet_gio_hang")
@Data
public class ChiTietGioHang {
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;
	
    @Column(name = "so_luong")
    private int soLuong;
	
    @Column(name = "tong_tien")
    private int tongTien;
    
    @ManyToOne
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id", insertable = false, updatable = false)
    private SanPham idSanPham;
    
    @OneToMany(mappedBy = "idChiTietGioHang")
    private List<GioHang> dsGioHang;
}

