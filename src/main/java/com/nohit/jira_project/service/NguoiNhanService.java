package com.nohit.jira_project.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.Impl.*;

@Service
public class NguoiNhanService implements NguoiNhanServiceImpl {
    @Autowired
    private NguoiNhanRepository nguoiNhanRepository;

    @Override
    public List<NguoiNhan> getDsNguoiNhan() {
        // TODO Auto-generated method stub
        return nguoiNhanRepository.findAll();
    }

    @Override
    public NguoiNhan getNguoiNhanById(int id) {
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
