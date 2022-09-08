package com.nohit.jira_project.controller;

<<<<<<< HEAD
import java.util.*;

=======
>>>>>>> origin/feCart
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

<<<<<<< HEAD
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
=======
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(value = { CHECKOUT_VIEW, "/giohang" })
public class ThanhToanController {

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    StringUtil stringUtil;

    @Autowired
    KhachHangService khachHangService;

    @Autowired
    private SanPhamService productService;
>>>>>>> origin/feCart

    @Autowired
    private ChiTietGioHangService chiTietGioHangService;

    @Autowired
<<<<<<< HEAD
    private DonHangService donHangService;

    @Autowired
    private ChiTietDonHangService chiTietDonHangService;

    @Autowired
    private TinhThanhService tinhThanhService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    // Fields
    private KhachHang mCurrentAccount;
=======
    private CreditCardService creditCardService;

    @Autowired
    private NguoiNhanService nguoiNhanService;

    @Autowired
    private TinhThanhService thanhService;

    @Autowired
    private GioHangService gioHangService;

    // Fields
    private KhachHang mCurrentAccount;
    private NguoiNhan mCurrentNguoiNhan;
    private ChiTietGioHang mChoosenOne;
    private SanPham mChoosenOneSP;
>>>>>>> origin/feCart
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;

<<<<<<< HEAD
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
=======

    // Load checkout
    @GetMapping(value = { "" })
    public ModelAndView checkout() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout
        if (!isValidAccount()) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(CHECKOUT_TEMP);
            var id = mCurrentAccount.getId();
            var creditCard = creditCardService.getCreditCard(id);
            
            var gioHang = gioHangService.getGioHang(id);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(id);
                gioHangService.saveGioHang(gioHang);
            }
            mav.addObject("dsTinhThanh", thanhService.getDsTinhThanh());
            mav.addObject("khachHang", mCurrentAccount);
            // mav.addObject("nguoinhan", mCurrentNguoiNhan);
            mav.addObject("creditCard", creditCard);
            mav.addObject("gioHang", gioHang);
            mav.addObject("some_products", productService.getDsSanPhamAscendingPriceOrder().subList(0, 2));
            mav.addObject("some_newProducts", productService.getDsSanPhamNewestOrder().subList(0, 2));
            mav.addObject("login", mCurrentAccount != null);
            showMessageBox(mav);
            mIsByPass = false;
            return mav;
        }
    }

    // Update info
    @RequestMapping(value = INFO_VIEW, method = { GET, PUT })
    public String profileInfo(@RequestParam("myCheckbox") boolean checkbox, NguoiNhan nguoiNhan) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            if(checkbox = true){
                System.out.println("Checkbox is checked");
                nguoiNhan.setHoTen(mCurrentNguoiNhan.getHoTen());
                nguoiNhan.setDiaChi(mCurrentNguoiNhan.getDiaChi());
                nguoiNhan.setXaPhuong(mCurrentNguoiNhan.getXaPhuong());
                nguoiNhan.setHuyenQuan(mCurrentNguoiNhan.getHuyenQuan());
                nguoiNhan.setTinhThanh(mCurrentNguoiNhan.getTinhThanh());
                nguoiNhan.setSoDienThoai(mCurrentNguoiNhan.getSoDienThoai());
                nguoiNhan.setGhiChu(mCurrentNguoiNhan.getGhiChu());
                nguoiNhanService.saveNguoiNhan(nguoiNhan);
                System.out.println(nguoiNhan.getHoTen());
            }
            
            mIsMsgShow = true;
            mMsg = "Thông tin cơ bản đã được cập nhật thành công!";
            mIsByPass = true;
            return REDIRECT_PREFIX + PROFILE_VIEW;
>>>>>>> origin/feCart
        }
    }

    // Check valid account
    private boolean isValidAccount() {
        // check bypass
        if (mIsByPass) {
            return true;
        } else {
            mCurrentAccount = authenticationUtil.getAccount();
<<<<<<< HEAD
=======
            // System.out.print(mCurrentAccount.getHoTen());
>>>>>>> origin/feCart
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
<<<<<<< HEAD
=======
    
>>>>>>> origin/feCart
}
