package com.nohit.jira_project.service.Impl;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface PhiVanChuyenServiceImpl {
    public List<PhiVanChuyen> getDsPhiVanChuyen();

    public PhiVanChuyen getPhiVanChuyenById(int id);

    public void savePhiVanChuyen(PhiVanChuyen phiVanChuyen);

    public void deletePhiVanChuyen(int id);
}
