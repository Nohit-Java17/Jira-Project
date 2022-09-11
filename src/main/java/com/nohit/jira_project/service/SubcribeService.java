package com.nohit.jira_project.service;

import java.util.*;

import com.nohit.jira_project.model.*;

public interface SubcribeService {
    public List<Subcribe> getDsSubcribe();

    public Subcribe getSubcribe(int id);

    public Subcribe getSubcribe(String email);

    public Subcribe saveSubcribe(Subcribe subcribe);

    public void deleteSubcribe(int id);
}
