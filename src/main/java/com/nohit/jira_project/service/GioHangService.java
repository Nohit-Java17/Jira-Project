package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface GioHangService {
    public List<GioHang> getDsGioHang();

    public GioHang getGioHang(int id);

    public GioHang saveGioHang(GioHang gioHang);

    public void deleteGioHang(int id);

    public GioHang createGioHang(KhachHang khachHang);
}
