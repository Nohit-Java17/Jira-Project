package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(ORDER_VIEW)
public class DonHangController {
    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private DonHangService donHangService;

    @GetMapping("")
    public String order() {
        return REDIRECT_PREFIX + HISTORY_VIEW;
    }

    // Load order
    @GetMapping(FIND_VIEW)
    public ModelAndView orderFind(int id) {
        var khachHang = authenticationUtil.getAccount();
        // check current account still valid
        if (khachHang == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(ORDER_TEMP);
            mav.addObject(TITLE_PARAM, DON_HANG);
            mav.addObject(CART_PARAM, gioHangService.getGioHang(khachHang.getId()));
            mav.addObject(LOGIN_PARAM, khachHang != null);
            mav.addObject(ORDER_PARAM, donHangService.getDonHang(id));
            return mav;
        }
    }
}
