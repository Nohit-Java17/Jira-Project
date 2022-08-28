package com.nohit.jira_project.model;

import javax.persistence.*;

import lombok.*;

@Entity(name = "chi_tiet_don_hang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDonHang {

    @EmbeddedId
    private ChiTietDonHangId id;

    @ManyToOne
    @MapsId("id_don_hang")
    @JoinColumn(name = "id_don_hang")
    private DonHang donHangs;

    @ManyToOne
    @MapsId("id_san_pham")
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPhams;

    @Column(name = "so_luong_san_pham")
    private int soLuong;

    @Column(name = "gia_ban")
    private int giaBan;
}
