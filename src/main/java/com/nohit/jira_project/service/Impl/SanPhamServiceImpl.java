package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

@Service
@Slf4j
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<SanPham> getDsSanPham() {
        log.info("Fetching all san_pham");
        return sanPhamRepository.findAll();
    }

    @Override
    public SanPham getSanPham(int id) {
        log.info("Fetching san_pham with id {}", id);
        return sanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public void saveSanPham(SanPham sanPham) {
        log.info("Saving san_pham with name: {}", sanPham.getTen());
        sanPhamRepository.save(sanPham);
    }

    @Override
    public void deleteSanPham(int id) {
        log.info("Deleting san_pham with id: {}", id);
        sanPhamRepository.deleteById(id);
    }
}
