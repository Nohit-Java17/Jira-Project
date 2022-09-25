package com.nohit.jira_project.controller;

import java.io.*;

import javax.mail.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.common.Bean.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(PASSWORD_RESET_VIEW)
public class MatKhauController {
    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private ApplicationUtil applicationUtil;

    // Load password-reset
    @GetMapping("")
    public ModelAndView resetPassword() {
        var mav = new ModelAndView(PASSWORD_RESET_TEMP);
        mav.addObject(TITLE_PARAM, MAT_KHAU);
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Reset password
    @PostMapping("")
    public String resetPassword(String email) throws UnsupportedEncodingException, MessagingException {
        _isMsgShow = true;
        // check email is already exist
        if (khachHangService.getKhachHang(email) == null) {
            _msg = "Email này chưa được đăng ký!";
            return REDIRECT_PREFIX + PASSWORD_RESET_VIEW;
        } else {
            khachHangService.resetPassword(email);
            _msg = "Mật khẩu mới đã được gửi về địa chỉ email " + email + " thành công!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        }
    }
}
