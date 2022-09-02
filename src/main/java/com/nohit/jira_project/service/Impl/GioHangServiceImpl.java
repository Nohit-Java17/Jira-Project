package com.nohit.jira_project.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nohit.jira_project.model.GioHang;
import com.nohit.jira_project.repository.GioHangRepository;
import com.nohit.jira_project.service.GioHangService;

@Service
public class GioHangServiceImpl implements GioHangService{
	@Autowired
	GioHangRepository gioHangRepository;
	
	@Override
	public List<GioHang> getDsGioHang() {
		// TODO Auto-generated method stub
		return gioHangRepository.findAll();
	}

	@Override
	public GioHang getGioHang(int id) {
		// TODO Auto-generated method stub
		return gioHangRepository.findById(id).orElse(null);
	}

	@Override
	public void saveGioHang(GioHang gioHang) {
		// TODO Auto-generated method stub
		gioHangRepository.save(gioHang);
	}

	@Override
	public void deleteGioHang(int id) {
		// TODO Auto-generated method stub
		gioHangRepository.deleteById(id);
	}

}
