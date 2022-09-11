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
public class ThuPhanHoiServiceImpl implements ThuPhanHoiService {
    @Autowired
    private ThuPhanHoiRepository phanHoiRepository;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private TextUtil textUtil;

    @Override
    public List<ThuPhanHoi> getDsThuPhanHoi() {
        log.info("Fetching all thu_phan_hoi");
        return phanHoiRepository.findAll();
    }

    @Override
    public ThuPhanHoi getThuPhanHoi(int id) {
        log.info("Fetching thu_phan_hoi with id {}", id);
        return phanHoiRepository.findById(id).orElse(null);
    }

    @Override
    public ThuPhanHoi saveThuPhanHoi(ThuPhanHoi thuPhanHoi) {
        thuPhanHoi.setEmail(stringUtil.parseEmail(thuPhanHoi.getEmail()));
        thuPhanHoi.setHoTen(stringUtil.parseName(thuPhanHoi.getHoTen()));
        thuPhanHoi.setNoiDung(textUtil.parseToLegalText(thuPhanHoi.getNoiDung()));
        log.info("Saving thu_phan_hoi with email: {}", thuPhanHoi.getEmail());
        return phanHoiRepository.save(thuPhanHoi);
    }

    @Override
    public void deleteThuPhanHoi(int id) {
        log.info("Deleting thu_phan_hoi with id: {}", id);
        phanHoiRepository.deleteById(id);
    }
}
