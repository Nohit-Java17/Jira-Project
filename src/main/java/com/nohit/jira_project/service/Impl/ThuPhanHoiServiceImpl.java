package com.nohit.jira_project.service.Impl;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

@Service
@Slf4j
public class ThuPhanHoiServiceImpl implements ThuPhanHoiService {
    @Autowired
    private ThuPhanHoiRepository phanHoiRepository;

    @Override
    public Iterable<ThuPhanHoi> getDsThuPhanHoi() {
        log.info("Fetching all thu_phan_hoi");
        return phanHoiRepository.findAll();
    }

    @Override
    public ThuPhanHoi getThuPhanHoi(int id) {
        log.info("Fetching thu_phan_hoi with id {}", id);
        return phanHoiRepository.findById(id).orElse(null);
    }

    @Override
    public void saveThuPhanHoi(ThuPhanHoi thuPhanHoi) {
        log.info("Saving thu_phan_hoi with email: {}", thuPhanHoi.getEmail());
        phanHoiRepository.save(thuPhanHoi);
    }

    @Override
    public void deleteThuPhanHoi(int id) {
        log.info("Deleting thu_phan_hoi with id: {}", id);
        phanHoiRepository.deleteById(id);
    }
}
