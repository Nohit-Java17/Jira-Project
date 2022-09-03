package com.nohit.jira_project.model;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.FetchType.*;

@Entity(name = "chi_tiet_gio_hang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietGioHang {
    @EmbeddedId
    private ChiTietGioHangId id;

    @ManyToOne(fetch = LAZY)
    @MapsId("id_gio_hang")
    @JoinColumn(name = "id_gio_hang")
    private GioHang gioHang;

    @ManyToOne(fetch = LAZY)
    @MapsId("id_san_pham")
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;

    @Column(name = "so_luong_san_pham")
    private int soLuongSanPhan;

    @Column(name = "gia_ban_san_pham")
    private int giaBanSanPham;

    @Column(name = "tong_tien_san_pham")
    private int tongTienSanPham;
}
