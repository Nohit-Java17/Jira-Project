package com.nohit.jira_project.model;

import java.util.*;

import javax.persistence.*;

import lombok.*;

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

    @Column(name = "tong_gio_hang")
    private int tongSoLuong;

    @Column(name = "chi_phi_van_chuyen")
    private int phiVanChuyen;

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
