package com.nohit.jira_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nohit.jira_project.model.DonHang;
import com.nohit.jira_project.repository.DonHangRepository;

@Service
public class DonHangService implements DonHangServiceImp{
	@Autowired
	DonHangRepository donHangRepository;

	@Override
	public List<DonHang> getDsDonHang() {
		// TODO Auto-generated method stub
		return donHangRepository.findAll();
	}

	@Override
	public DonHang getDonHangById(int id) {
		// TODO Auto-generated method stub
		return donHangRepository.findById(id).orElse(null);
	}

	@Override
	public void saveDonHang(DonHang donHang) {
		// TODO Auto-generated method stub
		donHangRepository.save(donHang);
	}

	@Override
	public void deleteDonHang(int id) {
		// TODO Auto-generated method stub
		donHangRepository.deleteById(id);
	}
	
}
