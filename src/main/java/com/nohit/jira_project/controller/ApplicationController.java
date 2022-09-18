package com.nohit.jira_project.controller;

import java.io.*;

import javax.mail.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.ApplicationConstant.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping("")
public class ApplicationController {
    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;

    // Fields
    private String mMsg;
    private boolean mIsMsgShow;
    private boolean mIsByPass;

    // Load register
    @GetMapping(REGISTER_VIEW)
    public ModelAndView register() {
        // check login
        if (!isDirect()) {
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        } else {
            var mav = new ModelAndView(REGISTER_TEMP);
            mav.addObject(TITLE_PARAM, DANG_KY);
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            mIsByPass = false;
            return mav;
        }
    }

    // Register
    @PostMapping(REGISTER_VIEW)
    public String register(KhachHang khachHang) {
        mIsMsgShow = true;
        mIsByPass = true;
        // check email is already exist
        if (khachHangService.getKhachHang(khachHang.getEmail()) != null) {
            mMsg = "Email này đã được đăng ký!";
            return REDIRECT_PREFIX + REGISTER_VIEW;
        } else {
            khachHang.setIdTinhThanh(DEFAULT_PROVINCE);
            khachHang.setVaiTro(DEFAULT_ROLE);
            khachHang = khachHangService.saveKhachHang(khachHang);
            gioHangService.createGioHang(khachHang);
            mMsg = "Tài khoản đã được đăng ký thành công!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        }
    }

    // Load login
    @GetMapping(LOGIN_VIEW)
    public ModelAndView login(boolean error) {
        // check login
        if (!isDirect()) {
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        } else {
            var mav = new ModelAndView(LOGIN_TEMP);
            // login failed
            if (error) {
                mIsMsgShow = true;
                mMsg = "Tài khoản đăng nhập chưa đúng!";
            }
            mav.addObject(TITLE_PARAM, DANG_NHAP);
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            mIsByPass = false;
            return mav;
        }
    }

    // Load password-reset
    @GetMapping(PASSWORD_RESET_VIEW)
    public ModelAndView resetPassword() {
        var mav = new ModelAndView(PASSWORD_RESET_TEMP);
        mav.addObject(TITLE_PARAM, MAT_KHAU);
        mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
        return mav;
    }

    // Reset password
    @PostMapping(PASSWORD_RESET_VIEW)
    public String resetPassword(String email) throws UnsupportedEncodingException, MessagingException {
        mIsMsgShow = true;
        // check email is already exist
        if (khachHangService.getKhachHang(email) == null) {
            mMsg = "Email này chưa được đăng ký!";
            return REDIRECT_PREFIX + PASSWORD_RESET_VIEW;
        } else {
            khachHangService.resetPassword(email);
            mMsg = "Mật khẩu mới đã được gửi về địa chỉ email " + email + " thành công!";
            mIsByPass = true;
            return REDIRECT_PREFIX + LOGIN_VIEW;
        }
    }

    // Check direct
    private boolean isDirect() {
        // check bypass
        if (mIsByPass) {
            return true;
        } else {
            return authenticationUtil.getAccount() == null;
        }
    }
}
