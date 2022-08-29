package com.nohit.jira_project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nohit.jira_project.model.KhachHang;
import com.nohit.jira_project.repository.KhachHangRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class KhachHangService implements KhachHangServiceImp{
	@Autowired
	KhachHangRepository khachHangRepository;

	@Override
	public List<KhachHang> getDsKhachHang() {
		// TODO Auto-generated method stub
		return khachHangRepository.findAll();
	}

	@Override
	public KhachHang getKhachHangById(int id) {
		// TODO Auto-generated method stub
		return khachHangRepository.findById(id).orElse(null);
	}

	@Override
	public void saveKhachHang(KhachHang khachHang) {
		// TODO Auto-generated method stub
		khachHangRepository.save(khachHang);
	}

	@Override
	public void deleteKhachHang(int id) {
		// TODO Auto-generated method stub
		khachHangRepository.deleteById(id);
	}
	
	
}
