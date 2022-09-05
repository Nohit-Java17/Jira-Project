package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface ChiTietGioHangService {
    public Iterable<ChiTietGioHang> getDsChiTietGioHang();

    public ChiTietGioHang getChiTietGioHang(int id);

    public void saveChiTietGioHang(ChiTietGioHang chiTietGioHang);

    public void deleteChiTietGioHang(int id);

}
