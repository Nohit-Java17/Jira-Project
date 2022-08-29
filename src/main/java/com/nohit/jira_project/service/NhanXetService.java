package com.nohit.jira_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nohit.jira_project.model.NhanXet;
import com.nohit.jira_project.repository.NhanXetRepository;

@Service
public class NhanXetService implements NhanXetServiceImp{
	@Autowired
	NhanXetRepository nhanXetRepository;

	@Override
	public List<NhanXet> getDsNhanXet() {
		// TODO Auto-generated method stub
		return nhanXetRepository.findAll();
	}

	@Override
	public NhanXet getNhanXetById(int id) {
		// TODO Auto-generated method stub
		return nhanXetRepository.findById(id).orElse(null);
	}

	@Override
	public void saveNhanXet(NhanXet nhanXet) {
		// TODO Auto-generated method stub
		nhanXetRepository.save(nhanXet);
	}

	@Override
	public void deleteNhanXet(int id) {
		// TODO Auto-generated method stub
		nhanXetRepository.deleteById(id);
	}
	
	
}
