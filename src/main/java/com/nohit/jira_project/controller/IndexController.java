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
@RequestMapping(value = { INDEX_VIEW, "/", "" })
public class IndexController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private SubcribeService subcribeService;

    @Autowired
    private ApplicationUtil applicationUtil;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private StringUtil stringUtil;

    // Fields
    private String mMsg;
    private boolean mIsMsgShow;

    // Load dashboard
    @GetMapping("")
    public ModelAndView index() {
        var mav = new ModelAndView(INDEX_TEMP);
        var khachHang = authenticationUtil.getAccount();
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
        mav.addObject("newProducts", sanPhamService.getDsSanPhamNewest().subList(0, 6));
        mav.addObject("topSaleProducts", sanPhamService.getDsSanPhamTopSale().subList(0, 3));
        mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
        showMessageBox(mav);
        return mav;
    }

    // Add thu_phan_hoi
    @PostMapping(SUB_VIEW)
    public String subcribe(Subcribe subcribe) {
        var trueEmail = stringUtil.removeSpCharsBeginAndEnd(subcribe.getEmail()).toLowerCase();
        mIsMsgShow = true;
        // check email is already exist
        if (subcribeService.getSubcribe(trueEmail) != null) {
            mMsg = "Email này đã được đăng ký!";
            return REDIRECT_PREFIX + REGISTER_VIEW;
        } else {
            subcribe.setEmail(trueEmail);
            subcribeService.saveSubcribe(subcribe);
            mMsg = "Đăng ký thành công!";
            return REDIRECT_PREFIX + INDEX_VIEW;
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
