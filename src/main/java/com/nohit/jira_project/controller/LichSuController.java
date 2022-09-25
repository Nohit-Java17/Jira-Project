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
@RequestMapping(HISTORY_VIEW)
public class LichSuController {
    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;

    // Load history
    @GetMapping("")
    public ModelAndView history() {
        var client = authenticationUtil.getAccount();
        // check current account still valid
        if (client == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(HISTORY_TEMP);
            mav.addObject(TITLE_PARAM, LICH_SU);
            mav.addObject(CART_PARAM, client.getGioHang());
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(ORDERS_PARAM, client.getDsDonHang());
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }
}
