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

    @Column(name = "so_luong_san_pham")
    private int soLuongSanPham;

    @Column(name = "gia_ban_san_pham")
    private int giaBanSanPham;

    @Column(name = "tong_tien_san_pham")
    private int tongTienSanPham;

    @ManyToOne(fetch = LAZY)
    @MapsId("id_gio_hang")
    @JoinColumn(name = "id_gio_hang", referencedColumnName = "id", insertable = false, updatable = false)
    private GioHang gioHang;

    @ManyToOne(fetch = LAZY)
    @MapsId("id_san_pham")
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id", insertable = false, updatable = false)
    private SanPham sanPham;
}
