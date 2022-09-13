package com.nohit.jira_project.service.Impl;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.Service;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import lombok.extern.slf4j.*;

import static com.nohit.jira_project.constant.AttributeConstant.*;
import static java.util.Collections.*;
import static net.bytebuddy.utility.RandomString.*;

@Service
@Transactional
@Slf4j
public class KhachHangServiceImpl implements KhachHangService, UserDetailsService {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private AddressUtil addressUtil;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = khachHangRepository.findByEmail(username);
        // check user exists
        if (user == null) {
            log.error("khach_hang not found");
            throw new UsernameNotFoundException("khach_hang not found");
        } else {
            log.info("khach_hang found: {}", username);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getMatKhau(),
                    singleton(new SimpleGrantedAuthority(ROLE_PREFIX + user.getVaiTro().toUpperCase())));
        }
    }

    @Override
    public List<KhachHang> getDsKhachHang() {
        log.info("Fetching all khach_hang");
        return khachHangRepository.findAll();
    }

    @Override
    public KhachHang getKhachHang(int id) {
        log.info("Fetching khach_hang with id {}", id);
        return khachHangRepository.findById(id).orElse(null);
    }

    @Override
    public KhachHang getKhachHang(String email) {
        email = stringUtil.parseEmail(email);
        log.info("Fetching khach_hang with email: {}", email);
        return khachHangRepository.findByEmail(email);
    }

    @Override
    public KhachHang saveKhachHang(KhachHang khachHang) {
        khachHang.setEmail(stringUtil.parseEmail(khachHang.getEmail()));
        khachHang.setMatKhau(passwordEncoder.encode(stringUtil.removeWhiteSpaceBeginAndEnd(khachHang.getMatKhau())));
        khachHang.setHoTen(stringUtil.parseName(khachHang.getHoTen()));
        khachHang.setDiaChi(addressUtil.parseToLegalAddress(khachHang.getDiaChi()));
        khachHang.setXaPhuong(addressUtil.parseToLegalAddress(khachHang.getXaPhuong()));
        khachHang.setHuyenQuan(addressUtil.parseToLegalAddress(khachHang.getHuyenQuan()));
        log.info("Saving khach_hang with email: {}", khachHang.getEmail());
        return khachHangRepository.save(khachHang);
    }

    @Override
    public void deleteKhachHang(int id) {
        log.info("Deleting khach_hang with id: {}", id);
        khachHangRepository.deleteById(id);
    }

    @Override
    public KhachHang saveKhachHangWithoutPassword(KhachHang khachHang) {
        khachHang.setEmail(stringUtil.parseEmail(khachHang.getEmail()));
        khachHang.setMatKhau(getKhachHang(khachHang.getId()).getMatKhau());
        khachHang.setHoTen(stringUtil.parseName(khachHang.getHoTen()));
        khachHang.setDiaChi(addressUtil.parseToLegalAddress(khachHang.getDiaChi()));
        khachHang.setXaPhuong(addressUtil.parseToLegalAddress(khachHang.getXaPhuong()));
        khachHang.setHuyenQuan(addressUtil.parseToLegalAddress(khachHang.getHuyenQuan()));
        log.info("Saving khach_hang with email: {}", khachHang.getEmail());
        return khachHangRepository.save(khachHang);
    }

    @Override
    public KhachHang updatePassword(int id, String password) {
        var khachHang = getKhachHang(id);
        khachHang.setMatKhau(passwordEncoder.encode(stringUtil.removeWhiteSpaceBeginAndEnd(password)));
        log.info("Update khach_hang password with email: {}", khachHang.getEmail());
        return khachHangRepository.save(khachHang);
    }

    @Override
    public KhachHang resetPassword(String email) throws UnsupportedEncodingException, MessagingException {
        email = stringUtil.parseEmail(email);
        var khachHang = getKhachHang(email);
        var randomCode = make(13);
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message);
        helper.setFrom("nohitshop@gmail.com", "Nohit Shop");
        helper.setTo(email);
        helper.setSubject("Quên mật khẩu");
        helper.setText("Mật khẩu mới của quý khách là:<br>" + "<h3>" + randomCode + "</h3>", true);
        mailSender.send(message);
        khachHang.setMatKhau(passwordEncoder.encode(stringUtil.removeWhiteSpaceBeginAndEnd(randomCode)));
        log.info("Reset khach_hang password with email: {}", email);
        return khachHangRepository.save(khachHang);
    }
}
