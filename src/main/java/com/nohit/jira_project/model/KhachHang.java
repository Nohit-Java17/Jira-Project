package com.nohit.jira_project.model;

import java.util.*;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity(name = "khach_hang")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class KhachHang {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "thu_dien_tu")
    private String email;

    @NonNull
    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "xa_phuong")
    private String xaPhuong;

    @Column(name = "huyen_quan")
    private String huyenQuan;

    @Column(name = "id_tinh_thanh")
    private int idTinhThanh;

    @Column(name = "vai_tro")
    private String vaiTro;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id_tinh_thanh", referencedColumnName = "id", insertable = false, updatable = false)
    private TinhThanh tinhThanh;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private GioHang gioHang;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private CreditCard creditCard;

    @OneToMany(mappedBy = "khachHang")
    private List<DonHang> dsDonHang;

    @OneToMany(mappedBy = "khachHang")
    private List<NhanXet> dsNhanXet;
}