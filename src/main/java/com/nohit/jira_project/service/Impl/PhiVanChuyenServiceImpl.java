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
public class PhiVanChuyenServiceImpl implements PhiVanChuyenService {
    @Autowired
    private PhiVanChuyenRepository phiVanChuyenRepository;

    @Override
    public List<PhiVanChuyen> getDsPhiVanChuyen() {
        log.info("Fetching all phi_van_chuyen");
        return phiVanChuyenRepository.findAll();
    }

    @Override
    public PhiVanChuyen getPhiVanChuyen(int id) {
        log.info("Fetching phi_van_chuyen with id {}", id);
        return phiVanChuyenRepository.findById(id).orElse(null);
    }

    @Override
    public void savePhiVanChuyen(PhiVanChuyen phiVanChuyen) {
        log.info("Saving phi_van_chuyen with name: {}", phiVanChuyen.getTinhThanh());
        phiVanChuyenRepository.save(phiVanChuyen);
    }

    @Override
    public void deletePhiVanChuyen(int id) {
        log.info("Deleting phi_van_chuyen with id: {}", id);
        phiVanChuyenRepository.deleteById(id);
    }
}
