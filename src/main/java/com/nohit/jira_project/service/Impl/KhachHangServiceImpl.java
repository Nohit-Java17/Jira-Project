package com.nohit.jira_project.service.Impl;

import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.StringUtil;

import lombok.extern.slf4j.*;
import net.bytebuddy.utility.RandomString;

import static com.nohit.jira_project.constant.AttributeConstant.*;
import static java.util.Collections.*;

import java.io.UnsupportedEncodingException;

@Service
@Transactional
@Slf4j
public class KhachHangServiceImpl implements KhachHangService, UserDetailsService {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    StringUtil stringUtil;
    
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public List<KhachHang> getDsKhachHang() {
        // TODO Auto-generated method stub
        return khachHangRepository.findAll();
    }

    @Override
    public KhachHang getKhachHang(int id) {
        // TODO Auto-generated method stub
        return khachHangRepository.findById(id).orElse(null);
    }

    @Override
    public KhachHang getKhachHang(String email) {
        // TODO Auto-generated method stub
        return khachHangRepository.findByEmail(email);
    }

    @Override
    public void saveKhachHang(KhachHang khachHang) {
        // TODO Auto-generated method stub
    	khachHang.setMatKhau(passwordEncoder.encode(stringUtil.removeWhiteSpaceBeginAndEnd(khachHang.getMatKhau())));
    	log.info("Saving user with email: {}", khachHang.getEmail());
        khachHangRepository.save(khachHang);
    }

    @Override
    public void deleteKhachHang(int id) {
        // TODO Auto-generated method stub
        khachHangRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Query data of user
        var user = khachHangRepository.findByEmail(username);
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

	@Override
	public void resetPassword(String email) throws UnsupportedEncodingException, MessagingException{
		// TODO Auto-generated method stub
		String randomCode = RandomString.make(13);
		
		KhachHang khachHang = getKhachHang(email);
		khachHang.setEmail(email);
		khachHang.setMatKhau(passwordEncoder.encode(stringUtil.removeWhiteSpaceBeginAndEnd(randomCode)));
		khachHangRepository.save(khachHang);
		
		String toAddress = email;
	    String fromAddress = "nohit@gmail.com";
	    String senderName = "Nohit Shop";
	    String subject = "Reset mật khẩu";
	    String content = ""
	            + "Mật khẩu mới của quý khách là:<br>"
	            + "<h3>"+randomCode+"</h3>";
	     
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	}
}
