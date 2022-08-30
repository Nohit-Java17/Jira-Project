package com.nohit.jira_project.service;

import java.util.List;

import com.nohit.jira_project.model.SanPham;

public interface SanPhamServiceImp {
	public List<SanPham> getDsSanPham();
	public SanPham getSanPhamById(int id);
	public void saveSanPham(SanPham sanPham);
	public void deleteSanPham(int id);
}
