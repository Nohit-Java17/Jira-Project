package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface DonHangService {
    public List<DonHang> getDsDonHang();

    public DonHang getDonHang(int id);

    public DonHang saveDonHang(DonHang donHang);

    public void deleteDonHang(int id);
}
