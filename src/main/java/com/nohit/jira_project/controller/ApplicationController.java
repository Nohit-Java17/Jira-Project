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
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping("")
public class ApplicationController {
    @Autowired
    KhachHangService khachHangService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    StringUtil stringUtil;

    // Fields
    private String mMsg;
    private boolean mIsMsgShow;

    // Load register
    @GetMapping(REGISTER_VIEW)
    public ModelAndView register() {
        // check current account still valid
        if (authenticationUtil.getAccount() == null) {
            var mav = new ModelAndView(REGISTER_TEMP);
            showMessageBox(mav);
            return mav;
        } else {
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        }
    }

    // Register
    @PostMapping(REGISTER_VIEW)
    public String register(KhachHang khachHang) {
        var trueEmail = stringUtil.removeSpCharsBeginAndEnd(khachHang.getEmail()).toLowerCase();
        mIsMsgShow = true;
        // check email is already exist
        if (khachHangService.getKhachHang(trueEmail) != null) {
            mMsg = "Email này đã được đăng ký!";
            return REDIRECT_PREFIX + REGISTER_VIEW;
        } else {
            khachHang.setEmail(trueEmail);
            khachHang.setIdTinhThanh(DEFAULT_PROVINCE);
            khachHang.setVaiTro(DEFAULT_ROLE);
            khachHangService.saveKhachHang(khachHang);
            mMsg = "Tài khoản đã được đăng ký thành công!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        }
    }

    // Load login
    @GetMapping(LOGIN_VIEW)
    public ModelAndView login(boolean error) {
        // check current account still valid
        if (authenticationUtil.getAccount() == null) {
            var mav = new ModelAndView(LOGIN_TEMP);
            // login failed
            if (error) {
                mIsMsgShow = true;
                mMsg = "Tài khoản đăng nhập chưa đúng!";
            }
            showMessageBox(mav);
            return mav;
        } else {
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        }
    }

    // Load password-reset
    @GetMapping(PASSWORD_RESET_VIEW)
    public ModelAndView resetPassword() {
        var mav = new ModelAndView(PASSWORD_RESET_TEMP);
        showMessageBox(mav);
        return mav;
    }

    // quên mật khẩu
    @PostMapping(PASSWORD_RESET_VIEW)
    public String resetPassword(String email) throws UnsupportedEncodingException, MessagingException {
        var trueEmail = stringUtil.removeSpCharsBeginAndEnd(email).toLowerCase();
        mIsMsgShow = true;
        // check email is already exist
        if (khachHangService.getKhachHang(trueEmail) == null) {
            mMsg = "Email này chưa được đăng ký. Vui lòng thử lại!";
            return REDIRECT_PREFIX + PASSWORD_RESET_VIEW;
        } else {
            khachHangService.resetPassword(email);
            mMsg = "Mật khẩu mới đã gửi về email. Vui lòng kiểm tra lại!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        }
    }

    // Show message
    private void showMessageBox(ModelAndView mav) {
        // check flag
        if (mIsMsgShow) {
            mav.addObject(FLAG_MSG_PARAM, true);
            mav.addObject(MSG_PARAM, mMsg);
            mIsMsgShow = false;
        }
    }
}
