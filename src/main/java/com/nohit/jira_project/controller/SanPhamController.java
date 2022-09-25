package com.nohit.jira_project.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.common.Bean.*;
import static com.nohit.jira_project.constant.ApplicationConstant.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(PRODUCT_VIEW)
public class SanPhamController {
    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private ApplicationUtil applicationUtil;

    // Load product
    @GetMapping("")
    public ModelAndView product() {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPhamTonKho();
        var maxProducts = products.size();
        var maxSize = (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1;
        mav.addObject(TITLE_PARAM, SAN_PHAM);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM,
                products.subList(0, maxProducts < DEFAULT_SIZE_PAGE ? maxProducts : DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, DEFAULT_PRODUCT);
        mav.addObject(MAX_SIZE_PARAM, (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1);
        mav.addObject(VIEW_PARAM, PAGE_VIEW + "?page=");
        mav.addObject(PREVIOUS_PARAM, PAGE_VIEW + "?page=" + 1);
        mav.addObject(NEXT_PARAM, PAGE_VIEW + "?page=" + (2 > maxSize ? maxSize : 2));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Load product from page
    @GetMapping(PAGE_VIEW)
    public ModelAndView product(int page) {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sanPhamService.getDsSanPhamTonKho();
        var maxProducts = products.size();
        var maxPage = page * DEFAULT_SIZE_PAGE;
        var maxSize = (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1;
        var previous = page - 1;
        var next = page + 1;
        mav.addObject(TITLE_PARAM, SAN_PHAM);
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

    // Load sort products
    @GetMapping(SORT_VIEW)
    public ModelAndView productSort(String sort) {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sortProducts(sort);
        var maxProducts = products.size();
        var maxSize = (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1;
        mav.addObject(TITLE_PARAM, SAN_PHAM);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM,
                products.subList(0, maxProducts < DEFAULT_SIZE_PAGE ? maxProducts : DEFAULT_SIZE_PAGE));
        mav.addObject(RADIO_CHECK_PARAM, PRODUCTS_MAP.get(sort));
        mav.addObject(MAX_SIZE_PARAM, (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1);
        mav.addObject(VIEW_PARAM, SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=");
        mav.addObject(PREVIOUS_PARAM, SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + 1);
        mav.addObject(NEXT_PARAM, SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + (2 > maxSize ? maxSize : 2));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Load sort products
    @GetMapping(SORT_VIEW + PAGE_VIEW)
    public ModelAndView productSort(String sort, int page) {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var client = authenticationUtil.getAccount();
        var products = sortProducts(sort);
        var maxProducts = products.size();
        var maxPage = page * DEFAULT_SIZE_PAGE;
        var maxSize = (maxProducts - 1) / DEFAULT_SIZE_PAGE + 1;
        var previous = page - 1;
        var next = page + 1;
        mav.addObject(TITLE_PARAM, SAN_PHAM);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCTS_PARAM,
                products.subList((page - 1) * DEFAULT_SIZE_PAGE, maxPage > maxProducts ? maxProducts : maxPage));
        mav.addObject(RADIO_CHECK_PARAM, PRODUCTS_MAP.get(sort));
        mav.addObject(MAX_SIZE_PARAM, maxSize);
        mav.addObject(VIEW_PARAM, SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=");
        mav.addObject(PREVIOUS_PARAM,
                SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + (previous < 1 ? 1 : previous));
        mav.addObject(NEXT_PARAM,
                SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + (next > maxSize ? maxSize : next));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Get list products by sort
    private List<SanPham> sortProducts(String sort) {
        switch (sort) {
            case "topSale": {
                return sanPhamService.getDsSanPhamTopSale();
            }
            case "newest": {
                return sanPhamService.getDsSanPhamNewest();
            }
            case "discount": {
                return sanPhamService.getDsSanPhamDescendingDiscount();
            }
            case "ascendingPrice": {
                return sanPhamService.getDsSanPhamAscendingPrice();
            }
            case "descendingPrice": {
                return sanPhamService.getDsSanPhamDescendingPrice();
            }
            default: {
                return sanPhamService.getDsSanPham();
            }
        }
    }
}
