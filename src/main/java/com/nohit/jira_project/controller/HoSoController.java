package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import lombok.*;

import static com.nohit.jira_project.common.Bean.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(PROFILE_VIEW)
public class HoSoController {
    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private TinhThanhService tinhThanhService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Load profile
    @GetMapping("")
    public ModelAndView profile() {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(PROFILE_TEMP);
            mav.addObject(TITLE_PARAM, THONG_TIN);
            mav.addObject(CART_PARAM, client.getGioHang());
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(CLIENT_PARAM, client);
            mav.addObject(CREDIT_CARD_PARAM, client.getCreditCard());
            mav.addObject(PROVINCES_PARAM, tinhThanhService.getDsTinhThanh());
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Update info
    @RequestMapping(value = INFO_VIEW, method = { GET, PUT })
    public String profileInfo(KhachHang khachHang) {
        // Check current account still valid
        if (authenticationUtil.getAccount() == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            khachHangService.saveKhachHangWithoutPassword(khachHang);
            _isMsgShow = true;
            _msg = "Thông tin cơ bản đã được cập nhật thành công!";
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }

    // Update password
    @PostMapping(PASSWORD_VIEW)
    public String profilePassword(String oldPassword, String newPassword, String rePassword) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            _isMsgShow = true;
            // check valid password
            if (passwordEncoder.matches(oldPassword, client.getMatKhau()) && rePassword.equals(newPassword)) {
                khachHangService.updatePassword(client.getId(), newPassword);
                _msg = "Mật khẩu đã được cập nhật thành công!";
            } else {
                _msg = "Mật khẩu chưa chính xác!";
            }
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }

    // Update card
    @PostMapping(CARD_VIEW)
    public String profileCard(CreditCard creditCard) {
        // Check current account still valid
        if (authenticationUtil.getAccount() == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            creditCardService.saveCreditCard(creditCard);
            _isMsgShow = true;
            _msg = "Thanh toán qua thẻ được cập nhật thành công!";
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }
}
