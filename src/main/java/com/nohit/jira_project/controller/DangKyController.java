package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.common.Bean.*;
import static com.nohit.jira_project.constant.ApplicationConstant.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(REGISTER_VIEW)
public class DangKyController {
    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;

    // Load register
    @GetMapping("")
    public ModelAndView register() {
        // check login
        if (authenticationUtil.getAccount() != null) {
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        } else {
            var mav = new ModelAndView(REGISTER_TEMP);
            mav.addObject(TITLE_PARAM, DANG_KY);
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Register
    @PostMapping("")
    public String register(KhachHang khachHang) {
        _isMsgShow = true;
        // check email is already exist
        if (khachHangService.getKhachHang(khachHang.getEmail()) != null) {
            _msg = "Email này đã được đăng ký!";
            return REDIRECT_PREFIX + REGISTER_VIEW;
        } else {
            khachHang.setIdTinhThanh(DEFAULT_PROVINCE);
            khachHang.setVaiTro(DEFAULT_ROLE);
            khachHang = khachHangService.saveKhachHang(khachHang);
            gioHangService.createGioHang(khachHang);
            creditCardService.createCreditCard(khachHang);
            _msg = "Tài khoản đã được đăng ký thành công!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        }
    }
}
