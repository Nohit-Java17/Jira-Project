package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface PhiVanChuyenService {
    public List<PhiVanChuyen> getDsPhiVanChuyen();

    public PhiVanChuyen getPhiVanChuyen(int id);

    public void savePhiVanChuyen(PhiVanChuyen phiVanChuyen);

    public void deletePhiVanChuyen(int id);
}
