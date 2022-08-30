package com.nohit.jira_project.model;

import java.io.*;

import javax.persistence.*;

import lombok.*;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDonHangId implements Serializable {
    @Column(name = "id_don_hang")
    private long donHangId;

    @Column(name = "id_san_pham")
    private long sanPhamId;
}
