package com.nohit.jira_project.service.Impl;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

@Service
@Slf4j
public class GioHangServiceImpl implements GioHangService {
    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public Iterable<GioHang> getDsGioHang() {
        log.info("Fetching all gio_hang");
        return gioHangRepository.findAll();
    }

    @Override
    public GioHang getGioHang(int id) {
        log.info("Fetching gio_hang with id {}", id);
        return gioHangRepository.findById(id).orElse(null);
    }

    @Override
    public void saveGioHang(GioHang gioHang) {
        log.info("Saving gio_hang with id: {}", gioHang.getId());
        gioHangRepository.save(gioHang);
    }

    @Override
    public void deleteGioHang(int id) {
        log.info("Deleting gio_hang with id: {}", id);
        gioHangRepository.deleteById(id);
    }

    @Override
    public GioHang createGioHang(int id) {
        var gioHang = new GioHang();
        gioHang.setId(id);
        log.info("Create gio_hang with id: {}", id);
        return gioHangRepository.save(gioHang);
    }
}
