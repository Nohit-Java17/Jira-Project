package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface NhanXetService {
    public Iterable<NhanXet> getDsNhanXet();

    public NhanXet getNhanXet(int id);

    public void saveNhanXet(NhanXet nhanXet);

    public void deleteNhanXet(int id);
}
