package com.nohit.jira_project.model;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.FetchType.*;

@Entity(name = "chi_tiet_don_hang")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDonHang {
    @EmbeddedId
    private ChiTietDonHangId id;

    @Column(name = "so_luong_san_pham")
    private int soLuongSanPham;

    @Column(name = "gia_ban_san_pham")
    private int giaBanSanPham;

    @Column(name = "tong_tien_san_pham")
    private int tongTienSanPham;

    @ManyToOne(fetch = LAZY)
    @MapsId("id_don_hang")
    @JoinColumn(name = "id_don_hang", referencedColumnName = "id", insertable = false, updatable = false)
    private DonHang donHang;

    @ManyToOne(fetch = LAZY)
    @MapsId("id_san_pham")
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id", insertable = false, updatable = false)
    private SanPham sanPham;
}
