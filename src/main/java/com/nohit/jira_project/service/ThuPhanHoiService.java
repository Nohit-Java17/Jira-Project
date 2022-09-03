package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface ThuPhanHoiService {
    public Iterable<ThuPhanHoi> getDsThuPhanHoi();

    public ThuPhanHoi getThuPhanHoi(int id);

    public void saveThuPhanHoi(ThuPhanHoi phanHoi);

    public void deleteThuPhanHoi(int id);
}
