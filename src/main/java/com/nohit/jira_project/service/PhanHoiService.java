package com.nohit.jira_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nohit.jira_project.model.PhanHoi;
import com.nohit.jira_project.repository.PhanHoiRepository;

@Service
public class PhanHoiService implements PhanHoiServiceImp{
	@Autowired
	PhanHoiRepository phanHoiRepository;

	@Override
	public List<PhanHoi> getDsPhanHoi() {
		// TODO Auto-generated method stub
		return phanHoiRepository.findAll();
	}

	@Override
	public PhanHoi getPhanHoiById(int id) {
		// TODO Auto-generated method stub
		return phanHoiRepository.findById(id).orElse(null);
	}

	@Override
	public void savePhanHoi(PhanHoi phanHoi) {
		// TODO Auto-generated method stub
		phanHoiRepository.save(phanHoi);
	}

	@Override
	public void deletePhanHoi(int id) {
		// TODO Auto-generated method stub
		phanHoiRepository.deleteById(id);
	}
}
