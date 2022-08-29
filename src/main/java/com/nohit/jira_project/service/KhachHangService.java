package com.nohit.jira_project.service;

import java.util.*;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;

import lombok.extern.slf4j.*;

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

    @Override
    public KhachHang findByemail(String email) {
        // TODO Auto-generated method stub
        return khachHangRepository.findByemail(email);
    }
	
	
}
