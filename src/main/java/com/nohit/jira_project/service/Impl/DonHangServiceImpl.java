package com.nohit.jira_project.service.Impl;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface DonHangServiceImpl {
    public List<DonHang> getDsDonHang();

    public DonHang getDonHangById(int id);

    public void saveDonHang(DonHang donHang);

    public void deleteDonHang(int id);
}
