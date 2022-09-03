package com.nohit.jira_project.model;

import java.io.*;

import javax.persistence.*;

import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietGioHangId implements Serializable {
    @Column(name = "id_gio_hang")
    private int idGioHang;

    @Column(name = "id_san_pham")
    private int idSanPham;
}
