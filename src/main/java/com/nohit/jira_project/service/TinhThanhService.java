package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface TinhThanhService {
    public Iterable<TinhThanh> getDsTinhThanh();

    public TinhThanh getTinhThanh(int id);

    public void saveTinhThanh(TinhThanh tinhThanh);

    public void deleteTinhThanh(int id);

}
