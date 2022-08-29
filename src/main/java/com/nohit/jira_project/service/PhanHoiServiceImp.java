package com.nohit.jira_project.service;

import java.util.List;

import com.nohit.jira_project.model.PhanHoi;

public interface PhanHoiServiceImp {
	public List<PhanHoi> getDsPhanHoi();
	public PhanHoi getPhanHoiById(int id);
	public void savePhanHoi(PhanHoi phanHoi);
	public void deletePhanHoi(int id);
}
