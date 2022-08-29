package com.nohit.jira_project.service;

import java.util.List;

import com.nohit.jira_project.model.ChiTietDonHang;

public interface ChiTietDonHangServiceImp {
	public List<ChiTietDonHang> getDsChiTietDonHang();
	public ChiTietDonHang geTietDonHangById(int id);
	public void saveChiTietDonHang(ChiTietDonHang chiTietDonHang);
	public void deleteChiTietDonHang(int id);
}
