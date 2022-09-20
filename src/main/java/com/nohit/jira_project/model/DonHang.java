package com.nohit.jira_project.model;

import java.util.*;

import javax.persistence.*;

import org.springframework.format.annotation.*;

import lombok.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity(name = "don_hang")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class DonHang {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngay_dat")
    private Date ngayDat;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "ngay_nhan")
    private Date ngayNhan;

    @Column(name = "tong_gio_hang")
    private int tongGioHang;

    @Column(name = "chi_phi_van_chuyen")
    private int chiPhiVanChuyen;

    @Column(name = "giam_gia")
    private int giamGia;

    @Column(name = "tong_don_hang")
    private int tongDonHang;

    @NonNull
    @Column(name = "phuong_thuc_thanh_toan")
    private String phuongThucThanhToan;

    @Column(name = "id_khach_hang")
    private int idKhachHang;

    @Column(name = "id_nguoi_nhan")
    private int idNguoiNhan;

    @NonNull
    @Column(name = "trang_thai")
    private String trangThai;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id_khach_hang", referencedColumnName = "id", insertable = false, updatable = false)
    private KhachHang khachHang;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id_nguoi_nhan", referencedColumnName = "id", insertable = false, updatable = false)
    private NguoiNhan nguoiNhan;

    @OneToMany(mappedBy = "donHang")
    private List<ChiTietDonHang> dsChiTietDonHang;
}
