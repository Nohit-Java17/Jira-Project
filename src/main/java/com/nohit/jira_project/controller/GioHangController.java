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
        var khachHang = authenticationUtil.getAccount();
        // Check current account still valid
        if (khachHang == null) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var mav = new ModelAndView(CART_TEMP);
            var gioHang = gioHangService.getGioHang(khachHang.getId());
            var tongSoLuong = gioHang.getTongSoLuong();
            var dsTonkho = new ArrayList<>();
            // get ton_kho each product
            for (var item : gioHang.getDsChiTietGioHang()) {
                dsTonkho.add(item.getSanPham().getTonKho());
            }
            mav.addObject(TITLE_PARAM, GIO_HANG);
            mav.addObject(CART_PARAM, gioHang);
            mav.addObject(LOGIN_PARAM, khachHang != null);
            mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 2));
            mav.addObject(PROVINCES_PARAM, tinhThanhService.getDsTinhThanh());
            mav.addObject(COUPON_PARAM, tongSoLuong < 1 ? 0 : gioHang.getGiamGia());
            mav.addObject(SHIPFEE_PARAM, tongSoLuong < 1 ? 0 : gioHang.getTinhThanh().getChiPhiVanChuyen());
            mav.addObject(LIMITS_PARAM, dsTonkho);
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            return mav;
        }
    }

    // Add to cart
    @RequestMapping(value = ADD_VIEW + PRODUCT_VIEW, method = { GET, POST })
    public String cartAddProduct(int id, int soLuongSanPham) {
        var khachHang = authenticationUtil.getAccount();
        // Check current account still valid
        if (khachHang == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var idKhachHang = khachHang.getId();
            var gioHang = gioHangService.getGioHang(idKhachHang);
            var sanPham = sanPhamService.getSanPham(id);
            var idChiTietGioHang = new ChiTietGioHangId(idKhachHang, id);
            mIsMsgShow = true;
            // chech chi_tiet_gio_hang exist
            if (chiTietGioHangService.getChiTietGioHang(idChiTietGioHang) != null) {
                mMsg = sanPham.getTen() + " đã tồn tại trong giỏ hàng!";
            } else {
                var chiTietGioHang = new ChiTietGioHang();
                var giaBanSanPham = sanPham.getGiaGoc() - sanPham.getKhuyenMai();
                chiTietGioHang.setId(idChiTietGioHang);
                chiTietGioHang.setSoLuongSanPham(soLuongSanPham);
                chiTietGioHang.setGiaBanSanPham(giaBanSanPham);
                chiTietGioHang = chiTietGioHangService.saveChiTietGioHang(chiTietGioHang);
                gioHang.setTongSoLuong(gioHang.getTongSoLuong() + soLuongSanPham);
                gioHang.setTongGioHang(gioHang.getTongGioHang() + chiTietGioHang.getTongTienSanPham());
                gioHang = gioHangService.saveGioHang(gioHang);
                mMsg = "Thêm " + sanPham.getTen() + " vào giỏ hàng thành công!";
            }
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Update coupon
    @RequestMapping(value = EDIT_VIEW + COUPON_VIEW, method = { GET, PUT })
    public String cartEditCoupon(String couponCode) {
        var khachHang = authenticationUtil.getAccount();
        // Check current account still valid
        if (khachHang == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var gioHang = gioHangService.getGioHang(khachHang.getId());
            var coupon = COUPON_MAP.get(couponCode);
            mIsMsgShow = true;
            // check coupon
            if (coupon == null) {
                mMsg = "Mã giảm giá chưa chính xác!";
            } else {
                gioHang.setGiamGia(coupon);
                gioHang = gioHangService.saveGioHang(gioHang);
                mMsg = "Áp dụng giảm giá thành công!";
            }
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Update ship fee
    @RequestMapping(value = EDIT_VIEW + SHIP_FEE_VIEW, method = { GET, PUT })
    public String cartEditShipFee(int id, String huyenQuan) {
        var khachHang = authenticationUtil.getAccount();
        // Check current account still valid
        if (khachHang == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var gioHang = gioHangService.getGioHang(khachHang.getId());
            gioHang.setHuyenQuan(huyenQuan);
            gioHang.setIdTinhThanh(id);
            gioHang = gioHangService.saveGioHang(gioHang);
            mIsMsgShow = true;
            mMsg = "Cập nhật  địa chỉ giao hàng cho giỏ hàng thành công!";
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Update cart
    @RequestMapping(value = SAVE_VIEW, method = { GET, PUT })
    public String cartSave(int[] productSize) {
        var khachHang = authenticationUtil.getAccount();
        // Check current account still valid
        if (khachHang == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var gioHang = gioHangService.getGioHang(khachHang.getId());
            var tongSoLuong = 0;
            var tongGioHang = 0;
            var index = 0;
            // update chi_tiet_gio_hang
            for (var item : gioHang.getDsChiTietGioHang()) {
                item.setSoLuongSanPham(productSize[index]);
                item = chiTietGioHangService.saveChiTietGioHang(item);
                tongSoLuong += productSize[index];
                tongGioHang += item.getTongTienSanPham();
                index++;
            }
            gioHang.setTongSoLuong(tongSoLuong);
            gioHang.setTongGioHang(tongGioHang);
            gioHang = gioHangService.saveGioHang(gioHang);
            mIsMsgShow = true;
            mMsg = "Cập nhật giỏ hàng thành công!";
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Delete chi_tiet_gio_hang
    @RequestMapping(value = DELETE_VIEW + PRODUCT_VIEW, method = { GET, DELETE })
    public String cartDeleteProduct(int id) {
        var khachHang = authenticationUtil.getAccount();
        // Check current account still valid
        if (khachHang == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var idKhachHang = khachHang.getId();
            var gioHang = gioHangService.getGioHang(idKhachHang);
            var chiTietGioHang = new ChiTietGioHangId(idKhachHang, id);
            gioHang.setTongGioHang(gioHang.getTongGioHang()
                    - chiTietGioHangService.getChiTietGioHang(chiTietGioHang).getTongTienSanPham());
            gioHang.setTongSoLuong(gioHang.getTongSoLuong()
                    - chiTietGioHangService.getChiTietGioHang(chiTietGioHang).getSoLuongSanPham());
            gioHang = gioHangService.saveGioHang(gioHang);
            chiTietGioHangService.deleteChiTietGioHang(chiTietGioHang);
            mIsMsgShow = true;
            mMsg = sanPhamService.getSanPham(id).getTen() + " đã được xóa khỏi giỏ hàng thành công!";
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }
}
