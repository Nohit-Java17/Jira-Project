package com.nohit.jira_project.service;

import java.io.*;

import javax.mail.*;

import com.nohit.jira_project.model.*;

public interface KhachHangService {
    public Iterable<KhachHang> getDsKhachHang();

    public KhachHang getKhachHang(int id);

    public KhachHang getKhachHang(String email);

    public KhachHang saveKhachHang(KhachHang khachHang);

    public void saveKhachHangWithoutPassword(KhachHang khachHang);

    public void updatePassword(int id, String password);

    public void deleteKhachHang(int id);

    public void resetPassword(String email) throws UnsupportedEncodingException, MessagingException;
}
