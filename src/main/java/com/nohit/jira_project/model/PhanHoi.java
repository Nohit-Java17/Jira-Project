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

@Entity(name = "thu_phan_hoi")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PhanHoi {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private int idPhanHoi;
	
	@NonNull
	@Column(name = "ho_ten")
	private String hoTen;
	
	@NonNull
	@Column(name = "email")
	private String email;
	
	@Column(name = "chu_de")
	private String chuDe;
	
	@Column(name = "noi_dung")
	private String noiDung;
}
