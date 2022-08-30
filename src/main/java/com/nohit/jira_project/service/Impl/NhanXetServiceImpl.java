package com.nohit.jira_project.service.Impl;

import java.util.List;

import com.nohit.jira_project.model.NhanXet;

public interface NhanXetServiceImp {
    public List<NhanXet> getDsNhanXet();

    public NhanXet getNhanXetById(int id);

    public void saveNhanXet(NhanXet nhanXet);

    public void deleteNhanXet(int id);
}
