package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface PhanHoiService {
    public List<PhanHoi> getDsPhanHoi();

    public PhanHoi getPhanHoi(int id);

    public void savePhanHoi(PhanHoi phanHoi);

    public void deletePhanHoi(int id);
}
