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
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
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
    private String mMsg;
    private boolean mIsMsgShow;

    // Load checkout
    @GetMapping(CHECKOUT_VIEW)
    public ModelAndView checkout() {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var mav = new ModelAndView(CHECKOUT_TEMP);
            var cart = client.getGioHang();
            var provinceCart = cart.getIdTinhThanh();
            var wardCart = cart.getHuyenQuan();
            var differentAddress = provinceCart != client.getIdTinhThanh();
            mav.addObject(TITLE_PARAM, THANH_TOAN);
            mav.addObject(CART_PARAM, cart);
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(CLIENT_PARAM, client);
            mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject(PROVINCES_PARAM, tinhThanhService.getDsTinhThanh());
            mav.addObject(DEFAULT_PROVINCE_PARAM, differentAddress ? provinceCart : DEFAULT_PROVINCE);
            mav.addObject(DEFAULT_WARD_PARAM, differentAddress ? wardCart : "");
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            return mav;
        }
    }

    // Checkout
    @PostMapping(CHECKOUT_VIEW)
    public String checkout(NguoiNhan nguoiNhan, boolean differentAddress, String phuongThucThanhToan) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGIN_VIEW;
        } else {
            var cart = client.getGioHang();
            mIsMsgShow = true;
            // check cart
            if (cart.getTongSoLuong() <= 0) {
                mMsg = "Không thể thanh toán giỏ hàng trống!";
                return REDIRECT_PREFIX + CHECKOUT_VIEW;
            } else {
                var creditCard = client.getCreditCard();
                // check exists credit_card
                if (phuongThucThanhToan.equals(CARD)
                        && (!hasText(creditCard.getNameOnCard()) || !hasText(creditCard.getCardNumber())
                                || !hasText(creditCard.getExpiration()) || !hasText(creditCard.getSecurityCode()))) {
                    mMsg = "Bạn chưa có thông tin thẻ tín dụng trong tài khoản!";
                    return REDIRECT_PREFIX + PROFILE_VIEW;
                } else if (!hasText(client.getHoTen()) || !hasText(client.getSoDienThoai())
                        || !hasText(client.getDiaChi()) || !hasText(client.getXaPhuong())
                        || !hasText(client.getHuyenQuan())) {
                    mMsg = "Bạn chưa có thông tin cá nhân đầy đủ để thanh toán!";
                    return REDIRECT_PREFIX + PROFILE_VIEW;
                } else {
                    // check different address
                    if (!differentAddress) {
                        nguoiNhan.setHoTen(client.getHoTen());
                        nguoiNhan.setSoDienThoai(client.getSoDienThoai());
                        nguoiNhan.setDiaChi(client.getDiaChi());
                        nguoiNhan.setXaPhuong(client.getXaPhuong());
                        nguoiNhan.setHuyenQuan(client.getHuyenQuan());
                        nguoiNhan.setIdTinhThanh(client.getIdTinhThanh());
                    }
                    nguoiNhan = nguoiNhanService.saveNguoiNhan(nguoiNhan);
                    var order = new DonHang();
                    order.setNgayDat(new Date());
                    order.setTongGioHang(cart.getTongGioHang());
                    order.setChiPhiVanChuyen(client.getTinhThanh().getChiPhiVanChuyen());
                    order.setGiamGia(cart.getGiamGia());
                    order.setPhuongThucThanhToan(phuongThucThanhToan);
                    order.setTrangThai(DEFAULT_STATUS);
                    order.setIdKhachHang(client.getId());
                    order.setIdNguoiNhan(nguoiNhan.getId());
                    order = donHangService.saveDonHang(order);
                    var idCart = cart.getId();
                    // update cart detail
                    for (var item : cart.getDsChiTietGioHang()) {
                        var orderDetail = new ChiTietDonHang();
                        var product = item.getSanPham();
                        var idProduct = product.getId();
                        var productsCount = item.getSoLuongSanPham();
                        orderDetail.setSoLuongSanPham(productsCount);
                        orderDetail.setGiaBanSanPham(item.getGiaBanSanPham());
                        orderDetail.setTongTienSanPham(item.getTongTienSanPham());
                        orderDetail.setId(new ChiTietDonHangId(order.getId(), idProduct));
                        orderDetail = chiTietDonHangService.saveChiTietDonHang(orderDetail);
                        product.setTonKho(product.getTonKho() - productsCount);
                        product = sanPhamService.saveSanPham(product);
                        chiTietGioHangService.deleteChiTietGioHang(new ChiTietGioHangId(idCart, idProduct));
                    }
                    cart = gioHangService.saveGioHang(gioHangService.createGioHang(client));
                    mMsg = "Đơn hàng đã được đặt thành công!";
                    return REDIRECT_PREFIX + HISTORY_VIEW;
                }
            }
        }
    }

    // Load profile
    @GetMapping(PROFILE_VIEW)
    public ModelAndView profile() {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(PROFILE_TEMP);
            mav.addObject(TITLE_PARAM, THONG_TIN);
            mav.addObject(CART_PARAM, client.getGioHang());
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(CLIENT_PARAM, client);
            mav.addObject(CREDIT_CARD_PARAM, client.getCreditCard());
            mav.addObject(PROVINCES_PARAM, tinhThanhService.getDsTinhThanh());
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            return mav;
        }
    }

    // Update info
    @RequestMapping(value = PROFILE_VIEW + INFO_VIEW, method = { GET, PUT })
    public String profileInfo(KhachHang khachHang) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (authenticationUtil.getAccount() == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            khachHang.setId(client.getId());
            khachHang.setEmail(client.getEmail());
            khachHang.setVaiTro(client.getVaiTro());
            khachHangService.saveKhachHangWithoutPassword(khachHang);
            mIsMsgShow = true;
            mMsg = "Thông tin cơ bản đã được cập nhật thành công!";
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }

    // Update password
    @PostMapping(PROFILE_VIEW + PASSWORD_VIEW)
    public String profilePassword(String oldPassword, String newPassword, String rePassword) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            mIsMsgShow = true;
            // check valid password
            if (passwordEncoder.matches(oldPassword, client.getMatKhau()) && rePassword.equals(newPassword)) {
                khachHangService.updatePassword(client.getId(), newPassword);
                mMsg = "Mật khẩu đã được cập nhật thành công!";
            } else {
                mMsg = "Mật khẩu chưa chính xác!";
            }
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }

    // Update card
    @PostMapping(PROFILE_VIEW + CARD_VIEW)
    public String profileCard(CreditCard creditCard) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            creditCard.setId(client.getId());
            creditCardService.saveCreditCard(creditCard);
            mIsMsgShow = true;
            mMsg = "Thanh toán qua thẻ được cập nhật thành công!";
            return REDIRECT_PREFIX + PROFILE_VIEW;
        }
    }

    // Load history
    @GetMapping(HISTORY_VIEW)
    public ModelAndView history() {
        var client = authenticationUtil.getAccount();
        // check current account still valid
        if (client == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(HISTORY_TEMP);
            mav.addObject(TITLE_PARAM, LICH_SU);
            mav.addObject(CART_PARAM, client.getGioHang());
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(ORDERS_PARAM, client.getDsDonHang());
            mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
            return mav;
        }
    }
}
