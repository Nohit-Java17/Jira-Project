package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface ChiTietDonHangService {
    public List<ChiTietDonHang> getDsChiTietDonHang();

    public ChiTietDonHang getChiTietDonHang(ChiTietDonHangId id);

    public ChiTietDonHang saveChiTietDonHang(ChiTietDonHang chiTietDonHang);

    public void deleteChiTietDonHang(ChiTietDonHangId id);
}
