package com.nohit.jira_project.model;

import java.util.*;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.FetchType.*;
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
    @Column(name = "huyen_quan")
    private String huyenQuan;

    @Column(name = "id_tinh_thanh")
    private int idTinhThanh;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id_tinh_thanh", referencedColumnName = "id", insertable = false, updatable = false)
    private TinhThanh tinhThanh;

    @OneToMany(mappedBy = "nguoiNhan")
    private List<DonHang> dsDonHang;
}
