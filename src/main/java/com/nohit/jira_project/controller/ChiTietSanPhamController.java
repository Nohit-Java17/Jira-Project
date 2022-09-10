package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

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
    @GetMapping(VIEW_VIEW)
    public ModelAndView detailFind(int id) {
        var mav = new ModelAndView(DETAIL_TEMP);
        var khachHang = authenticationUtil.getAccount();
        var sanPham = sanPhamService.getSanPham(id);
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
        mav.addObject("product", sanPham);
        mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject("topSaleProducts", sanPhamService.getDsSanPhamTopSale().subList(0, 4));
        mav.addObject("limit", sanPham.getTonKho());
        mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
        return mav;
    }

    // Load detail
    @GetMapping(SEARCH_VIEW)
    public ModelAndView detailSearch(String name) {
        var khachHang = authenticationUtil.getAccount();
        var sanPham = sanPhamService.getSanPham(name);
        var mav = sanPham == null ? new ModelAndView(BLANK_TEMP) : new ModelAndView(DETAIL_TEMP);
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
        mav.addObject("product", sanPham);
        mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject("topSaleProducts", sanPhamService.getDsSanPhamTopSale().subList(0, 4));
        mav.addObject("limit", sanPham.getTonKho());
        return mav;
    }

    // Rate product
    @PostMapping(RATE_VIEW)
    public String detailRate(NhanXet nhanXet) {
        // check current account still valid
        if (authenticationUtil.getAccount() == null) {
            return REDIRECT_PREFIX + LOGIN_VIEW;
        } else {
            var sanPham = sanPhamService.getSanPham(nhanXet.getIdSanPham());
            var dsNhanXet = sanPham.getDsNhanXet();
            var dsNhanXetSize = dsNhanXet.size();
            var danhGia = 0;
            for (var i = 0; i < dsNhanXetSize; i++) {
                danhGia += dsNhanXet.get(i).getDanhGia();
            }
            sanPham.setDanhGia(Math.round((danhGia + nhanXet.getDanhGia()) / (dsNhanXetSize + 1)));
            sanPhamService.saveSanPham(sanPham);
            nhanXetService.saveNhanXet(nhanXet);
            mIsMsgShow = true;
            mMsg = "Nhận xét sản phẩm thành công!";
            return REDIRECT_PREFIX + DETAIL_VIEW + VIEW_VIEW + "?id=" + nhanXet.getIdSanPham();
        }
    }
}
