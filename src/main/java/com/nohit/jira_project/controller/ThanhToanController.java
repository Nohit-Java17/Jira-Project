package com.nohit.jira_project.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
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
import static org.springframework.util.StringUtils.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("")
public class ThanhToanController {
    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private CreditCardService creditCardService;

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

    @Autowired
    private ApplicationUtil applicationUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Fields
    private KhachHang mCurrentAccount;
    private GioHang mClienCart;
    private String mMsg;
    private boolean mIsMsgShow;
    private boolean mIsByPass;

    // Load checkout
    @GetMapping(CHECKOUT_VIEW)
    public ModelAndView checkout() {
        // check current account still valid
        if (!isValidAccount()) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var mav = new ModelAndView(CHECKOUT_TEMP);
            mClienCart = gioHangService.getGioHang(mCurrentAccount.getId());
            var provinceCart = mClienCart.getIdTinhThanh();
            var wardCart = mClienCart.getHuyenQuan();
            var differentAddress = provinceCart != mCurrentAccount.getIdTinhThanh();
            mav.addObject("cart", mClienCart);
            mav.addObject("login", mCurrentAccount != null);
            mav.addObject("client", mCurrentAccount);
            mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject("provinces", tinhThanhService.getDsTinhThanh());
            mav.addObject("defaultProvince", differentAddress ? provinceCart : DEFAULT_PROVINCE);
            mav.addObject("defaultWard", differentAddress ? wardCart : "");
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            mIsByPass = false;
            return mav;
        }
    }

    // Checkout
    @PostMapping(CHECKOUT_VIEW)
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
                } else if (!hasText(mCurrentAccount.getHoTen()) || !hasText(mCurrentAccount.getSoDienThoai())
                        || !hasText(mCurrentAccount.getDiaChi()) || !hasText(mCurrentAccount.getXaPhuong())
                        || !hasText(mCurrentAccount.getHuyenQuan())) {
                    mMsg = "Bạn chưa có thông tin cá nhân đầy đủ để thanh toán!";
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
                        chiTietDonHang.setSoLuongSanPham(chiTietGioHang.getSoLuongSanPham());
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

    // Load profile
    @GetMapping(PROFILE_VIEW)
    public ModelAndView profile() {
        // check current account still valid
        if (!isValidAccount()) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(PROFILE_TEMP);
            var id = mCurrentAccount.getId();
            var creditCard = creditCardService.getCreditCard(id);
            // check credit_card exist
            if (creditCard == null) {
                creditCard = new CreditCard();
                creditCard.setId(id);
                creditCardService.saveCreditCard(creditCard);
            }
            var gioHang = gioHangService.getGioHang(id);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(id);
                gioHangService.saveGioHang(gioHang);
            }
            mav.addObject("provinces", tinhThanhService.getDsTinhThanh());
            mav.addObject("creditCard", creditCard);
            mav.addObject("client", mCurrentAccount);
            mav.addObject("cart", gioHang);
            mav.addObject("login", mCurrentAccount != null);
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            mIsByPass = false;
            return mav;
        }
    }

    // Update info
    @RequestMapping(value = PROFILE_VIEW + INFO_VIEW, method = { GET, PUT })
    public String profileInfo(KhachHang khachHang) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            khachHang.setId(mCurrentAccount.getId());
            khachHang.setEmail(mCurrentAccount.getEmail());
            khachHang.setVaiTro(mCurrentAccount.getVaiTro());
            khachHangService.saveKhachHangWithoutPassword(khachHang);
            mIsMsgShow = true;
            mMsg = "Thông tin cơ bản đã được cập nhật thành công!";
            mIsByPass = true;
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }

    // Update password
    @PostMapping(PROFILE_VIEW + PASSWORD_VIEW)
    public String profilePassword(String oldPassword, String newPassword, String rePassword) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            mIsMsgShow = true;
            // check valid password
            if (passwordEncoder.matches(oldPassword, mCurrentAccount.getMatKhau()) && rePassword.equals(newPassword)) {
                mMsg = "Mật khẩu đã được cập nhật thành công!";
                khachHangService.updatePassword(mCurrentAccount.getId(), newPassword);
            } else {
                mMsg = "Chưa chính xác!";
            }
            mIsByPass = true;
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }

    // Update card
    @PostMapping(PROFILE_VIEW + CARD_VIEW)
    public String profileCard(CreditCard creditCard) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            creditCard.setId(mCurrentAccount.getId());
            creditCardService.saveCreditCard(creditCard);
            mIsMsgShow = true;
            mMsg = "Thanh toán qua thẻ được cập nhật thành công!";
            mIsByPass = true;
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }

    // Load history
    @GetMapping(HISTORY_VIEW)
    public ModelAndView history() {
        var khachHang = authenticationUtil.getAccount();
        // check current account still valid
        if (khachHang == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(HISTORY_TEMP);
            var idKhachHang = khachHang.getId();
            mav.addObject("cart", gioHangService.getGioHang(idKhachHang));
            mav.addObject("login", khachHang != null);
            mav.addObject("client", khachHang);
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            return mav;
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
}
