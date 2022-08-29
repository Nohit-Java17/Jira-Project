package com.nohit.jira_project.service;

import java.util.List;

import com.nohit.jira_project.model.NguoiNhan;

public interface NguoiNhanServiceImp {
	public List<NguoiNhan> getDsNguoiNhan();
	public NguoiNhan getNguoiNhanById(int id);
	public void saveNguoiNhan(NguoiNhan nguoiNhan);
	public void deleteNguoiNhan(int id);
}
