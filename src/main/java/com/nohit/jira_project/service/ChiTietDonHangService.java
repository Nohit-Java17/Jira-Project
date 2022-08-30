package com.nohit.jira_project.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.Impl.*;

@Service
public class ChiTietDonHangService implements ChiTietDonHangServiceImpl {
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    @Override
    public List<ChiTietDonHang> getDsChiTietDonHang() {
        // TODO Auto-generated method stub
        return chiTietDonHangRepository.findAll();
    }

    @Override
    public ChiTietDonHang geTietDonHangById(int id) {
        // TODO Auto-generated method stub
        return chiTietDonHangRepository.findById(id).orElse(null);
    }

    @Override
    public void saveChiTietDonHang(ChiTietDonHang chiTietDonHang) {
        // TODO Auto-generated method stub
        chiTietDonHangRepository.save(chiTietDonHang);
    }

    @Override
    public void deleteChiTietDonHang(int id) {
        // TODO Auto-generated method stub
        chiTietDonHangRepository.deleteById(id);
    }
}
