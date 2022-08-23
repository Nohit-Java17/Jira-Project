package com.nohit.jira_project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity(name = "phi_van_chuyen")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PhiVanChuyen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma")
	private int maVanChuyen;
	
	@NonNull
	@Column(name = "tinh_thanh")
	private String tinhThanh;
	
	@NonNull
	@Column(name = "chi_phi_van_chuyen")
	private int chiPhi;
}
