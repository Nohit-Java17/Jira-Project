package com.nohit.jira_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nohit.jira_project.model.SanPham;
import com.nohit.jira_project.repository.SanPhamRepository;

@Service
public class SanPhamService implements SanPhamServiceImp{
	@Autowired
	SanPhamRepository sanPhamRepository;
	
	@Override
	public List<SanPham> getDsSanPham() {
		// TODO Auto-generated method stub
		return sanPhamRepository.findAll();
	}

	@Override
	public SanPham getSanPhamById(int id) {
		// TODO Auto-generated method stub
		return sanPhamRepository.findById(id).orElse(null);
	}

	@Override
	public void saveSanPham(SanPham sanPham) {
		// TODO Auto-generated method stub
		sanPhamRepository.save(sanPham);
	}

	@Override
	public void deleteSanPham(int id) {
		// TODO Auto-generated method stub
		sanPhamRepository.deleteById(id);
	}

}
