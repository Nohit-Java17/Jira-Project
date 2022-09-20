package com.nohit.jira_project.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import lombok.*;

import static com.nohit.jira_project.constant.ApplicationConstant.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(CART_VIEW)
public class GioHangController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private ChiTietGioHangService chiTietGioHangService;

    @Autowired
    private TinhThanhService tinhThanhService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private ApplicationUtil applicationUtil;

    // Fields
    private String mMsg;
    private boolean mIsMsgShow;

    // Load cart
    @GetMapping("")
    public ModelAndView cart() {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var mav = new ModelAndView(CART_TEMP);
            var cart = client.getGioHang();
            var productsCount = cart.getTongSoLuong();
            var inventories = new ArrayList<>();
            // get ton_kho each product
            for (var item : cart.getDsChiTietGioHang()) {
                inventories.add(item.getSanPham().getTonKho());
            }
            mav.addObject(TITLE_PARAM, GIO_HANG);
            mav.addObject(CART_PARAM, cart);
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 2));
            mav.addObject(PROVINCES_PARAM, tinhThanhService.getDsTinhThanh());
            mav.addObject(COUPON_PARAM, productsCount < 1 ? 0 : cart.getGiamGia());
            mav.addObject(SHIPFEE_PARAM, productsCount < 1 ? 0 : cart.getTinhThanh().getChiPhiVanChuyen());
            mav.addObject(LIMITS_PARAM, inventories);
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            return mav;
        }
    }

    // Add to cart
    @RequestMapping(value = ADD_VIEW + PRODUCT_VIEW, method = { GET, POST })
    public String cartAddProduct(int id, int soLuongSanPham) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var cart = client.getGioHang();
            var product = sanPhamService.getSanPham(id);
            var idCartDetail = new ChiTietGioHangId(client.getId(), id);
            mIsMsgShow = true;
            // chech chi_tiet_gio_hang exist
            if (chiTietGioHangService.getChiTietGioHang(idCartDetail) != null) {
                mMsg = product.getTen() + " đã tồn tại trong giỏ hàng!";
            } else {
                var cartDetail = new ChiTietGioHang();
                var price = product.getGiaGoc() - product.getKhuyenMai();
                cartDetail.setId(idCartDetail);
                cartDetail.setSoLuongSanPham(soLuongSanPham);
                cartDetail.setGiaBanSanPham(price);
                cartDetail = chiTietGioHangService.saveChiTietGioHang(cartDetail);
                cart.setTongSoLuong(cart.getTongSoLuong() + soLuongSanPham);
                cart.setTongGioHang(cart.getTongGioHang() + cartDetail.getTongTienSanPham());
                cart = gioHangService.saveGioHang(cart);
                mMsg = "Thêm " + product.getTen() + " vào giỏ hàng thành công!";
            }
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Update coupon
    @RequestMapping(value = EDIT_VIEW + COUPON_VIEW, method = { GET, PUT })
    public String cartEditCoupon(String maGiamGia) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var cart = client.getGioHang();
            var coupon = COUPON_MAP.get(maGiamGia);
            mIsMsgShow = true;
            // check coupon
            if (coupon == null) {
                mMsg = "Mã giảm giá chưa chính xác!";
            } else {
                cart.setGiamGia(coupon);
                cart = gioHangService.saveGioHang(cart);
                mMsg = "Áp dụng giảm giá thành công!";
            }
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Update ship fee
    @RequestMapping(value = EDIT_VIEW + SHIP_FEE_VIEW, method = { GET, PUT })
    public String cartEditShipFee(int id, String huyenQuan) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var cart = client.getGioHang();
            cart.setHuyenQuan(huyenQuan);
            cart.setIdTinhThanh(id);
            cart = gioHangService.saveGioHang(cart);
            mIsMsgShow = true;
            mMsg = "Cập nhật  địa chỉ giao hàng cho giỏ hàng thành công!";
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Update cart
    @RequestMapping(value = SAVE_VIEW, method = { GET, PUT })
    public String cartSave(int[] soSanPham) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var cart = client.getGioHang();
            var productsCount = 0;
            var cartTotal = 0;
            var index = 0;
            // update chi_tiet_gio_hang
            for (var item : cart.getDsChiTietGioHang()) {
                item.setSoLuongSanPham(soSanPham[index]);
                item = chiTietGioHangService.saveChiTietGioHang(item);
                productsCount += soSanPham[index];
                cartTotal += item.getTongTienSanPham();
                index++;
            }
            cart.setTongSoLuong(productsCount);
            cart.setTongGioHang(cartTotal);
            cart = gioHangService.saveGioHang(cart);
            mIsMsgShow = true;
            mMsg = "Cập nhật giỏ hàng thành công!";
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Delete chi_tiet_gio_hang
    @RequestMapping(value = DELETE_VIEW + PRODUCT_VIEW, method = { GET, DELETE })
    public String cartDeleteProduct(int id) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var cart = client.getGioHang();
            var cartDetail = new ChiTietGioHangId(client.getId(), id);
            cart.setTongGioHang(
                    cart.getTongGioHang() - chiTietGioHangService.getChiTietGioHang(cartDetail).getTongTienSanPham());
            cart.setTongSoLuong(
                    cart.getTongSoLuong() - chiTietGioHangService.getChiTietGioHang(cartDetail).getSoLuongSanPham());
            cart = gioHangService.saveGioHang(cart);
            chiTietGioHangService.deleteChiTietGioHang(cartDetail);
            mIsMsgShow = true;
            mMsg = sanPhamService.getSanPham(id).getTen() + " đã được xóa khỏi giỏ hàng thành công!";
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }
}
