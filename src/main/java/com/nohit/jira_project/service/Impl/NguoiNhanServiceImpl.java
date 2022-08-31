package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

@Service
public class NguoiNhanServiceImpl implements NguoiNhanService {
    @Autowired
    private NguoiNhanRepository nguoiNhanRepository;

    @Override
    public List<NguoiNhan> getDsNguoiNhan() {
        // TODO Auto-generated method stub
        return nguoiNhanRepository.findAll();
    }

    @Override
    public NguoiNhan getNguoiNhan(int id) {
        // TODO Auto-generated method stub
        return nguoiNhanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveNguoiNhan(NguoiNhan nguoiNhan) {
        // TODO Auto-generated method stub
        nguoiNhanRepository.save(nguoiNhan);
    }

    @Override
    public void deleteNguoiNhan(int id) {
        // TODO Auto-generated method stub
        nguoiNhanRepository.deleteById(id);
    }
}
