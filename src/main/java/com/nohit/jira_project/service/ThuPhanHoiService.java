package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface ThuPhanHoiService {
    public List<ThuPhanHoi> getDsThuPhanHoi();

    public ThuPhanHoi getThuPhanHoi(int id);

    public ThuPhanHoi saveThuPhanHoi(ThuPhanHoi phanHoi);

    public void deleteThuPhanHoi(int id);
}
