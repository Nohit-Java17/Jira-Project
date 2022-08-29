package com.nohit.jira_project.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nohit.jira_project.model.NguoiNhan;
import com.nohit.jira_project.repository.NguoiNhanRepository;

@Service
public class NguoiNhanService implements NguoiNhanServiceImp{
	@Autowired
	NguoiNhanRepository nguoiNhanRepository;

	@Override
	public List<NguoiNhan> getDsNguoiNhan() {
		// TODO Auto-generated method stub
		return nguoiNhanRepository.findAll();
	}

	@Override
	public NguoiNhan getNguoiNhanById(int id) {
		// TODO Auto-generated method stub
		return nguoiNhanRepository.findById(id).orElse(null);
	}

	@Override
	public void saveNguoiNhan(NguoiNhan nguoiNhan) {
		// TODO Auto-generated method stub
		nguoiNhanRepository.save(nguoiNhan);
	}

	@Override
	public void deleteNguoiNhan(int id) {
		// TODO Auto-generated method stub
		nguoiNhanRepository.deleteById(id);
	}
	
}
