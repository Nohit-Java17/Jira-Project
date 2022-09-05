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
@RequestMapping(PRODUCT_VIEW)
public class SanPhamController {
    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private GioHangService gioHangService;

    // Load product
    @GetMapping("")
    public ModelAndView product() {
        var mav = new ModelAndView(PRODUCT_TEMP);
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
    @GetMapping(SORT_VIEW)
    public ModelAndView productSort(String sort) {
        var mav = new ModelAndView(PRODUCT_TEMP);
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
        List<SanPham> dsSanPham;
        int radioCheck;
        // filter function
        switch (sort) {
            case "topSale": {
                dsSanPham = sanPhamService.getDsSanPhamTopSale();
                radioCheck = 1;
                break;
            }
            case "newest": {
                dsSanPham = sanPhamService.getDsSanPhamNewest();
                radioCheck = 2;
                break;
            }
            case "discount": {
                dsSanPham = sanPhamService.getDsSanPhamDescendingDiscount();
                radioCheck = 3;
                break;
            }
            case "ascendingPrice": {
                dsSanPham = sanPhamService.getDsSanPhamAscendingPrice();
                radioCheck = 4;
                break;
            }
            case "descendingPrice": {
                dsSanPham = sanPhamService.getDsSanPhamDescendingPrice();
                radioCheck = 5;
                break;
            }
            default: {
                dsSanPham = sanPhamService.getDsSanPham();
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
