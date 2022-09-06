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
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;

    // Load checkout
    @GetMapping("")
    public ModelAndView checkout() {
        ModelAndView mav;
        // check current account still valid
        if (!isValidAccount()) {
            mav = new ModelAndView(REDIRECT_PREFIX + LOGIN_VIEW);
        } else {
            mav = new ModelAndView(CHECKOUT_TEMP);
            var idKhacHang = mCurrentAccount.getId();
            var gioHang = gioHangService.getGioHang(idKhacHang);
            // check gio_hang exist
            if (gioHang == null) {
                gioHangService.createGioHang(idKhacHang);
            }
            mav.addObject("client", mCurrentAccount);
            mav.addObject("cart", gioHang);
            mav.addObject("login", mCurrentAccount != null);
            mav.addObject("defaultProvince", DEFAULT_PROVINCE);
            mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject("topSaleProducts", sanPhamService.getDsSanPhamTopSale().subList(0, 4));
            mav.addObject("provinces", tinhThanhService.getDsTinhThanh());
        }
<<<<<<< HEAD
=======

        mav.addObject("client", mCurrentAccount);
        mav.addObject("dsTinhThanh", thanhService.getDsTinhThanh());
        mav.addObject("cart", gioHang);
        mav.addObject("newProducts", productService.getDsSanPhamNewest());
        mav.addObject("some_products", productService.getDsSanPhamAscendingPrice().subList(0, 3));
        mav.addObject("some_newProducts", productService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject("phiVanChuyen", thanhService.getTinhThanh(mCurrentAccount.getIdTinhThanh()));
        mav.addObject("login", mCurrentAccount != null);
        mav.addObject("dsHang", gioHang.getDsChiTietGioHang());
        
>>>>>>> 24267b3c5216efd1fb3057ab3bcdc3cd0c19623b
        showMessageBox(mav);
        mIsByPass = false;
        return mav;
    }

    // Checkout
    @PostMapping("")
    public String checkout(NguoiNhan nguoiNhan, boolean differentAddress, String paymentMethod) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGIN_VIEW;
        } else {
            var gioHang = mCurrentAccount.getGioHang();
            mIsMsgShow = true;
            // mIsByPass = true;
            // check cart
            if (gioHang.getTongSoLuong() <= 0) {
                mMsg = "Không thể thanh toán giỏ hàng trống!";
                return REDIRECT_PREFIX + CHECKOUT_VIEW;
            } else {
                // check exists credit_card
                if (paymentMethod.equals(CARD) && mCurrentAccount.getCreditCard() == null) {
                    mMsg = "Bạn chưa có thông tin thẻ tín dụng trong tài khoản!";
                    return REDIRECT_PREFIX + CHECKOUT_VIEW;
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
                    var tongGioHang = gioHang.getTongGioHang();
                    donHang.setTongGioHang(tongGioHang);
                    var chiPhiVanChuyen = mCurrentAccount.getTinhThanh().getChiPhiVanChuyen();
                    donHang.setChiPhiVanChuyen(chiPhiVanChuyen);
                    var giamGia = gioHang.getGiamGia();
                    donHang.setGiamGia(giamGia);
                    donHang.setTongDonHang(tongGioHang + chiPhiVanChuyen - giamGia);
                    donHang.setPhuongThucThanhToan(paymentMethod);
                    donHang.setTrangThai(DEFAULT_STATUS);
                    donHang.setIdKhachHang(mCurrentAccount.getId());
                    donHang.setIdNguoiNhan(nguoiNhan.getId());
                    donHang = donHangService.saveDonHang(donHang);
                    var idGioHang = gioHang.getId();
                    for (var chiTietGioHang : gioHang.getDsChiTietGioHang()) {
                        var chiTietDonHang = new ChiTietDonHang();
                        chiTietDonHang.setSoLuongSanPhan(chiTietGioHang.getSoLuongSanPhan());
                        chiTietDonHang.setGiaBanSanPham(chiTietGioHang.getGiaBanSanPham());
                        chiTietDonHang.setTongTienSanPham(chiTietGioHang.getTongTienSanPham());
                        var idSanPham = chiTietGioHang.getSanPham().getId();
                        chiTietDonHang.setId(new ChiTietDonHangId(donHang.getId(), idSanPham));
                        chiTietDonHangService.saveChiTietDonHang(chiTietDonHang);
                        chiTietGioHangService.deleteChiTietGioHang(new ChiTietGioHangId(idGioHang, idSanPham));
                    }
                    gioHangService.deleteGioHang(idGioHang);
                    gioHangService.createGioHang(idGioHang);
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
