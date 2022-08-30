package com.nohit.jira_project.service.Impl;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface NguoiNhanServiceImp {
    public List<NguoiNhan> getDsNguoiNhan();

    public NguoiNhan getNguoiNhanById(int id);

    public void saveNguoiNhan(NguoiNhan nguoiNhan);

    public void deleteNguoiNhan(int id);
}
