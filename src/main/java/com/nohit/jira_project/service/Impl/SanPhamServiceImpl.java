package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<SanPham> getDsSanPham() {
        // TODO Auto-generated method stub
        return sanPhamRepository.findAll();
    }

    @Override
    public SanPham getSanPham(int id) {
        // TODO Auto-generated method stub
        return sanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public void saveSanPham(SanPham sanPham) {
        // TODO Auto-generated method stub
        sanPhamRepository.save(sanPham);
    }

    @Override
    public void deleteSanPham(int id) {
        // TODO Auto-generated method stub
        sanPhamRepository.deleteById(id);
    }
}