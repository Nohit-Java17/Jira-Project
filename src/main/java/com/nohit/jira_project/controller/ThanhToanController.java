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

import static com.nohit.jira_project.common.Bean.*;
import static com.nohit.jira_project.constant.ApplicationConstant.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.ApplicationConstant.Payment.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;
import static org.springframework.util.StringUtils.*;

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

    @Autowired
    private ApplicationUtil applicationUtil;

    // Load checkout
    @GetMapping("")
    public ModelAndView checkout() {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var mav = new ModelAndView(CHECKOUT_TEMP);
            var cart = client.getGioHang();
            var provinceCart = cart.getIdTinhThanh();
            var differentAddress = provinceCart != client.getIdTinhThanh();
            mav.addObject(TITLE_PARAM, THANH_TOAN);
            mav.addObject(CART_PARAM, cart);
            mav.addObject(LOGIN_PARAM, client != null);
            mav.addObject(CLIENT_PARAM, client);
            mav.addObject(TOP_DISCOUNTS_PARAM, sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
            mav.addObject(TOP_NEWS_PARAM, sanPhamService.getDsSanPhamNewest().subList(0, 3));
            mav.addObject(PROVINCES_PARAM, tinhThanhService.getDsTinhThanh());
            mav.addObject(DEFAULT_PROVINCE_PARAM, differentAddress ? provinceCart : DEFAULT_PROVINCE);
            mav.addObject(DEFAULT_WARD_PARAM, differentAddress ? cart.getHuyenQuan() : "");
            _isMsgShow = applicationUtil.showMessageBox(mav);
            return mav;
        }
    }

    // Checkout
    @PostMapping("")
    public String checkout(NguoiNhan nguoiNhan, boolean differentAddress, String phuongThucThanhToan) {
        var client = authenticationUtil.getAccount();
        // Check current account still valid
        if (client == null) {
            return REDIRECT_PREFIX + LOGIN_VIEW;
        } else {
            var cart = applicationUtil.getOrDefaultGioHang(client);
            _isMsgShow = true;
            // check cart
            if (cart.getTongSoLuong() <= 0) {
                _msg = "Không thể thanh toán giỏ hàng trống!";
                return REDIRECT_PREFIX + CHECKOUT_VIEW;
            } else {
                var creditCard = client.getCreditCard();
                var name = client.getHoTen();
                var phone = client.getSoDienThoai();
                var address = client.getDiaChi();
                var ward = client.getXaPhuong();
                var district = client.getHuyenQuan();
                // check exists credit_card
                if (phuongThucThanhToan.equals(CARD)
                        && (!hasText(creditCard.getNameOnCard()) || !hasText(creditCard.getCardNumber())
                                || !hasText(creditCard.getExpiration()) || !hasText(creditCard.getSecurityCode()))) {
                    _msg = "Bạn chưa có thông tin thẻ tín dụng trong tài khoản!";
                    return REDIRECT_PREFIX + PROFILE_VIEW;
                } else if (!hasText(name) || !hasText(phone) || !hasText(address) || !hasText(ward)
                        || !hasText(district)) {
                    _msg = "Bạn chưa có thông tin cá nhân đầy đủ để thanh toán!";
                    return REDIRECT_PREFIX + PROFILE_VIEW;
                } else {
                    // check different address
                    if (!differentAddress) {
                        nguoiNhan.setHoTen(name);
                        nguoiNhan.setSoDienThoai(phone);
                        nguoiNhan.setDiaChi(address);
                        nguoiNhan.setXaPhuong(ward);
                        nguoiNhan.setHuyenQuan(district);
                        nguoiNhan.setIdTinhThanh(client.getIdTinhThanh());
                    }
                    nguoiNhan = nguoiNhanService.saveNguoiNhan(nguoiNhan);
                    var idReceiver = nguoiNhan.getId();
                    var order = new DonHang();
                    order.setNgayDat(new Date());
                    order.setTongGioHang(cart.getTongGioHang());
                    order.setChiPhiVanChuyen(client.getTinhThanh().getChiPhiVanChuyen());
                    order.setGiamGia(cart.getGiamGia());
                    order.setPhuongThucThanhToan(phuongThucThanhToan);
                    order.setTrangThai(DEFAULT_STATUS);
                    order.setIdKhachHang(client.getId());
                    order.setIdNguoiNhan(idReceiver);
                    order = donHangService.saveDonHang(order);
                    var id = order.getId();
                    // update cart detail
                    for (var item : cart.getDsChiTietGioHang()) {
                        var product = item.getSanPham();
                        var idProduct = product.getId();
                        var inventory = product.getTonKho();
                        var productsCount = item.getSoLuongSanPham();
                        // check valid product
                        if (product == null || inventory < productsCount) {
                            nguoiNhanService.deleteNguoiNhan(idReceiver);
                            donHangService.deleteDonHang(id);
                            _msg = "Không còn đủ sản phẩm để thanh toán!";
                            return REDIRECT_PREFIX + CARD_VIEW + DELETE_VIEW + PRODUCT_VIEW + "?id=" + idProduct;
                        }
                        var orderDetail = new ChiTietDonHang();
                        orderDetail.setSoLuongSanPham(productsCount);
                        orderDetail.setGiaBanSanPham(item.getGiaBanSanPham());
                        orderDetail.setTongTienSanPham(item.getTongTienSanPham());
                        orderDetail.setId(new ChiTietDonHangId(id, idProduct));
                        orderDetail = chiTietDonHangService.saveChiTietDonHang(orderDetail);
                        product.setTonKho(inventory - productsCount);
                        sanPhamService.updateTonKho(idProduct, product.getTonKho());
                        chiTietGioHangService.deleteChiTietGioHang(new ChiTietGioHangId(cart.getId(), idProduct));
                    }
                    cart = gioHangService.saveGioHang(gioHangService.createGioHang(client));
                    _msg = "Đơn hàng đã được đặt thành công!";
                    return REDIRECT_PREFIX + HISTORY_VIEW;
                }
            }
        }
    }
}
