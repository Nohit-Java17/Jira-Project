package com.nohit.jira_project.service;

import java.util.List;

import com.nohit.jira_project.model.DonHang;

public interface DonHangServiceImp {
	public List<DonHang> getDsDonHang();
	public DonHang getDonHangById(int id);
	public void saveDonHang(DonHang donHang);
	public void deleteDonHang(int id);
}
