package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface GioHangService {
    public Iterable<GioHang> getDsGioHang();

    public GioHang getGioHang(int id);

    public void saveGioHang(GioHang gioHang);

    public void deleteGioHang(int id);

    public GioHang createGioHang(KhachHang khachHang);
}
