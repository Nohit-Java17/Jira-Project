package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.common.Bean.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping("")
public class ApplicationController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private TheoDoiService theoDoiService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;

    // Load login
    @GetMapping(LOGIN_VIEW)
    public ModelAndView login(boolean error) {
        // check login
        if (authenticationUtil.getAccount() != null) {
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        } else {
            var mav = new ModelAndView(LOGIN_TEMP);
            // login failed
            if (error) {
                _isMsgShow = true;
                _msg = "Tài khoản đăng nhập chưa đúng!";
            }
            mav.addObject(TITLE_PARAM, DANG_NHAP);
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Load dashboard
    @GetMapping(value = { INDEX_VIEW, "/", "" })
    public ModelAndView index() {
        var mav = new ModelAndView(INDEX_TEMP);
        var client = authenticationUtil.getAccount();
        var newestProducts = sanPhamService.getDsSanPhamNewest();
        mav.addObject(TITLE_PARAM, TRANG_CHU);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(NEW_PRODUCTS_PARAM, newestProducts.subList(0, 6));
        mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 3));
        mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject(TOP_NEWS_PARAM, newestProducts.subList(0, 3));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Add thu_phan_hoi
    @PostMapping(SUBCRIBE_VIEW)
    public String subcribe(TheoDoi theoDoi) {
        _isMsgShow = true;
        // check email is already exist
        if (theoDoiService.getTheoDoi(theoDoi.getEmail()) != null) {
            _msg = "Email này đã được đăng ký!";
        } else {
            theoDoiService.saveTheoDoi(theoDoi);
            _msg = "Đăng ký nhận thông báo thành công!";
        }
        return REDIRECT_PREFIX + INDEX_VIEW;
    }

    // Load search
    @GetMapping(SEARCH_VIEW)
    public String search(String ten) {
        var product = sanPhamService.getSanPham(ten);
        // check if product is exist
        if (product == null || product.getTonKho() < 1) {
            return REDIRECT_PREFIX + BLANK_VIEW;
        } else {
            return REDIRECT_PREFIX + DETAIL_VIEW + FIND_VIEW + "?id=" + product.getId();
        }
    }

    // Load blank
    @GetMapping(BLANK_VIEW)
    public ModelAndView blank() {
        var mav = new ModelAndView(BLANK_TEMP);
        var client = authenticationUtil.getAccount();
        mav.addObject(TITLE_PARAM, CHI_TIET);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 4));
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }
}
