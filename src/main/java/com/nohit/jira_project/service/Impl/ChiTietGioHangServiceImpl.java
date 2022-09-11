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
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {
    @Autowired
    private ChiTietGioHangRepository chiTietGioHangRepository;

    @Override
    public List<ChiTietGioHang> getDsChiTietGioHang() {
        log.info("Fetching all chi_tiet_gio_hang");
        return chiTietGioHangRepository.findAll();
    }

    @Override
    public ChiTietGioHang getChiTietGioHang(ChiTietGioHangId id) {
        log.info("Fetching chi_tiet_gio_hang with id: {}", id);
        return chiTietGioHangRepository.findById(id).orElse(null);
    }

    @Override
    public ChiTietGioHang saveChiTietGioHang(ChiTietGioHang chiTietGioHang) {
        chiTietGioHang.setTongTienSanPham(chiTietGioHang.getSoLuongSanPham() * chiTietGioHang.getGiaBanSanPham());
        log.info("Saving chi_tiet_gio_hang with id: {}", chiTietGioHang.getId());
        return chiTietGioHangRepository.save(chiTietGioHang);
    }

    @Override
    public void deleteChiTietGioHang(ChiTietGioHangId id) {
        log.info("Deleting chi_tiet_gio_hang with id: {}", id);
        chiTietGioHangRepository.deleteById(id);
    }
}
