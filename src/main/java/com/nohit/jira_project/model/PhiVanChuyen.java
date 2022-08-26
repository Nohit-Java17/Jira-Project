package com.nohit.jira_project.model;

import javax.persistence.*;

import lombok.*;

@Entity(name = "phi_van_chuyen")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PhiVanChuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idVanChuyen;

    @NonNull
    @Column(name = "tinh_thanh")
    private String tinhThanh;

    @Column(name = "chi_phi_van_chuyen")
    private int chiPhi;
}
