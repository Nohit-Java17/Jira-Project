package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;
import static java.lang.Math.*;

@Controller
@RequestMapping(DETAIL_VIEW)
public class ChiTietSanPhamController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private NhanXetService nhanXetService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;

    // Fields
    private String mMsg;
    private boolean mIsMsgShow;

    @GetMapping("")
    public String detail() {
        return REDIRECT_PREFIX + PRODUCT_VIEW;
    }

    // Load detail
    @GetMapping(FIND_VIEW)
    public ModelAndView detailFind(int id) {
        var mav = new ModelAndView(DETAIL_TEMP);
        var khachHang = authenticationUtil.getAccount();
        var sanPham = sanPhamService.getSanPham(id);
        mav.addObject(TITLE_PARAM, CHI_TIET);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject(LOGIN_PARAM, khachHang != null);
        mav.addObject(PRODUCT_PARAM, sanPham);
        mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 4));
        mav.addObject(LIMIT_PARAM, sanPham.getTonKho());
        mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
        return mav;
    }

    // Load search
    @GetMapping(SEARCH_VIEW)
    public ModelAndView detailSearch(String name) {
        var khachHang = authenticationUtil.getAccount();
        var sanPham = sanPhamService.getSanPham(name);
        var mav = sanPham == null ? new ModelAndView(BLANK_TEMP) : new ModelAndView(DETAIL_TEMP);
        mav.addObject(TITLE_PARAM, CHI_TIET);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject(LOGIN_PARAM, khachHang != null);
        mav.addObject(PRODUCT_PARAM, sanPham);
        mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 4));
        mav.addObject(LIMIT_PARAM, sanPham.getTonKho());
        return mav;
    }

    // Rate product
    @PostMapping(RATE_VIEW)
    public String detailRate(NhanXet nhanXet) {
        // check current account still valid
        if (authenticationUtil.getAccount() == null) {
            return REDIRECT_PREFIX + LOGIN_VIEW;
        } else {
            var sanPham = nhanXet.getSanPham();
            var dsNhanXet = sanPham.getDsNhanXet();
            var dsNhanXetSize = dsNhanXet.size();
            var danhGia = 0;
            // get all danh_gia of product
            for (var i = 0; i < dsNhanXetSize; i++) {
                danhGia += dsNhanXet.get(i).getDanhGia();
            }
            sanPham.setDanhGia(round((danhGia + nhanXet.getDanhGia()) / (dsNhanXetSize + 1)));
            sanPham = sanPhamService.saveSanPham(sanPham);
            nhanXet = nhanXetService.saveNhanXet(nhanXet);
            mIsMsgShow = true;
            mMsg = "Nhận xét sản phẩm thành công!";
            return REDIRECT_PREFIX + DETAIL_VIEW + FIND_VIEW + "?id=" + sanPham.getId();
        }
    }
}
