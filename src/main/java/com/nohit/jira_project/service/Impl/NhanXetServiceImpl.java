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
public class NhanXetServiceImpl implements NhanXetService {
    @Autowired
    private NhanXetRepository nhanXetRepository;

    @Override
    public List<NhanXet> getDsNhanXet() {
        log.info("Fetching all nhan_xet");
        return nhanXetRepository.findAll();
    }

    @Override
    public NhanXet getNhanXet(int id) {
        log.info("Fetching nhan_xet with id {}", id);
        return nhanXetRepository.findById(id).orElse(null);
    }

    @Override
    public void saveNhanXet(NhanXet nhanXet) {
        log.info("Saving nhan_xet with id: {}", nhanXet.getId());
        nhanXetRepository.save(nhanXet);
    }

    @Override
    public void deleteNhanXet(int id) {
        log.info("Deleting nhan_xet with id: {}", id);
        nhanXetRepository.deleteById(id);
    }
}
