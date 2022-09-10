package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

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
        var khachHang = authenticationUtil.getAccount();
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
        mav.addObject("newProducts", sanPhamService.getDsSanPhamNewest().subList(0, 6));
        mav.addObject("topSaleProducts", sanPhamService.getDsSanPhamTopSale().subList(0, 3));
        mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
        return mav;
    }
}
