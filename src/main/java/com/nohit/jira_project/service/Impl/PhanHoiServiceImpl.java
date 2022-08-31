package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

@Service
public class PhanHoiServiceImpl implements PhanHoiService {
    @Autowired
    private PhanHoiRepository phanHoiRepository;

    @Override
    public List<PhanHoi> getDsPhanHoi() {
        // TODO Auto-generated method stub
        return phanHoiRepository.findAll();
    }

    @Override
    public PhanHoi getPhanHoi(int id) {
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
