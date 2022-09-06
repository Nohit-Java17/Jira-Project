package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface ChiTietGioHangService {
    public Iterable<ChiTietGioHang> getDsChiTietGioHang();

    public ChiTietGioHang getChiTietGioHang(ChiTietGioHangId id);

    public void saveChiTietGioHang(ChiTietGioHang chiTietGioHang);

    public void deleteChiTietGioHang(ChiTietGioHangId id);

}
