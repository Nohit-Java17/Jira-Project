package com.nohit.jira_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nohit.jira_project.model.ChiTietDonHang;
import com.nohit.jira_project.repository.ChiTietDonHangRepository;

@Service
public class ChiTietDonHangService implements ChiTietDonHangServiceImp{
	@Autowired
	ChiTietDonHangRepository chiTietDonHangRepository;

	@Override
	public List<ChiTietDonHang> getDsChiTietDonHang() {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findAll();
	}

	@Override
	public ChiTietDonHang geTietDonHangById(int id) {
		// TODO Auto-generated method stub
		return chiTietDonHangRepository.findById(id).orElse(null);
	}

	@Override
	public void saveChiTietDonHang(ChiTietDonHang chiTietDonHang) {
		// TODO Auto-generated method stub
		chiTietDonHangRepository.save(chiTietDonHang);
	}

	@Override
	public void deleteChiTietDonHang(int id) {
		// TODO Auto-generated method stub
		chiTietDonHangRepository.deleteById(id);
	}
}
