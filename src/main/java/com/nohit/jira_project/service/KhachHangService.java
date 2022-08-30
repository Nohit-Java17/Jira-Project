package com.nohit.jira_project.service;

import java.util.*;

import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.Impl.*;

import lombok.extern.slf4j.*;

import static com.nohit.jira_project.constant.AttributeConstant.*;
import static java.util.Collections.*;

@Service
@Transactional
@Slf4j
public class KhachHangService implements KhachHangServiceImpl, UserDetailsService {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Query data of user
        var user = khachHangRepository.findByemail(username);
        // Check user exists
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found: {}", username);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getMatKhau(),
                    singleton(new SimpleGrantedAuthority(ROLE_PREFIX + user.getVaiTro().toUpperCase())));
        }
    }

}
