package com.nohit.jira_project.model;

import java.io.*;

import javax.persistence.*;

import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanXetId implements Serializable {
    @Column(name = "id_khach_hang")
    private int idKhachHang;

    @Column(name = "id_san_pham")
    private int idSanPham;
}
