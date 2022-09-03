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
public class TinhThanhServiceImpl implements TinhThanhService {
    @Autowired
    private TinhThanhRepository tinhThanhRepository;

    @Override
    public List<TinhThanh> getDsTinhThanh() {
        log.info("Fetching all tinh_thanh");
        return tinhThanhRepository.findAll();
    }

    @Override
    public TinhThanh getTinhThanh(int id) {
        log.info("Fetching tinh_thanh with id {}", id);
        return tinhThanhRepository.findById(id).orElse(null);
    }

    @Override
    public void saveTinhThanh(TinhThanh tinhThanh) {
        log.info("Saving tinh_thanh with name: {}", tinhThanh.getTen());
        tinhThanhRepository.save(tinhThanh);
    }

    @Override
    public void deleteTinhThanh(int id) {
        log.info("Deleting tinh_thanh with id: {}", id);
        tinhThanhRepository.deleteById(id);
    }
}
