package com.nohit.jira_project.model;

import java.util.*;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.GenerationType.*;

@Entity(name = "tinh_thanh")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TinhThanh {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "ten")
    private String ten;

    @Column(name = "chi_phi_van_chuyen")
    private int chiPhiVanChuyen;

    @OneToMany(mappedBy = "tinhThanh")
    private List<KhachHang> dsKhachHang;

    @OneToMany(mappedBy = "tinhThanh")
    private List<NguoiNhan> dsNguoiNhan;

    @OneToMany(mappedBy = "tinhThanh")
    private List<GioHang> dsGioHang;
}
