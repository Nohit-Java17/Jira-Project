package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface SanPhamService {
    public Iterable<SanPham> getDsSanPham();

    public SanPham getSanPham(int id);

    public void saveSanPham(SanPham sanPham);

    public void deleteSanPham(int id);

    // Methods to sort in Product Controller
    public List<SanPham> getDsSanPhamTopSale();

    public List<SanPham> getDsSanPhamDescendingPriceOrder();

    public List<SanPham> getDsSanPhamAscendingPriceOrder();

    public List<SanPham> getDsSanPhamNewestOrder();

    // Methods to sort in Category Controller
    public Iterable<SanPham> getDsSanPhamLaptop();

    public Iterable<SanPham> getDsSanPhamComputer();

    public Iterable<SanPham> getDsSanPhamSmartPhone();

    public Iterable<SanPham> getDsSanPhamTablet();

    public Iterable<SanPham> getDsSanPhamDevices();

}
