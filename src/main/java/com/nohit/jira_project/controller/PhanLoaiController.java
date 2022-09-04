package com.nohit.jira_project.controller;

import java.util.*;

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
        var radioCheck = 0;
        mav.addObject("client", khachHang);
        mav.addObject("cart", gioHang);
        mav.addObject("login", khachHang != null);
        mav.addObject("products", sanPhamService.getDsSanPhamTonKho());
        mav.addObject("radioCheck", radioCheck);
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
            var id = khachHang.getId();
            gioHang = gioHangService.getGioHang(id);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(id);
                gioHangService.saveGioHang(gioHang);
            }
        }
        List<SanPham> dsSanPham;
        int radioCheck;
        // filter function
        switch (filter) {
            case "laptop": {
                dsSanPham = sanPhamService.getDsSanPhamLaptop();
                radioCheck = 1;
                break;
            }
            case "computer": {
                dsSanPham = sanPhamService.getDsSanPhamComputer();
                radioCheck = 2;
                break;
            }
            case "tablet": {
                dsSanPham = sanPhamService.getDsSanPhamTablet();
                radioCheck = 3;
                break;
            }
            case "smartphone": {
                dsSanPham = sanPhamService.getDsSanPhamSmartPhone();
                radioCheck = 4;
                break;
            }
            case "devices": {
                dsSanPham = sanPhamService.getDsSanPhamDevices();
                radioCheck = 5;
                break;
            }
            default: {
                dsSanPham = sanPhamService.getDsSanPhamTonKho();
                radioCheck = 0;
                break;
            }
        }
        mav.addObject("client", khachHang);
        mav.addObject("cart", gioHang);
        mav.addObject("login", khachHang != null);
        mav.addObject("products", dsSanPham);
        mav.addObject("radioCheck", radioCheck);
        return mav;
    }
}
