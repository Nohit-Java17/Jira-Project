package com.nohit.jira_project.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.Impl.*;

@Service
public class PhanHoiService implements PhanHoiServiceImpl {
    @Autowired
    private PhanHoiRepository phanHoiRepository;

    @Override
    public List<PhanHoi> getDsPhanHoi() {
        // TODO Auto-generated method stub
        return phanHoiRepository.findAll();
    }

    @Override
    public PhanHoi getPhanHoiById(int id) {
        // TODO Auto-generated method stub
        return phanHoiRepository.findById(id).orElse(null);
    }

    @Override
    public void savePhanHoi(PhanHoi phanHoi) {
        // TODO Auto-generated method stub
        phanHoiRepository.save(phanHoi);
    }

    @Override
    public void deletePhanHoi(int id) {
        // TODO Auto-generated method stub
        phanHoiRepository.deleteById(id);
    }
}
