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
        var dsTonkho = sanPhamService.getDsSanPhamTonKho();
        var maxDsTonkho = dsTonkho.size();
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("choosenOne", PHAN_LOAI);
        mav.addObject("login", khachHang != null);
        mav.addObject("products",
                dsTonkho.subList(0, maxDsTonkho < DEFAULT_SIZE_PAGE ? maxDsTonkho : DEFAULT_SIZE_PAGE));
        mav.addObject("radioCheck", DEFAULT_CATEGORY);
        mav.addObject("maxSize", (dsTonkho.size() - 1) / DEFAULT_SIZE_PAGE + 1);
        mav.addObject("view", PAGE_VIEW + "?page=");
        mav.addObject("previous", PAGE_VIEW + "?page=" + 1);
        mav.addObject("next", PAGE_VIEW + "?page=" + 2);
        return mav;
    }

    // Load category from page
    @GetMapping(PAGE_VIEW)
    public ModelAndView category(int page) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var khachHang = authenticationUtil.getAccount();
        var dsTonkho = sanPhamService.getDsSanPhamTonKho();
        var maxDsTonkho = dsTonkho.size();
        var maxPage = page * DEFAULT_SIZE_PAGE;
        var maxSize = (maxDsTonkho - 1) / DEFAULT_SIZE_PAGE + 1;
        var previous = page - 1;
        var next = page + 1;
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("choosenOne", PHAN_LOAI);
        mav.addObject("login", khachHang != null);
        mav.addObject("products",
                dsTonkho.subList((page - 1) * DEFAULT_SIZE_PAGE, maxPage > maxDsTonkho ? maxDsTonkho : maxPage));
        mav.addObject("radioCheck", DEFAULT_CATEGORY);
        mav.addObject("maxSize", maxSize);
        mav.addObject("view", PAGE_VIEW + "?page=");
        mav.addObject("previous", PAGE_VIEW + "?page=" + (previous < 1 ? 1 : previous));
        mav.addObject("next", PAGE_VIEW + "?page=" + (next > maxSize ? maxSize : next));
        return mav;
    }

    // Load filter products
    @GetMapping(FILTER_VIEW)
    public ModelAndView categoryFilter(String filter) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var khachHang = authenticationUtil.getAccount();
        var dsTonkho = sanPhamService.getDsSanPham(filter);
        var maxDsTonkho = dsTonkho.size();
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("choosenOne", PHAN_LOAI);
        mav.addObject("login", khachHang != null);
        mav.addObject("products",
                dsTonkho.subList(0, maxDsTonkho < DEFAULT_SIZE_PAGE ? maxDsTonkho : DEFAULT_SIZE_PAGE));
        mav.addObject("radioCheck", CATEGORIES_MAP.get(filter));
        mav.addObject("maxSize", (dsTonkho.size() - 1) / DEFAULT_SIZE_PAGE + 1);
        mav.addObject("view", FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=");
        mav.addObject("previous", FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + 1);
        mav.addObject("next", FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + 2);
        return mav;
    }

    // Load filter products from page
    @GetMapping(FILTER_VIEW + PAGE_VIEW)
    public ModelAndView categoryFilter(String filter, int page) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var khachHang = authenticationUtil.getAccount();
        var dsTonkho = sanPhamService.getDsSanPham(filter);
        var maxDsTonkho = dsTonkho.size();
        var maxPage = page * DEFAULT_SIZE_PAGE;
        var maxSize = (maxDsTonkho - 1) / DEFAULT_SIZE_PAGE + 1;
        var previous = page - 1;
        var next = page + 1;
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("choosenOne", PHAN_LOAI);
        mav.addObject("login", khachHang != null);
        mav.addObject("products",
                dsTonkho.subList((page - 1) * DEFAULT_SIZE_PAGE, maxPage > maxDsTonkho ? maxDsTonkho : maxPage));
        mav.addObject("radioCheck", CATEGORIES_MAP.get(filter));
        mav.addObject("maxSize", maxSize);
        mav.addObject("view", FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=");
        mav.addObject("previous",
                FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + (previous < 1 ? 1 : previous));
        mav.addObject("next",
                FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + (next > maxSize ? maxSize : next));
        return mav;
    }
}
