package com.nohit.jira_project.service.Impl;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

@Service
@Slf4j
public class NguoiNhanServiceImpl implements NguoiNhanService {
    @Autowired
    private NguoiNhanRepository nguoiNhanRepository;

    @Override
    public Iterable<NguoiNhan> getDsNguoiNhan() {
        log.info("Fetching all nguoi_nhan");
        return nguoiNhanRepository.findAll();
    }

    @Override
    public NguoiNhan getNguoiNhan(int id) {
        log.info("Fetching nguoi_nhan with id {}", id);
        return nguoiNhanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveNguoiNhan(NguoiNhan nguoiNhan) {
        log.info("Saving nguoi_nhan with name: {}", nguoiNhan.getHoTen());
        nguoiNhanRepository.save(nguoiNhan);
    }

    @Override
    public void deleteNguoiNhan(int id) {
        log.info("Deleting nguoi_nhan with id: {}", id);
        nguoiNhanRepository.deleteById(id);
    }
}
