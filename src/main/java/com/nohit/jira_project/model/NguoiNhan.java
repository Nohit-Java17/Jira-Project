package com.nohit.jira_project.model;

import java.util.*;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.GenerationType.*;

@Entity(name = "nguoi_nhan")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class NguoiNhan {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "ho_ten")
    private String hoTen;

    @NonNull
    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @NonNull
    @Column(name = "dia_chi")
    private String diaChi;

    @NonNull
    @Column(name = "xa_phuong")
    private String xaPhuong;

    @NonNull
    @Column(name = "quan_huyen")
    private String quanHuyen;

    @NonNull
    @Column(name = "tinh_thanh")
    private String tinhThanh;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @OneToMany(mappedBy = "nguoiNhan")
    private List<DonHang> dsDonHang;
}
