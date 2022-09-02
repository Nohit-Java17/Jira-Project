package com.nohit.jira_project.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nohit.jira_project.model.ChiTietGioHang;
import com.nohit.jira_project.repository.ChiTietGioHangRepository;
import com.nohit.jira_project.service.ChiTietGioHangService;

@Service
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService{
	
	@Autowired
	ChiTietGioHangRepository chiTietGioHangRepository;

	@Override
	public List<ChiTietGioHang> getDsChiTietGioHang() {
		// TODO Auto-generated method stub
		return chiTietGioHangRepository.findAll();
	}

	@Override
	public ChiTietGioHang getChiTietGioHang(int id) {
		// TODO Auto-generated method stub
		return chiTietGioHangRepository.findById(id).orElse(null);
	}

	@Override
	public void saveChiTietGioHang(ChiTietGioHang chiTietGioHang) {
		// TODO Auto-generated method stub
		chiTietGioHangRepository.save(chiTietGioHang);
	}

	@Override
	public void deleteChiTietGioHang(int id) {
		// TODO Auto-generated method stub
		chiTietGioHangRepository.deleteById(id);
	}

}
