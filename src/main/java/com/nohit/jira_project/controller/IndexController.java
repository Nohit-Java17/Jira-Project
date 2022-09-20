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
@RequestMapping(value = { INDEX_VIEW, "/", "" })
public class IndexController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private ApplicationUtil applicationUtil;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    // Load dashboard
    @GetMapping("")
    public ModelAndView index() {
        var mav = new ModelAndView(INDEX_TEMP);
        var client = authenticationUtil.getAccount();
        mav.addObject(TITLE_PARAM, TRANG_CHU);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(NEW_PRODUCTS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 6));
        mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 3));
        mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
        return mav;
    }
}
