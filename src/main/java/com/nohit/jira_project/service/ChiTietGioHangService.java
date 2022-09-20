package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface ChiTietGioHangService {
    public List<ChiTietGioHang> getDsChiTietGioHang();

    public ChiTietGioHang getChiTietGioHang(ChiTietGioHangId id);

    public ChiTietGioHang saveChiTietGioHang(ChiTietGioHang chiTietGioHang);

    public void deleteChiTietGioHang(ChiTietGioHangId id);
}
