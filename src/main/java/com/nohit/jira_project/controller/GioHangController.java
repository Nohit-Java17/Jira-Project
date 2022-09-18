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
    private KhachHang mCurrentAccount;
    private GioHang mClienCart;
    private String mMsg;
    private boolean mIsMsgShow;
    private boolean mIsByPass;

    // Load cart
    @GetMapping("")
    public ModelAndView cart() {
        // Check current account still valid
        if (!isValidAccount()) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var mav = new ModelAndView(CART_TEMP);
            var tongSoLuong = mClienCart.getTongSoLuong();
            var dsTonkho = new ArrayList<>();
            // get ton_kho each product
            for (var item : mClienCart.getDsChiTietGioHang()) {
                dsTonkho.add(item.getSanPham().getTonKho());
            }
            mav.addObject(TITLE_PARAM, GIO_HANG);
            mav.addObject(CART_PARAM, mClienCart);
            mav.addObject(LOGIN_PARAM, mCurrentAccount != null);
            mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject(TOP_SALES_PARAM, sanPhamService.getDsSanPhamTopSale().subList(0, 2));
            mav.addObject(PROVINCES_PARAM, tinhThanhService.getDsTinhThanh());
            mav.addObject(COUPON_PARAM, tongSoLuong < 1 ? 0 : mClienCart.getGiamGia());
            mav.addObject(SHIPFEE_PARAM, tongSoLuong < 1 ? 0 : mClienCart.getTinhThanh().getChiPhiVanChuyen());
            mav.addObject(LIMITS_PARAM, dsTonkho);
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            mIsByPass = false;
            return mav;
        }
    }

    // Add to cart
    @RequestMapping(value = ADD_VIEW + PRODUCT_VIEW, method = { GET, POST })
    public String cartAddProduct(int id, int soLuongSanPham) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var idKhachHang = mCurrentAccount.getId();
            var sanPham = sanPhamService.getSanPham(id);
            var idChiTietGioHang = new ChiTietGioHangId(idKhachHang, id);
            mIsMsgShow = true;
            // chech chi_tiet_gio_hang exist
            if (chiTietGioHangService.getChiTietGioHang(idChiTietGioHang) != null) {
                mMsg = sanPham.getTen() + " đã tồn tại trong giỏ hàng!";
            } else {
                var chiTietGioHang = new ChiTietGioHang();
                var giaBanSanPham = sanPham.getGiaGoc() - sanPham.getKhuyenMai();
                mClienCart = gioHangService.getGioHang(idKhachHang);
                chiTietGioHang.setId(idChiTietGioHang);
                chiTietGioHang.setSoLuongSanPham(soLuongSanPham);
                chiTietGioHang.setGiaBanSanPham(giaBanSanPham);
                chiTietGioHang = chiTietGioHangService.saveChiTietGioHang(chiTietGioHang);
                mClienCart.setTongSoLuong(mClienCart.getTongSoLuong() + soLuongSanPham);
                mClienCart.setTongGioHang(mClienCart.getTongGioHang() + chiTietGioHang.getTongTienSanPham());
                mClienCart = gioHangService.saveGioHang(mClienCart);
                mMsg = "Thêm " + sanPham.getTen() + " vào giỏ hàng thành công!";
            }
            mIsByPass = true;
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Update coupon
    @RequestMapping(value = EDIT_VIEW + COUPON_VIEW, method = { GET, PUT })
    public String cartEditCoupon(String couponCode) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var coupon = COUPON_MAP.get(couponCode);
            mIsMsgShow = true;
            // check coupon
            if (coupon == null) {
                mMsg = "Mã giảm giá chưa chính xác!";
            } else {
                mClienCart.setGiamGia(coupon);
                mClienCart = gioHangService.saveGioHang(mClienCart);
                mMsg = "Áp dụng giảm giá thành công!";
            }
            mIsByPass = true;
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Update ship fee
    @RequestMapping(value = EDIT_VIEW + SHIP_FEE_VIEW, method = { GET, PUT })
    public String cartEditShipFee(int id, String huyenQuan) {
        // Check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            mClienCart.setHuyenQuan(huyenQuan);
            mClienCart.setIdTinhThanh(id);
            mClienCart = gioHangService.saveGioHang(mClienCart);
            mIsMsgShow = true;
            mMsg = "Cập nhật  địa chỉ giao hàng cho giỏ hàng thành công!";
            mIsByPass = true;
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Update cart
    @RequestMapping(value = SAVE_VIEW, method = { GET, PUT })
    public String cartSave(int[] productSize) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var tongSoLuong = 0;
            var tongGioHang = 0;
            var index = 0;
            // update chi_tiet_gio_hang
            for (var item : mClienCart.getDsChiTietGioHang()) {
                item.setSoLuongSanPham(productSize[index]);
                item = chiTietGioHangService.saveChiTietGioHang(item);
                tongSoLuong += productSize[index];
                tongGioHang += item.getTongTienSanPham();
                index++;
            }
            mClienCart.setTongSoLuong(tongSoLuong);
            mClienCart.setTongGioHang(tongGioHang);
            mClienCart = gioHangService.saveGioHang(mClienCart);
            mIsMsgShow = true;
            mMsg = "Cập nhật giỏ hàng thành công!";
            mIsByPass = true;
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Delete chi_tiet_gio_hang
    @RequestMapping(value = DELETE_VIEW + PRODUCT_VIEW, method = { GET, DELETE })
    public String cartDeleteProduct(int id) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var chiTietGioHang = new ChiTietGioHangId(mCurrentAccount.getId(), id);
            mClienCart.setTongGioHang(mClienCart.getTongGioHang()
                    - chiTietGioHangService.getChiTietGioHang(chiTietGioHang).getTongTienSanPham());
            mClienCart.setTongSoLuong(mClienCart.getTongSoLuong()
                    - chiTietGioHangService.getChiTietGioHang(chiTietGioHang).getSoLuongSanPham());
            mClienCart = gioHangService.saveGioHang(mClienCart);
            chiTietGioHangService.deleteChiTietGioHang(chiTietGioHang);
            mIsMsgShow = true;
            mMsg = sanPhamService.getSanPham(id).getTen() + " đã được xóa khỏi giỏ hàng thành công!";
            mIsByPass = true;
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Check valid account
    private boolean isValidAccount() {
        // check bypass
        if (mIsByPass) {
            return true;
        } else {
            mCurrentAccount = authenticationUtil.getAccount();
            var result = mCurrentAccount != null;
            mClienCart = result ? gioHangService.getGioHang(mCurrentAccount.getId()) : new GioHang();
            return result;
        }
    }
}
