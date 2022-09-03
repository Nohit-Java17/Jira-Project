package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface TinhThanhService {
    public List<TinhThanh> getDsTinhThanh();

    public TinhThanh getTinhThanh(int id);

    public void saveTinhThanh(TinhThanh tinhThanh);

    public void deleteTinhThanh(int id);
}
