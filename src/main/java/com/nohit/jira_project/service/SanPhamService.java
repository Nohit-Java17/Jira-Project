package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface SanPhamService {
    public List<SanPham> getDsSanPham();

    public List<SanPham> getDsSanPham(String phanLoai);

    public SanPham getSanPham(int id);

    public void saveSanPham(SanPham sanPham);

    public void deleteSanPham(int id);

    // Methods to sort in Product Controller
    public List<SanPham> getDsSanPhamTonKho();

    public List<SanPham> getDsSanPhamTopSale();

    public List<SanPham> getDsSanPhamNewest();

    public List<SanPham> getDsSanPhamAscendingPrice();

    public List<SanPham> getDsSanPhamDescendingPrice();

    public List<SanPham> getDsSanPhamAscendingDiscount();

    public List<SanPham> getDsSanPhamDescendingDiscount();
}
