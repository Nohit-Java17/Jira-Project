package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface KhachHangService {
    public List<KhachHang> getDsKhachHang();

    public KhachHang getKhachHang(int id);

    public KhachHang getKhachHang(String email);

    public void saveKhachHang(KhachHang khachHang);

    public void deleteKhachHang(int id);
}
