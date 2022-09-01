package com.nohit.jira_project.model;

import javax.persistence.*;

import lombok.*;

import static javax.persistence.GenerationType.*;

@Entity(name = "phi_van_chuyen")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PhiVanChuyen {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "tinh_thanh")
    private String tinhThanh;

    @Column(name = "chi_phi_van_chuyen")
    private int chiPhiVanChuyen;
}
