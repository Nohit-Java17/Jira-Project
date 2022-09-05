package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.ApplicationConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(CATEGORY_VIEW)
public class PhanLoaiController {
    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private GioHangService gioHangService;

    // Load category
    @GetMapping("")
    public ModelAndView category() {
        var mav = new ModelAndView(CATEGORY_TEMP);
        GioHang gioHang;
        var khachHang = authenticationUtil.getAccount();
        // check current account still valid
        if (khachHang == null) {
            gioHang = new GioHang();
        } else {
            var idKhachHang = khachHang.getId();
            gioHang = gioHangService.getGioHang(idKhachHang);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(idKhachHang);
                gioHangService.saveGioHang(gioHang);
            }
        }
        mav.addObject("client", khachHang);
        mav.addObject("cart", gioHang);
        mav.addObject("login", khachHang != null);
        mav.addObject("products", sanPhamService.getDsSanPhamTonKho());
        mav.addObject("radioCheck", DEFAULT_CATEGORY);
        return mav;
    }

    // Load top sale products
    @GetMapping(FILTER_VIEW)
    public ModelAndView categoryFilter(String filter) {
        var mav = new ModelAndView(CATEGORY_TEMP);
        GioHang gioHang;
        var khachHang = authenticationUtil.getAccount();
        // check current account still valid
        if (khachHang == null) {
            gioHang = new GioHang();
        } else {
            var idKhachHang = khachHang.getId();
            gioHang = gioHangService.getGioHang(idKhachHang);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(idKhachHang);
                gioHangService.saveGioHang(gioHang);
            }
        }
        mav.addObject("client", khachHang);
        mav.addObject("cart", gioHang);
        mav.addObject("login", khachHang != null);
        mav.addObject("products", sanPhamService.getDsSanPham(filter));
        mav.addObject("radioCheck", CATEGORIES_MAP.get(filter));
        return mav;
    }
}
