package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping("")
public class ApplicationController {

    @Autowired
    private AuthenticationUtil authenticationUtil;

    // Fields
    private KhachHang mCurrentAccount;
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;

    // Check login
    @GetMapping(LOGIN_VIEW)
    public ModelAndView login(boolean error) {
        // check current account still valid
        if (!isValidAccount()) {
            var mav = new ModelAndView(LOGIN_TEMP);
            if (error) {
                mIsMsgShow = true;
                mMsg = "Tài khoản đăng nhập chưa đúng!";
                showMessageBox(mav);
            }
            return mav;
        } else {
            mIsByPass = true;
            return new ModelAndView(REDIRECT_PREFIX + INDEX_VIEW);
        }
    }

    // Load dashboard
    @GetMapping(value = { INDEX_VIEW, "/", "" })
    public ModelAndView index() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout
        var mav = new ModelAndView(INDEX_TEMP);
        mIsByPass = false;
        return mav;

    }

    // Load detail
    @GetMapping(value = { DETAIL_VIEW })
    public ModelAndView detail() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout
        var mav = new ModelAndView(DETAIL_TEMP);
        mIsByPass = false;
        return mav;

    }

    // Load about
    @GetMapping(value = { ABOUT_VIEW})
    public ModelAndView about() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout
        var mav = new ModelAndView(ABOUT_TEMP);
        mIsByPass = false;
        return mav;

    }

    // Load about
    @GetMapping(value = { REGISTER_VIEW})
    public ModelAndView register() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout
        var mav = new ModelAndView(REGISTER_TEMP);
        mIsByPass = false;
        return mav;

    }

    // Load product
    @GetMapping(value = { CONTACT_VIEW })
    public ModelAndView contact() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout
        var mav = new ModelAndView(CONTACT_TEMP);
        mIsByPass = false;
        return mav;
    }

    // Load product
    @GetMapping(value = { CART_VIEW })
    public ModelAndView cart() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout

        // Check current account still valid
        if (!isValidAccount()) {
            var mav = new ModelAndView(LOGIN_TEMP);
            return mav;
        } else {
            mIsByPass = true;
            return new ModelAndView(REDIRECT_PREFIX + CART_VIEW);
        }
    }

    // Load product
    @GetMapping(value = { CHECKOUT_VIEW })
    public ModelAndView checkout() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout

        // Check current account still valid
        if (!isValidAccount()) {
            var mav = new ModelAndView(LOGIN_TEMP);
            return mav;
        } else {
            mIsByPass = true;
            return new ModelAndView(REDIRECT_PREFIX + CHECKOUT_VIEW);
        }
    }

    // @GetMapping("/home")
    // public String index() {
    // return "index";
    // }

    // @GetMapping("/shop")
    // public String shop() {
    //     return "shop";
    // }

    // @GetMapping("/category")
    // public String category() {
    //     return "category";
    // }

    // @GetMapping("/single-product")
    // public String singleProduct() {
    //     return "single-product";
    // }

    // @GetMapping("/cart")
    // public String cart() {
    //     return "cart";
    // }

    // @GetMapping("/checkout")
    // public String checkout() {
    //     return "checkout";
    // }

    // @GetMapping("/about")
    // public String about() {
    //     return "about";
    // }

    // @GetMapping("/contact")
    // public String contact() {
    //     return "contact";
    // }

    // Load blank page (Can be deleted)
    // (Have no Blank View YET)
    @GetMapping(BLANK_VIEW)
    public ModelAndView blank() {
        // check current account still valid
        if (!isValidAccount()) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(BLANK_TEMP);
            mav.addObject(USER_PARAM, mCurrentAccount);
            mIsByPass = false;
            return mav;
        }
    }

    // Load forbidden page (Can be deleted)
    // (Have no Forbidden View YET)
    @GetMapping(FORBIDDEN_VIEW)
    public String forbidden() {
        mIsByPass = false;
        return FORBIDDEN_TEMP;
    }

    // Check valid account
    private boolean isValidAccount() {
        // check bypass
        if (mIsByPass) {
            return true;
        } else {
            mCurrentAccount = authenticationUtil.getAccount();
            // System.out.print(mCurrentAccount.getHoTen());
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
