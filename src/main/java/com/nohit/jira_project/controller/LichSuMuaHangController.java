package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(HISTORY_VIEW)
public class LichSuMuaHangController {
    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    // Load history
    @GetMapping("")
    public ModelAndView history() {
        var khachHang = authenticationUtil.getAccount();
        // check current account still valid
        if (khachHang == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(HISTORY_TEMP);
            var idKhachHang = khachHang.getId();
            mav.addObject("cart", gioHangService.getGioHang(idKhachHang));
            mav.addObject("login", khachHang != null);
            mav.addObject("client", khachHang);
            return mav;
        }
    }
}
