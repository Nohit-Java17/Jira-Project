package com.nohit.jira_project.controller;

import java.util.*;

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
@RequestMapping(PRODUCT_VIEW)
public class SanPhamController {
    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private ApplicationUtil applicationUtil;

    // Load product
    @GetMapping("")
    public ModelAndView product() {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var khachHang = authenticationUtil.getAccount();
        var dsSanPham = sanPhamService.getDsSanPhamTonKho();
        var maxDsSanPham = dsSanPham.size();
        var maxSize = (maxDsSanPham - 1) / DEFAULT_SIZE_PAGE + 1;
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
        mav.addObject("products",
                dsSanPham.subList(0, maxDsSanPham < DEFAULT_SIZE_PAGE ? maxDsSanPham : DEFAULT_SIZE_PAGE));
        mav.addObject("radioCheck", DEFAULT_PRODUCT);
        mav.addObject("maxSize", (dsSanPham.size() - 1) / DEFAULT_SIZE_PAGE + 1);
        mav.addObject("view", PAGE_VIEW + "?page=");
        mav.addObject("previous", PAGE_VIEW + "?page=" + 1);
        mav.addObject("next", PAGE_VIEW + "?page=" + (2 > maxSize ? maxSize : 2));
        return mav;
    }

    // Load product from page
    @GetMapping(PAGE_VIEW)
    public ModelAndView product(int page) {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var khachHang = authenticationUtil.getAccount();
        var dsSanPham = sanPhamService.getDsSanPhamTonKho();
        var maxDsSanPham = dsSanPham.size();
        var maxPage = page * DEFAULT_SIZE_PAGE;
        var maxSize = (maxDsSanPham - 1) / DEFAULT_SIZE_PAGE + 1;
        var previous = page - 1;
        var next = page + 1;
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
        mav.addObject("products",
                dsSanPham.subList((page - 1) * DEFAULT_SIZE_PAGE, maxPage > maxDsSanPham ? maxDsSanPham : maxPage));
        mav.addObject("radioCheck", DEFAULT_CATEGORY);
        mav.addObject("maxSize", maxSize);
        mav.addObject("view", PAGE_VIEW + "?page=");
        mav.addObject("previous", PAGE_VIEW + "?page=" + (previous < 1 ? 1 : previous));
        mav.addObject("next", PAGE_VIEW + "?page=" + (next > maxSize ? maxSize : next));
        return mav;
    }

    // Load sort products
    @GetMapping(SORT_VIEW)
    public ModelAndView productSort(String sort) {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var khachHang = authenticationUtil.getAccount();
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
        var maxDsSanPham = dsSanPham.size();
        var maxSize = (maxDsSanPham - 1) / DEFAULT_SIZE_PAGE + 1;
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
        mav.addObject("products",
                dsSanPham.subList(0, maxDsSanPham < DEFAULT_SIZE_PAGE ? maxDsSanPham : DEFAULT_SIZE_PAGE));
        mav.addObject("radioCheck", radioCheck);
        mav.addObject("maxSize", (dsSanPham.size() - 1) / DEFAULT_SIZE_PAGE + 1);
        mav.addObject("view", SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=");
        mav.addObject("previous", SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + 1);
        mav.addObject("next", SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + (2 > maxSize ? maxSize : 2));
        return mav;
    }

    // Load sort products
    @GetMapping(SORT_VIEW + PAGE_VIEW)
    public ModelAndView productSort(String sort, int page) {
        var mav = new ModelAndView(PRODUCT_TEMP);
        var khachHang = authenticationUtil.getAccount();
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
        var maxDsSanPham = dsSanPham.size();
        var maxPage = page * DEFAULT_SIZE_PAGE;
        var maxSize = (maxDsSanPham - 1) / DEFAULT_SIZE_PAGE + 1;
        var previous = page - 1;
        var next = page + 1;
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
        mav.addObject("products",
                dsSanPham.subList((page - 1) * DEFAULT_SIZE_PAGE, maxPage > maxDsSanPham ? maxDsSanPham : maxPage));
        mav.addObject("radioCheck", radioCheck);
        mav.addObject("maxSize", maxSize);
        mav.addObject("view", SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=");
        mav.addObject("previous",
                SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + (previous < 1 ? 1 : previous));
        mav.addObject("next",
                SORT_VIEW + PAGE_VIEW + "?sort=" + sort + "&page=" + (next > maxSize ? maxSize : next));
        return mav;
    }
}
