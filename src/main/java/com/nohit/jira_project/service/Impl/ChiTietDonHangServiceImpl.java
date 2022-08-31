package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

@Service
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    @Override
    public List<ChiTietDonHang> getDsChiTietDonHang() {
        // TODO Auto-generated method stub
        return chiTietDonHangRepository.findAll();
    }

    @Override
    public ChiTietDonHang getChiTietDonHang(int id) {
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
