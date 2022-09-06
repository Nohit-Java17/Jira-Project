package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface ChiTietDonHangService {
    public Iterable<ChiTietDonHang> getDsChiTietDonHang();

    public ChiTietDonHang getChiTietDonHang(ChiTietDonHangId id);

    public void saveChiTietDonHang(ChiTietDonHang chiTietDonHang);

    public void deleteChiTietDonHang(ChiTietDonHangId id);
}
