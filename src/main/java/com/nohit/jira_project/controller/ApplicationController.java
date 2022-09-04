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
    private AuthenticationUtil authenticationUtil;

    @Autowired
    StringUtil stringUtil;

    @Autowired
    KhachHangService khachHangService;

    // Fields
    private KhachHang mCurrentAccount;
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;

    // Check login
    @GetMapping(LOGIN_VIEW)
    public ModelAndView login(boolean error) {
        // check current account still valid
        if (!isValidAccount()) {
            var mav = new ModelAndView(LOGIN_TEMP);
            showMessageBox(mav);
            if (error) {
                mIsMsgShow = true;
                mMsg = "Tài khoản đăng nhập chưa đúng!";
            }
            return mav;
        } else {
            mIsByPass = true;
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        }
    }

    // Load register
    @GetMapping(REGISTER_VIEW)
    public ModelAndView register() {
        var mav = new ModelAndView(REGISTER_TEMP);
        showMessageBox(mav);
        return mav;
    }

    @GetMapping(value = { CART_VIEW })
    public ModelAndView cart() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout

        // Check current account still valid
        if (!isValidAccount()) {
            var mav = new ModelAndView(LOGIN_TEMP);
            return mav;
        } else {
            mIsByPass = true;
            return new ModelAndView(REDIRECT_PREFIX + CART_VIEW);
        }
    }

    // Load checkout
    @GetMapping(value = { CHECKOUT_VIEW })
    public ModelAndView checkout() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout

        // Check current account still valid
        if (!isValidAccount()) {
            var mav = new ModelAndView(LOGIN_TEMP);
            return mav;
        } else {
            mIsByPass = true;
            return new ModelAndView(REDIRECT_PREFIX + CHECKOUT_VIEW);
        }
    }

    // Load password-reset
    @GetMapping(value = { PASSWORD_RESET_VIEW })
    public ModelAndView passwordReset() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout

        // Check current account still valid

        var mav = new ModelAndView(PASSWORD_RESET_TEMP);
        mIsByPass = false;
        return mav;

    }

    // Register
    @PostMapping(REGISTER_VIEW)
    public String register(KhachHang khachHang) {
        mIsByPass = false;
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

    // quên mật khẩu
    @PostMapping(PASSWORD_RESET_VIEW)
    public String resetPassword(@RequestParam("email") String email)
            throws UnsupportedEncodingException, MessagingException {
        mIsMsgShow = true;
        mIsByPass = true;
        var trueEmail = stringUtil.removeSpCharsBeginAndEnd(email).toLowerCase();
        // check email is already exist
        if (khachHangService.getKhachHang(trueEmail) == null) {
            mMsg = "Email này chưa được đăng ký. Vui lòng thử lại!";
            return null;
        } else {
            khachHangService.resetPassword(email);
            mMsg = "Mật khẩu mới đã gửi về email. Vui lòng kiểm tra lại!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        }
    }

    // Check valid account
    private boolean isValidAccount() {
        // check bypass
        if (mIsByPass) {
            return true;
        } else {
            mCurrentAccount = authenticationUtil.getAccount();
            // System.out.print(mCurrentAccount.getHoTen());
            return mCurrentAccount != null;
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
