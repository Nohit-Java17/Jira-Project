package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.ApplicationConstant.*;
import static com.nohit.jira_project.constant.ApplicationConstant.ChoosenOne.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(CATEGORY_VIEW)
public class PhanLoaiController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;

    // Load category
    @GetMapping("")
    public ModelAndView category() {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var khachHang = authenticationUtil.getAccount();
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("choosenOne", PHAN_LOAI);
        mav.addObject("login", khachHang != null);
        mav.addObject("products", sanPhamService.getDsSanPhamTonKho());
        mav.addObject("radioCheck", DEFAULT_CATEGORY);
        return mav;
    }

    // Load filter products
    @GetMapping(FILTER_VIEW)
    public ModelAndView categoryFilter(String filter) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var khachHang = authenticationUtil.getAccount();
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("choosenOne", PHAN_LOAI);
        mav.addObject("login", khachHang != null);
        mav.addObject("products", sanPhamService.getDsSanPham(filter));
        mav.addObject("radioCheck", CATEGORIES_MAP.get(filter));
        return mav;
    }
}
