package com.nohit.jira_project.service.Impl;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface KhachHangServiceImpl {
    public List<KhachHang> getDsKhachHang();

    public KhachHang getKhachHangById(int id);

    public void saveKhachHang(KhachHang khachHang);

    public void deleteKhachHang(int id);

    public KhachHang findByemail(String email);
}
