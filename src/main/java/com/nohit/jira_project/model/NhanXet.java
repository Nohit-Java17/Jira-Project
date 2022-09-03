package com.nohit.jira_project.model;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity(name = "nhan_xet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanXet {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "danh_gia")
    private int danhGia;

    @Column(name = "binh_luan")
    private String binhLuan;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id", insertable = false, updatable = false)
    private SanPham sanPham;
}
