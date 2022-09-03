package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface DonHangService {
    public Iterable<DonHang> getDsDonHang();

    public DonHang getDonHang(int id);

    public void saveDonHang(DonHang donHang);

    public void deleteDonHang(int id);
}
