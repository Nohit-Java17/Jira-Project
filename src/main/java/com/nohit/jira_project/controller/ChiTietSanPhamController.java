package com.nohit.jira_project.controller;

import java.util.*;

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
    private KhachHang mCurrentAccount;
    private String mMsg;
    private boolean mIsByPass;
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
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
        mav.addObject("product", sanPhamService.getSanPham(id));
        mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject("topSaleProducts", sanPhamService.getDsSanPhamTopSale().subList(0, 4));
        showMessageBox(mav);
        mIsByPass = false;
        return mav;
    }

    // Load detail
    @GetMapping(SEARCH_VIEW)
    public ModelAndView detailFindByName(String nameProduct) {
        var mav = new ModelAndView(DETAIL_TEMP);
        // check current account still valid
        if (!isValidAccount()) {
            mCurrentCart = new GioHang();
        } else {
            var idKhacHang = mCurrentAccount.getId();
            mCurrentCart = gioHangService.getGioHang(idKhacHang);
            // check gio_hang exist
            if (mCurrentCart == null) {
                mCurrentCart = new GioHang();
                mCurrentCart.setId(idKhacHang);
                gioHangService.saveGioHang(mCurrentCart);
            }
        }

        // Get and check null result
        SanPham sanPhamFindByName = sanPhamService.getSanPhamByName(nameProduct);
        if (sanPhamFindByName == null) {
            mav = new ModelAndView(INDEX_TEMP);
        }
        mav.addObject("client", mCurrentAccount);
        mav.addObject("cart", mCurrentCart);
        mav.addObject("login", mCurrentAccount != null);
        mav.addObject("product", sanPhamFindByName);
        mav.addObject("listAllProducts", sanPhamService.getDsSanPham());
        mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject("topSaleProducts", sanPhamService.getDsSanPhamTopSale().subList(0, 4));
        showMessageBox(mav);
        mIsByPass = false;
        return mav;
    }

    // Rate product
    @PostMapping(RATE_VIEW)
    public String detailRate(NhanXet nhanXet) {
        // check current account still valid
        if (!isValidAccount()) {
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
            mIsByPass = true;
            return REDIRECT_PREFIX + DETAIL_VIEW + VIEW_VIEW + "?id=" + nhanXet.getIdSanPham();
        }
    }

    // Check valid account
    private boolean isValidAccount() {
        // check bypass
        if (mIsByPass) {
            return true;
        } else {
            mCurrentAccount = authenticationUtil.getAccount();
            return mCurrentAccount != null;
        }
    }

    // Show message
    private void showMessageBox(ModelAndView mav) {
        // check flag
        if (mIsMsgShow) {
            mav.addObject(FLAG_MSG_PARAM, true);
            mav.addObject(MSG_PARAM, mMsg);
            mIsMsgShow = false;
        }
    }
}
