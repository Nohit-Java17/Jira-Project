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
    private SanPham mChoosenOne;
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;

    // Load dashboard
    @GetMapping(value = {""})
    public ModelAndView index() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout
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
        showMessageBox(mav);
        
        mav.addObject("user", mCurrentAccount);
        mav.addObject("products", productService.getDsSanPham());
        mav.addObject("newProducts", productService.getDsSanPhamNewestOrder());
        mav.addObject("some_products", productService.getDsSanPhamAscendingPriceOrder().subList(0, 3));
        mav.addObject("some_newProducts", productService.getDsSanPhamNewestOrder().subList(0, 3));
        mav.addObject("some_topsaleProducts", productService.getDsSanPhamTopSale().subList(0, 3));
        mIsByPass = false;
        return mav;
    }

    // Re-check choosen one
    private boolean isAliveChoosenOne() {
        // check the project has been declared
        if (mChoosenOne == null) {
            return false;
        } else {
            mChoosenOne = productService.getSanPham(mChoosenOne.getId());
            return mChoosenOne != null;
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
