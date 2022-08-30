package com.nohit.jira_project.service.Impl;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface SanPhamServiceImpl {
    public List<SanPham> getDsSanPham();

    public SanPham getSanPhamById(int id);

    public void saveSanPham(SanPham sanPham);

    public void deleteSanPham(int id);
}
