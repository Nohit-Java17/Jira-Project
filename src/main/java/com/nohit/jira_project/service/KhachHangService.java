package com.nohit.jira_project.service;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.mail.MessagingException;

import com.nohit.jira_project.model.*;

public interface KhachHangService {
    public List<KhachHang> getDsKhachHang();

    public KhachHang getKhachHang(int id);

    public KhachHang getKhachHang(String email);

    public void saveKhachHang(KhachHang khachHang);

    public void deleteKhachHang(int id);
    
    public void resetPassword(String email) throws UnsupportedEncodingException, MessagingException;
}
