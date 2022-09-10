package com.nohit.jira_project.service;

import com.nohit.jira_project.model.*;

public interface SubcribeService {
    public Iterable<Subcribe> getDsSubcribe();

    public Subcribe getSubcribe(int id);

    public Subcribe getSubcribe(String email);

    public void saveSubcribe(Subcribe subcribe);

    public void deleteSubcribe(int id);
}
