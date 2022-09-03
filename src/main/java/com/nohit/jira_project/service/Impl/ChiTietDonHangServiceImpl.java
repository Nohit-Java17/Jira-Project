package com.nohit.jira_project.service.Impl;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

@Service
@Slf4j
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    @Override
    public Iterable<ChiTietDonHang> getDsChiTietDonHang() {
        log.info("Fetching all chi_tiet_don_hang");
        return chiTietDonHangRepository.findAll();
    }

    @Override
    public ChiTietDonHang getChiTietDonHang(int id) {
        log.info("Fetching chi_tiet_don_hang with id {}", id);
        return chiTietDonHangRepository.findById(id).orElse(null);
    }

    @Override
    public void saveChiTietDonHang(ChiTietDonHang chiTietDonHang) {
        log.info("Saving chi_tiet_don_hang with id: {}", chiTietDonHang.getId());
        chiTietDonHangRepository.save(chiTietDonHang);
    }

    @Override
    public void deleteChiTietDonHang(int id) {
        log.info("Deleting chi_tiet_don_hang with id: {}", id);
        chiTietDonHangRepository.deleteById(id);
    }
}
