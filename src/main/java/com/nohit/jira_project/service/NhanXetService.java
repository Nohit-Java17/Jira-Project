package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface NhanXetService {
    public List<NhanXet> getDsNhanXet();

    public NhanXet getNhanXet(int id);

    public void saveNhanXet(NhanXet nhanXet);

    public void deleteNhanXet(int id);
}
