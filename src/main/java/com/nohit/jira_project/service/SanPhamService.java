package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface SanPhamService {
    public List<SanPham> getDsSanPham();

    public SanPham getSanPham(int id);

    public void saveSanPham(SanPham sanPham);

    public void deleteSanPham(int id);

    public List<SanPham> getDsSanPhamTonKho();

    // Methods to sort in Product Controller
    public List<SanPham> getDsSanPhamTopSale();

    public List<SanPham> getDsSanPhamAscendingPriceOrder();

    public List<SanPham> getDsSanPhamDescendingPriceOrder();

    public List<SanPham> getDsSanPhamNewestOrder();

    // Methods to sort in Category Controller
    public List<SanPham> getDsSanPhamLaptop();

    public List<SanPham> getDsSanPhamComputer();

    public List<SanPham> getDsSanPhamSmartPhone();

    public List<SanPham> getDsSanPhamTablet();

    public List<SanPham> getDsSanPhamDevices();
}
