package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.common.Bean.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(ABOUT_VIEW)
public class GioiThieuController {
    @Autowired
    private ApplicationUtil applicationUtil;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    // Load about
    @GetMapping("")
    public ModelAndView about() {
        var mav = new ModelAndView(ABOUT_TEMP);
        var client = authenticationUtil.getAccount();
        mav.addObject(TITLE_PARAM, GIOI_THIEU);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }
}
