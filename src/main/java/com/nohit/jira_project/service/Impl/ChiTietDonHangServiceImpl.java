package com.nohit.jira_project.service.Impl;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface ChiTietDonHangServiceImp {
    public List<ChiTietDonHang> getDsChiTietDonHang();

    public ChiTietDonHang geTietDonHangById(int id);

    public void saveChiTietDonHang(ChiTietDonHang chiTietDonHang);

    public void deleteChiTietDonHang(int id);
}
