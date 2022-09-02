package com.nohit.jira_project.service;

import java.util.List;

import com.nohit.jira_project.model.ChiTietGioHang;

public interface ChiTietGioHangService {

	public List<ChiTietGioHang> getDsChiTietGioHang();

    public ChiTietGioHang getChiTietGioHang(int id);

    public void saveChiTietGioHang(ChiTietGioHang chiTietGioHang);

    public void deleteChiTietGioHang(int id);
}
