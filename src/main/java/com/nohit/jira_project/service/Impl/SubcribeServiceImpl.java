package com.nohit.jira_project.service.Impl;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

@Service
@Slf4j
public class SubcribeServiceImpl implements SubcribeService {
    @Autowired
    private SubcribeRepository subcribeRepository;

    @Override
    public Iterable<Subcribe> getDsSubcribe() {
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
        log.info("Fetching subcribe with email: {}", email);
        return subcribeRepository.findByEmail(email);
    }

    @Override
    public void saveSubcribe(Subcribe subcribe) {
        log.info("Saving subcribe with email: {}", subcribe.getEmail());
        subcribeRepository.save(subcribe);
        
    }

    @Override
    public void deleteSubcribe(int id) {
        log.info("Deleting subcribe with id: {}", id);
        subcribeRepository.deleteById(id);
    }   
}
