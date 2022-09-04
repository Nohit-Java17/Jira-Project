package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(INDEX_VIEW)
public class IndexController {
    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private SanPhamService productService;

    @Autowired
    private GioHangService gioHangService;

    // Fields
    private KhachHang mCurrentAccount;
    private boolean mIsByPass;

    // Load dashboard
    @GetMapping("")
    public ModelAndView index() {
        var mav = new ModelAndView(INDEX_TEMP);
        GioHang gioHang;
        // check current account still valid
        if (!isValidAccount()) {
            gioHang = new GioHang();
        } else {
            var id = mCurrentAccount.getId();
            gioHang = gioHangService.getGioHang(id);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(id);
                gioHangService.saveGioHang(gioHang);
            }
        }
        mav.addObject("khachHang", mCurrentAccount);
        mav.addObject("gioHang", gioHang);
        mav.addObject("login", mCurrentAccount != null);
        mav.addObject("newProducts", productService.getDsSanPhamNewestOrder().subList(0, 6));
        mav.addObject("some_topsaleProducts", productService.getDsSanPhamTopSale().subList(0, 3));
        mav.addObject("some_products", productService.getDsSanPhamDescendingPriceOrder().subList(0, 3));
        mav.addObject("some_newProducts", productService.getDsSanPhamNewestOrder().subList(0, 3));
        mIsByPass = false;
        return mav;
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
}
