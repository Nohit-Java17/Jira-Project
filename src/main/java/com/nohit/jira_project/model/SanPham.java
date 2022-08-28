package com.nohit.jira_project.model;

import java.util.*;

import javax.persistence.*;

import lombok.*;

@Entity(name = "san_pham")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idSP;

    @NonNull
    @Column(name = "ten")
    private String tenSP;

    @NonNull
    @Column(name = "hinh_anh")
    private String hinhAnh;

    @NonNull
    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "gia_goc")
    private int giaGoc;

    @Column(name = "giam_gia")
    private int giamGia;

    @Column(name = "so_luong")
    private int soLuong;

    @NonNull
    @Column(name = "ngay_nhap")
    private String ngayNhap;

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

    @OneToMany(mappedBy = "idSP")
    private List<NhanXet> nhanXets;

    @OneToMany(mappedBy = "sanPhams")
    private List<ChiTietDonHang> chiTietDonHangs;
}
