package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.common.Bean.*;
import static com.nohit.jira_project.constant.ApplicationConstant.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
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
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPhamTonKho();
        var maxProducts = products.size();
        var maxSize = (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1;
        mav.addObject(TITLE_PARAM, PHAN_LOAI);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM,
                products.subList(0, maxProducts < DEFAULT_SIZE_PAGE ? maxProducts : DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, DEFAULT_CATEGORY);
        mav.addObject(MAX_SIZE_PARAM, (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1);
        mav.addObject(VIEW_PARAM, PAGE_VIEW + "?page=");
        mav.addObject(PREVIOUS_PARAM, PAGE_VIEW + "?page=" + 1);
        mav.addObject(NEXT_PARAM, PAGE_VIEW + "?page=" + (2 > maxSize ? maxSize : 2));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Load category from page
    @GetMapping(PAGE_VIEW)
    public ModelAndView category(int page) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPhamTonKho();
        var maxProducts = products.size();
        var maxPage = page * DEFAULT_SIZE_PAGE;
        var maxSize = (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1;
        var previous = page - 1;
        var next = page + 1;
        mav.addObject(TITLE_PARAM, PHAN_LOAI);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM,
                products.subList((page - 1) * DEFAULT_SIZE_PAGE, maxPage > maxProducts ? maxProducts : maxPage));
        mav.addObject(RADIO_CHECK_PARAM, DEFAULT_CATEGORY);
        mav.addObject(MAX_SIZE_PARAM, maxSize);
        mav.addObject(VIEW_PARAM, PAGE_VIEW + "?page=");
        mav.addObject(PREVIOUS_PARAM, PAGE_VIEW + "?page=" + (previous < 1 ? 1 : previous));
        mav.addObject(NEXT_PARAM, PAGE_VIEW + "?page=" + (next > maxSize ? maxSize : next));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Load filter products
    @GetMapping(FILTER_VIEW)
    public ModelAndView categoryFilter(String filter) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPham(filter);
        var maxProducts = products.size();
        var maxSize = (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1;
        mav.addObject(TITLE_PARAM, PHAN_LOAI);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM,
                products.subList(0, maxProducts < DEFAULT_SIZE_PAGE ? maxProducts : DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, CATEGORIES_MAP.get(filter));
        mav.addObject(MAX_SIZE_PARAM, (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1);
        mav.addObject(VIEW_PARAM, FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=");
        mav.addObject(PREVIOUS_PARAM, FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + 1);
        mav.addObject(NEXT_PARAM,
                FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + (2 > maxSize ? maxSize : 2));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Load filter products from page
    @GetMapping(FILTER_VIEW + PAGE_VIEW)
    public ModelAndView categoryFilter(String filter, int page) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPham(filter);
        var maxProducts = products.size();
        var maxPage = page * DEFAULT_SIZE_PAGE;
        var maxSize = (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1;
        var previous = page - 1;
        var next = page + 1;
        mav.addObject(TITLE_PARAM, PHAN_LOAI);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM,
                products.subList((page - 1) * DEFAULT_SIZE_PAGE, maxPage > maxProducts ? maxProducts : maxPage));
        mav.addObject(RADIO_CHECK_PARAM, CATEGORIES_MAP.get(filter));
        mav.addObject(MAX_SIZE_PARAM, maxSize);
        mav.addObject(VIEW_PARAM, FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=");
        mav.addObject(PREVIOUS_PARAM,
                FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + (previous < 1 ? 1 : previous));
        mav.addObject(NEXT_PARAM,
                FILTER_VIEW + PAGE_VIEW + "?filter=" + filter + "&page=" + (next > maxSize ? maxSize : next));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }
}
