package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;
import static java.lang.Math.*;

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
    private String mMsg;
    private boolean mIsMsgShow;

    @GetMapping("")
    public String detail() {
        return REDIRECT_PREFIX + PRODUCT_VIEW;
    }

    // Load detail
    @GetMapping(FIND_VIEW)
    public ModelAndView detailFind(int id) {
        var mav = new ModelAndView(DETAIL_TEMP);
        var client = authenticationUtil.getAccount();
        var product = sanPhamService.getSanPham(id);
        mav.addObject(TITLE_PARAM, CHI_TIET);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(PRODUCT_PARAM, product);
        mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 4));
        mav.addObject(LIMIT_PARAM, product.getTonKho());
        mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
        return mav;
    }

    // Load search
    @GetMapping(SEARCH_VIEW)
    public ModelAndView detailSearch(String name) {
        ModelAndView mav;
        var client = authenticationUtil.getAccount();
        var product = sanPhamService.getSanPham(name);
        if (product == null) {
            mav = new ModelAndView(BLANK_TEMP);
            mav.addObject(TITLE_PARAM, CHI_TIET);
            mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 4));
        } else {
            mav = new ModelAndView(DETAIL_TEMP);
            mav.addObject(TITLE_PARAM, CHI_TIET);
            mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(PRODUCT_PARAM, product);
            mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 4));
            mav.addObject(LIMIT_PARAM, product.getTonKho());
        }
        return mav;
    }

    // Rate product
    @PostMapping(RATE_VIEW)
    public String detailRate(NhanXet nhanXet, int idSanPham) {
        var client = authenticationUtil.getAccount();
        // check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGIN_VIEW;
        } else {
            var product = sanPhamService.getSanPham(idSanPham);
            var votes = product.getDsNhanXet();
            var votesSize = votes.size();
            var rate = 0;
            // get all danh_gia of product
            for (var i = 0; i < votesSize; i++) {
                rate += votes.get(i).getDanhGia();
            }
            var idVote = new NhanXetId(client.getId(), idSanPham);
            var clientVote = nhanXetService.getNhanXet(idVote);
            if (clientVote != null) {
                rate -= clientVote.getDanhGia();
            } else {
                votesSize += 1;
            }
            product.setDanhGia(round((rate + nhanXet.getDanhGia()) / votesSize));
            product = sanPhamService.saveSanPham(product);
            nhanXet.setId(idVote);
            nhanXet = nhanXetService.saveNhanXet(nhanXet);
            mIsMsgShow = true;
            mMsg = "Nhận xét sản phẩm thành công!";
            return REDIRECT_PREFIX + DETAIL_VIEW + FIND_VIEW + "?id=" + product.getId();
        }
    }
}
