package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

@Service
public class DonHangServiceImpl implements DonHangService {
    @Autowired
    private DonHangRepository donHangRepository;

    @Override
    public List<DonHang> getDsDonHang() {
        // TODO Auto-generated method stub
        return donHangRepository.findAll();
    }

    @Override
    public DonHang getDonHang(int id) {
        // TODO Auto-generated method stub
        return donHangRepository.findById(id).orElse(null);
    }

    @Override
    public void saveDonHang(DonHang donHang) {
        // TODO Auto-generated method stub
        donHangRepository.save(donHang);
    }

    @Override
    public void deleteDonHang(int id) {
        // TODO Auto-generated method stub
        donHangRepository.deleteById(id);
    }
}
