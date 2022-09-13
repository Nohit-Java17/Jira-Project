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
public class NhanXetServiceImpl implements NhanXetService {
    @Autowired
    private NhanXetRepository nhanXetRepository;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<NhanXet> getDsNhanXet() {
        log.info("Fetching all nhan_xet");
        return nhanXetRepository.findAll();
    }

    @Override
    public NhanXet getNhanXet(NhanXetId id) {
        log.info("Fetching nhan_xet with id {}", id);
        return nhanXetRepository.findById(id).orElse(null);
    }

    @Override
    public NhanXet saveNhanXet(NhanXet nhanXet) {
        nhanXet.setBinhLuan(textUtil.parseToLegalText(nhanXet.getBinhLuan()));
        log.info("Saving nhan_xet with id: {}", nhanXet.getId());
        return nhanXetRepository.save(nhanXet);
    }

    @Override
    public void deleteNhanXet(NhanXetId id) {
        log.info("Deleting nhan_xet with id: {}", id);
        nhanXetRepository.deleteById(id);
    }
}
