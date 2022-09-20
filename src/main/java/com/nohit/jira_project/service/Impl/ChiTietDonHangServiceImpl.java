package com.nohit.jira_project.service.Impl;

import java.util.*;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

@Service
@Transactional
@Slf4j
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    @Override
    public List<ChiTietDonHang> getDsChiTietDonHang() {
        log.info("Fetching all chi_tiet_don_hang");
        return chiTietDonHangRepository.findAll();
    }

    @Override
    public ChiTietDonHang getChiTietDonHang(ChiTietDonHangId id) {
        log.info("Fetching chi_tiet_don_hang with id {}", id);
        return chiTietDonHangRepository.findById(id).orElse(null);
    }

    @Override
    public ChiTietDonHang saveChiTietDonHang(ChiTietDonHang chiTietDonHang) {
        log.info("Saving chi_tiet_don_hang with id: {}", chiTietDonHang.getId());
        return chiTietDonHangRepository.save(chiTietDonHang);
    }

    @Override
    public void deleteChiTietDonHang(ChiTietDonHangId id) {
        log.info("Deleting chi_tiet_don_hang with id: {}", id);
        chiTietDonHangRepository.deleteById(id);
    }
}
