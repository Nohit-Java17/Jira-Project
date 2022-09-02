package com.nohit.jira_project.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity(name = "gio_hang")
@Data
public class GioHang {
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "so_luong")
    private int soLuong;
	
    @Column(name = "tong_tien")
    private int tongTien;
	
    @ManyToOne
    @JoinColumn(name = "id_chi_tiet_gio_hang", referencedColumnName = "id", insertable = false, updatable = false)
    private ChiTietGioHang idChiTietGioHang;
    
    @OneToOne
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id", insertable = false, updatable = false)
    private KhachHang idKhachHang;
}
