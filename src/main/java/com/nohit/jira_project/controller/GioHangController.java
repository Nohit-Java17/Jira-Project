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

    // Fields
    private KhachHang mCurrentAccount;
    private GioHang mClienCart;
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;

    // Load cart
    @GetMapping("")
    public ModelAndView cart() {
        // Check current account still valid
        if (!isValidAccount()) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var mav = new ModelAndView(CART_TEMP);
            mClienCart = gioHangService.getGioHang(mCurrentAccount.getId());
            var tongSoLuong = mClienCart.getTongSoLuong();
            var dsTonkho = new ArrayList<>();
            for (var item : mClienCart.getDsChiTietGioHang()) {
                dsTonkho.add(item.getSanPham().getTonKho());
            }
            mav.addObject("cart", mClienCart);
            mav.addObject("login", mCurrentAccount != null);
            mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject("topSaleProducts", sanPhamService.getDsSanPhamTopSale().subList(0, 2));
            mav.addObject("provinces", tinhThanhService.getDsTinhThanh());
            mav.addObject("coupon", tongSoLuong < 1 ? 0 : mClienCart.getGiamGia());
            mav.addObject("shipFee", tongSoLuong < 1 ? 0 : mClienCart.getTinhThanh().getChiPhiVanChuyen());
            mav.addObject("limit", dsTonkho);
            showMessageBox(mav);
            mIsByPass = false;
            return mav;
        }
    }

    // Update coupon
    @RequestMapping(value = COUPON_VIEW, method = { GET, PUT })
    public String cartCoupon(String couponCode) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var coupon = COUPON_MAP.get(couponCode);
            mIsMsgShow = true;
            if (coupon == null) {
                mMsg = "Mã giảm giá chưa chính xác!";
            } else {
                mClienCart.setGiamGia(coupon);
                gioHangService.saveGioHang(mClienCart);
                mMsg = "Áp dụng giảm giá thành công!";
            }
            mIsByPass = true;
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Load ship fee
    @RequestMapping(value = SHIP_FEE_VIEW, method = { GET, PUT })
    public String cartShipFee(int idTinhThanh, String huyenQuan) {
        // Check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            mClienCart.setHuyenQuan(huyenQuan);
            mClienCart.setIdTinhThanh(idTinhThanh);
            gioHangService.saveGioHang(mClienCart);
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
                var tongTienSanPham = productSize[index] * item.getGiaBanSanPham();
                item.setSoLuongSanPhan(productSize[index]);
                item.setTongTienSanPham(tongTienSanPham);
                chiTietGioHangService.saveChiTietGioHang(item);
                tongSoLuong += productSize[index];
                tongGioHang += tongTienSanPham;
                index++;
            }
            mClienCart.setTongSoLuong(tongSoLuong);
            mClienCart.setTongGioHang(tongGioHang);
            gioHangService.saveGioHang(mClienCart);
            mIsMsgShow = true;
            mMsg = "Cập nhật giỏ hàng thành công!";
            mIsByPass = true;
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // Delete chi_tiet_gio_hang
    @RequestMapping(value = DELETE_VIEW, method = { GET, DELETE })
    public String cartDeleteProduct(int idSanPham) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var chiTietGioHang = new ChiTietGioHangId(mCurrentAccount.getId(), idSanPham);
            mClienCart.setTongGioHang(mClienCart.getTongGioHang()
                    - chiTietGioHangService.getChiTietGioHang(chiTietGioHang).getTongTienSanPham());
            mClienCart.setTongSoLuong(mClienCart.getTongSoLuong()
                    - chiTietGioHangService.getChiTietGioHang(chiTietGioHang).getSoLuongSanPhan());
            gioHangService.saveGioHang(mClienCart);
            chiTietGioHangService.deleteChiTietGioHang(chiTietGioHang);
            mIsMsgShow = true;
            mMsg = sanPhamService.getSanPham(idSanPham).getTen() + " đã được xóa khỏi giỏ hàng thành công!";
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
            return mCurrentAccount != null;
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
