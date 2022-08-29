package com.nohit.jira_project.service;

import java.util.List;

import com.nohit.jira_project.model.PhiVanChuyen;

public interface PhiVanChuyenServiceImp {
	public List<PhiVanChuyen> getDsPhiVanChuyen();
	public PhiVanChuyen getPhiVanChuyenById(int id);
	public void savePhiVanChuyen(PhiVanChuyen phiVanChuyen);
	public void deletePhiVanChuyen(int id);
}
