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
import static com.nohit.jira_project.constant.ApplicationConstant.Payment.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(CHECKOUT_VIEW)
public class ThanhToanController {
    @Autowired
    private NguoiNhanService nguoiNhanService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private ChiTietGioHangService chiTietGioHangService;

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private ChiTietDonHangService chiTietDonHangService;

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

    // Load checkout
    @GetMapping("")
    public ModelAndView checkout() {
        // check current account still valid
        if (!isValidAccount()) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var mav = new ModelAndView(CHECKOUT_TEMP);
            mClienCart = gioHangService.getGioHang(mCurrentAccount.getId());
            var provinceCart = mClienCart.getIdTinhThanh();
            var wardCart = mClienCart.getHuyenQuan();
            var differentAddress = provinceCart != mCurrentAccount.getIdTinhThanh()
                    || !wardCart.equals(mCurrentAccount.getHuyenQuan());
            mav.addObject("cart", mClienCart);
            mav.addObject("login", mCurrentAccount != null);
            mav.addObject("client", mCurrentAccount);
            mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject("provinces", tinhThanhService.getDsTinhThanh());
            mav.addObject("defaultProvince", differentAddress ? provinceCart : DEFAULT_PROVINCE);
            mav.addObject("defaultWard", differentAddress ? wardCart : "");
            showMessageBox(mav);
            mIsByPass = false;
            return mav;
        }
    }

    // Checkout
    @PostMapping("")
    public String checkout(NguoiNhan nguoiNhan, boolean differentAddress, String paymentMethod) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGIN_VIEW;
        } else {
            mIsMsgShow = true;
            mIsByPass = true;
            // check cart
            if (mClienCart.getTongSoLuong() <= 0) {
                mMsg = "Không thể thanh toán giỏ hàng trống!";
                return REDIRECT_PREFIX + CHECKOUT_VIEW;
            } else {
                // check exists credit_card
                if (paymentMethod.equals(CARD) && mCurrentAccount.getCreditCard() == null) {
                    mMsg = "Bạn chưa có thông tin thẻ tín dụng trong tài khoản!";
                    return REDIRECT_PREFIX + PROFILE_VIEW;
                } else {
                    if (!differentAddress) {
                        nguoiNhan.setHoTen(mCurrentAccount.getHoTen());
                        nguoiNhan.setSoDienThoai(mCurrentAccount.getSoDienThoai());
                        nguoiNhan.setDiaChi(mCurrentAccount.getDiaChi());
                        nguoiNhan.setXaPhuong(mCurrentAccount.getXaPhuong());
                        nguoiNhan.setHuyenQuan(mCurrentAccount.getHuyenQuan());
                        nguoiNhan.setIdTinhThanh(mCurrentAccount.getIdTinhThanh());
                    }
                    nguoiNhan = nguoiNhanService.saveNguoiNhan(nguoiNhan);
                    var donHang = new DonHang();
                    donHang.setNgayDat(new Date());
                    var tongGioHang = mClienCart.getTongGioHang();
                    donHang.setTongGioHang(tongGioHang);
                    var chiPhiVanChuyen = mCurrentAccount.getTinhThanh().getChiPhiVanChuyen();
                    donHang.setChiPhiVanChuyen(chiPhiVanChuyen);
                    var giamGia = mClienCart.getGiamGia();
                    donHang.setGiamGia(giamGia);
                    donHang.setTongDonHang(tongGioHang + chiPhiVanChuyen - giamGia);
                    donHang.setPhuongThucThanhToan(paymentMethod);
                    donHang.setTrangThai(DEFAULT_STATUS);
                    donHang.setIdKhachHang(mCurrentAccount.getId());
                    donHang.setIdNguoiNhan(nguoiNhan.getId());
                    donHang = donHangService.saveDonHang(donHang);
                    var idGioHang = mClienCart.getId();
                    for (var chiTietGioHang : mClienCart.getDsChiTietGioHang()) {
                        var chiTietDonHang = new ChiTietDonHang();
                        chiTietDonHang.setSoLuongSanPhan(chiTietGioHang.getSoLuongSanPhan());
                        chiTietDonHang.setGiaBanSanPham(chiTietGioHang.getGiaBanSanPham());
                        chiTietDonHang.setTongTienSanPham(chiTietGioHang.getTongTienSanPham());
                        var idSanPham = chiTietGioHang.getSanPham().getId();
                        chiTietDonHang.setId(new ChiTietDonHangId(donHang.getId(), idSanPham));
                        chiTietDonHangService.saveChiTietDonHang(chiTietDonHang);
                        chiTietGioHangService.deleteChiTietGioHang(new ChiTietGioHangId(idGioHang, idSanPham));
                    }
                    gioHangService.saveGioHang(gioHangService.createGioHang(mCurrentAccount));
                    mMsg = "Đơn hàng đã được đặt thành công!";
                    return REDIRECT_PREFIX + HISTORY_VIEW;
                }
            }
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
