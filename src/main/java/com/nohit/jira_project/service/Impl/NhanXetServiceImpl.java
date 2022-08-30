package com.nohit.jira_project.service.Impl;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface NhanXetServiceImpl {
    public List<NhanXet> getDsNhanXet();

    public NhanXet getNhanXetById(int id);

    public void saveNhanXet(NhanXet nhanXet);

    public void deleteNhanXet(int id);
}
