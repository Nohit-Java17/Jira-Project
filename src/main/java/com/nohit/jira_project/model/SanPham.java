package com.nohit.jira_project.model;

import java.util.*;

import javax.persistence.*;

import org.springframework.format.annotation.*;

import lombok.*;

import static javax.persistence.GenerationType.*;

@Entity(name = "san_pham")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class SanPham {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "ten")
    private String ten;

    @NonNull
    @Column(name = "album")
    private String album;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "gia_goc")
    private int giaGoc;

    @Column(name = "khuyen_mai")
    private int khuyenMai;

    @Column(name = "so_luong")
    private int soLuong;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngay_nhap")
    private Date ngayNhap;

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

    @OneToMany(mappedBy = "sanPham")
    private List<NhanXet> dsNhanXet;

    @OneToMany(mappedBy = "sanPham")
    private List<ChiTietDonHang> dsChiTietDonHang;

    @OneToMany(mappedBy = "sanPham")
    private List<ChiTietGioHang> dsChiTietGioHang;
}
