package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.common.Bean.*;
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

    @GetMapping("")
    public String detail() {
        _isMsgShow = true;
        _msg = "Cần chọn 1 sản phẩm để xem!";
        return REDIRECT_PREFIX + PRODUCT_VIEW;
    }

    // Load detail
    @GetMapping(FIND_VIEW)
    public ModelAndView detailFind(int id) {
        var product = sanPhamService.getSanPham(id);
        // check if product is exist
        if (product == null || product.getTonKho() < 1) {
            _isMsgShow = true;
            _msg = "Sản phẩm không còn tồn tại!";
            return new ModelAndView(REDIRECT_PREFIX + PRODUCT_VIEW);
        } else {
            var mav = new ModelAndView(DETAIL_TEMP);
            var client = authenticationUtil.getAccount();
            mav.addObject(TITLE_PARAM, CHI_TIET);
            mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(PRODUCT_PARAM, product);
            mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 4));
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Rate product
    @PostMapping(RATE_VIEW)
    public String detailRate(NhanXet nhanXet, int idSanPham) {
        var client = authenticationUtil.getAccount();
        _isMsgShow = true;
        // check current account still valid
        if (client == null) {
            _msg = "Cần đăng nhập để nhận xét sản phẩm!";
            return REDIRECT_PREFIX + LOGIN_VIEW;
        } else {
            var product = sanPhamService.getSanPham(idSanPham);
            // check if product is exist
            if (product == null || product.getTonKho() < 1) {
                _msg = "Sản phẩm không còn tồn tại!";
                return REDIRECT_PREFIX + PRODUCT_VIEW;
            } else {
                var votes = product.getDsNhanXet();
                var votesSize = votes.size();
                var rate = 0;
                // get all danh_gia of product
                for (var i = 0; i < votesSize; i++) {
                    rate += votes.get(i).getDanhGia();
                }
                var id = new NhanXetId(client.getId(), idSanPham);
                var clientVote = nhanXetService.getNhanXet(id);
                if (clientVote != null) {
                    rate -= clientVote.getDanhGia();
                } else {
                    votesSize++;
                }
                product.setDanhGia(round((rate + nhanXet.getDanhGia()) / votesSize));
                sanPhamService.updateDanhGia(idSanPham, product.getDanhGia());
                nhanXet.setId(id);
                nhanXet = nhanXetService.saveNhanXet(nhanXet);
                _msg = "Nhận xét sản phẩm thành công!";
                return REDIRECT_PREFIX + DETAIL_VIEW + FIND_VIEW + "?id=" + product.getId();
            }
        }
    }
}
