package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface NguoiNhanService {
    public Iterable<NguoiNhan> getDsNguoiNhan();

    public NguoiNhan getNguoiNhan(int id);

    public NguoiNhan saveNguoiNhan(NguoiNhan nguoiNhan);

    public void deleteNguoiNhan(int id);
}
