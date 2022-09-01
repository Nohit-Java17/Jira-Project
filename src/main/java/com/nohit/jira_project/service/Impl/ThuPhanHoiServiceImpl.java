package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

@Service
public class ThuPhanHoiServiceImpl implements ThuPhanHoiService {
    @Autowired
    private ThuPhanHoiRepository phanHoiRepository;

    @Override
    public List<ThuPhanHoi> getDsPhanHoi() {
        // TODO Auto-generated method stub
        return phanHoiRepository.findAll();
    }

    @Override
    public ThuPhanHoi getPhanHoi(int id) {
        // TODO Auto-generated method stub
        return phanHoiRepository.findById(id).orElse(null);
    }

    @Override
    public void savePhanHoi(ThuPhanHoi phanHoi) {
        // TODO Auto-generated method stub
        phanHoiRepository.save(phanHoi);
    }

    @Override
    public void deletePhanHoi(int id) {
        // TODO Auto-generated method stub
        phanHoiRepository.deleteById(id);
    }
}
