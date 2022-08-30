package com.nohit.jira_project.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.Impl.*;

@Service
public class NhanXetService implements NhanXetServiceImpl {
    @Autowired
    private NhanXetRepository nhanXetRepository;

    @Override
    public List<NhanXet> getDsNhanXet() {
        // TODO Auto-generated method stub
        return nhanXetRepository.findAll();
    }

    @Override
    public NhanXet getNhanXetById(int id) {
        // TODO Auto-generated method stub
        return nhanXetRepository.findById(id).orElse(null);
    }

    @Override
    public void saveNhanXet(NhanXet nhanXet) {
        // TODO Auto-generated method stub
        nhanXetRepository.save(nhanXet);
    }

    @Override
    public void deleteNhanXet(int id) {
        // TODO Auto-generated method stub
        nhanXetRepository.deleteById(id);
    }

}
