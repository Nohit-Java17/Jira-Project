package com.nohit.jira_project.service.Impl;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface PhanHoiServiceImp {
    public List<PhanHoi> getDsPhanHoi();

    public PhanHoi getPhanHoiById(int id);

    public void savePhanHoi(PhanHoi phanHoi);

    public void deletePhanHoi(int id);
}
