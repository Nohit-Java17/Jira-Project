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
public class TheoDoiServiceImpl implements TheoDoiService {
    @Autowired
    private TheoDoiRepository theoDoiRepository;

    @Autowired
    private StringUtil stringUtil;

    @Override
    public List<TheoDoi> getDsTheoDoi() {
        log.info("Fetching all theo_doi");
        return theoDoiRepository.findAll();
    }

    @Override
    public TheoDoi getTheoDoi(int id) {
        log.info("Fetching theo_doi with id {}", id);
        return theoDoiRepository.findById(id).orElse(null);
    }

    @Override
    public TheoDoi getTheoDoi(String email) {
        email = stringUtil.parseEmail(email);
        log.info("Fetching theo_doi with email: {}", email);
        return theoDoiRepository.findByEmail(email);
    }

    @Override
    public TheoDoi saveTheoDoi(TheoDoi theoDoi) {
        theoDoi.setEmail(stringUtil.parseEmail(theoDoi.getEmail()));
        log.info("Saving theo_doi with email: {}", theoDoi.getEmail());
        return theoDoiRepository.save(theoDoi);
    }

    @Override
    public void deleteTheoDoi(int id) {
        log.info("Deleting theo_doi with id: {}", id);
        theoDoiRepository.deleteById(id);
    }
}
