package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface ThuPhanHoiService {
    public List<ThuPhanHoi> getDsPhanHoi();

    public ThuPhanHoi getPhanHoi(int id);

    public void savePhanHoi(ThuPhanHoi phanHoi);

    public void deletePhanHoi(int id);
}
