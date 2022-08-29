package com.nohit.jira_project.service;

import java.util.List;

import com.nohit.jira_project.model.KhachHang;

public interface KhachHangServiceImp {
	public List<KhachHang> getDsKhachHang();
	public KhachHang getKhachHangById(int id);
	public void saveKhachHang(KhachHang khachHang);
	public void deleteKhachHang(int id);
}
