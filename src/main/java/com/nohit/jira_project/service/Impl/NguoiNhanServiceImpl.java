package com.nohit.jira_project.service.Impl;

import java.util.*;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import lombok.extern.slf4j.*;

@Service
@Transactional
@Slf4j
public class NguoiNhanServiceImpl implements NguoiNhanService {
    @Autowired
    private NguoiNhanRepository nguoiNhanRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Autowired
    private AddressUtil addressUtil;

    @Override
    public List<NguoiNhan> getDsNguoiNhan() {
        log.info("Fetching all nguoi_nhan");
        return nguoiNhanRepository.findAll();
    }

    @Override
    public NguoiNhan getNguoiNhan(int id) {
        log.info("Fetching nguoi_nhan with id {}", id);
        return nguoiNhanRepository.findById(id).orElse(null);
    }

    @Override
    public NguoiNhan saveNguoiNhan(NguoiNhan nguoiNhan) {
        nguoiNhan.setHoTen(stringUtil.parseName(nguoiNhan.getHoTen()));
        nguoiNhan.setDiaChi(addressUtil.parseToLegalAddress(nguoiNhan.getDiaChi()));
        nguoiNhan.setXaPhuong(addressUtil.parseToLegalAddress(nguoiNhan.getXaPhuong()));
        nguoiNhan.setHuyenQuan(addressUtil.parseToLegalAddress(nguoiNhan.getHuyenQuan()));
        nguoiNhan.setGhiChu(textUtil.parseToLegalText(nguoiNhan.getGhiChu()));
        log.info("Saving nguoi_nhan with name: {}", nguoiNhan.getHoTen());
        return nguoiNhanRepository.save(nguoiNhan);
    }

    @Override
    public void deleteNguoiNhan(int id) {
        log.info("Deleting nguoi_nhan with id: {}", id);
        nguoiNhanRepository.deleteById(id);
    }
}
