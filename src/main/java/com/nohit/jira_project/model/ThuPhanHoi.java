package com.nohit.jira_project.model;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.GenerationType.*;

@Entity(name = "thu_phan_hoi")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ThuPhanHoi {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ho_ten")
    private String hoTen;

    @NonNull
    @Column(name = "thu_dien_tu")
    private String email;

    @Column(name = "chu_de")
    private String chuDe;

    @Column(name = "noi_dung")
    private String noiDung;
}
