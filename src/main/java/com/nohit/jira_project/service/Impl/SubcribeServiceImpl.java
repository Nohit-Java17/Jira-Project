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
public class SubcribeServiceImpl implements SubcribeService {
    @Autowired
    private SubcribeRepository subcribeRepository;

    @Autowired
    private StringUtil stringUtil;

    @Override
    public List<Subcribe> getDsSubcribe() {
        log.info("Fetching all subcribe");
        return subcribeRepository.findAll();
    }

    @Override
    public Subcribe getSubcribe(int id) {
        log.info("Fetching subcribe with id {}", id);
        return subcribeRepository.findById(id).orElse(null);
    }

    @Override
    public Subcribe getSubcribe(String email) {
        email = stringUtil.parseEmail(email);
        log.info("Fetching subcribe with email: {}", email);
        return subcribeRepository.findByEmail(email);
    }

    @Override
    public Subcribe saveSubcribe(Subcribe subcribe) {
        subcribe.setEmail(stringUtil.parseEmail(subcribe.getEmail()));
        log.info("Saving subcribe with email: {}", subcribe.getEmail());
        return subcribeRepository.save(subcribe);
    }

    @Override
    public void deleteSubcribe(int id) {
        log.info("Deleting subcribe with id: {}", id);
        subcribeRepository.deleteById(id);
    }
}
