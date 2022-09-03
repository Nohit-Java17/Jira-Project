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
@RequestMapping(CATEGORY_VIEW)
public class PhanLoaiController {
    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private SanPhamService productService;

    // Fields
    private KhachHang mCurrentAccount;
    private SanPham mChoosenOne;
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;

    // Load category
    @GetMapping(value = { "" })
    public ModelAndView category() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout
        var mav = new ModelAndView(CATEGORY_TEMP);
        mav.addObject("user", mCurrentAccount);
        mav.addObject("products", productService.getDsSanPham());
        mIsByPass = false;
        return mav;
    }

    // Load top sale products
    @GetMapping(value = { "/sort" })
    public ModelAndView topsaleProduct(@RequestParam("sort") String criteria) {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout
        var mav = new ModelAndView(CATEGORY_TEMP);
        mav.addObject("user", mCurrentAccount);
        if (criteria.equals("pc")) {
            mav.addObject("products", productService.getDsSanPhamComputer());
        } else if (criteria.equals("devices")) {
            mav.addObject("products", productService.getDsSanPhamDevices());
        } else if (criteria.equals("laptop")) {
            mav.addObject("products", productService.getDsSanPhamLaptop());
        } else if (criteria.equals("smartphone")) {
            mav.addObject("products", productService.getDsSanPhamSmartPhone());
        } else if (criteria.equals("tablet")) {
            mav.addObject("products", productService.getDsSanPhamTablet());
        } else {
            mav.addObject("products", productService.getDsSanPham());
        }
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
