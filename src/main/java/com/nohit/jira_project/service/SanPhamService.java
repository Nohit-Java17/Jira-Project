package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface SanPhamService {
    public List<SanPham> getDsSanPham();

    public List<SanPham> getDsSanPhamTopSale();

    public List<SanPham> getDsSanPhamDescendingPriceOrder();

    public List<SanPham> getDsSanPhamAscendingPriceOrder();

    public List<SanPham> getDsSanPhamNewestOrder();

    public SanPham getSanPham(int id);

    public void saveSanPham(SanPham sanPham);

    public void deleteSanPham(int id);
}
