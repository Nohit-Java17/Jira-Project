package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface TheoDoiService {
    public List<TheoDoi> getDsTheoDoi();

    public TheoDoi getTheoDoi(int id);

    public TheoDoi getTheoDoi(String email);

    public TheoDoi saveTheoDoi(TheoDoi theoDoi);

    public void deleteTheoDoi(int id);
}
