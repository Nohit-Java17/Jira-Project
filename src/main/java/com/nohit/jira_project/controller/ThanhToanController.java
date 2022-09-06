package com.nohit.jira_project.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;
import static com.nohit.jira_project.constant.ApplicationConstant.*;

@Controller
@RequestMapping(CHECKOUT_VIEW)
public class ThanhToanController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private TinhThanhService tinhThanhService;

    @Autowired
    private NguoiNhanService nguoiNhanService;

    @Autowired
    private ChiTietDonHangService chiTietDonHangService;

    @Autowired
    private DonHangService donHangService;

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
        var mav = new ModelAndView(CHECKOUT_TEMP);
        GioHang gioHang;
        // check current account still valid
        if (!isValidAccount()) {
            gioHang = new GioHang();
        } else {
            var idKhacHang = mCurrentAccount.getId();
            gioHang = gioHangService.getGioHang(idKhacHang);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(idKhacHang);
                gioHangService.saveGioHang(gioHang);
            }
        }
        mav.addObject("client", mCurrentAccount);
        mav.addObject("cart", gioHang);
        mav.addObject("login", mCurrentAccount != null);
        mav.addObject("topPriceProducts", sanPhamService.getDsSanPhamDescendingDiscount().subList(0, 3));
        mav.addObject("topNewProducts", sanPhamService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject("topSaleProducts", sanPhamService.getDsSanPhamTopSale().subList(0, 4));
        mav.addObject("provinces", tinhThanhService.getDsTinhThanh());
        mav.addObject("province", tinhThanhService.getTinhThanh(mCurrentAccount.getIdTinhThanh()));
        mav.addObject("units", gioHang.getDsChiTietGioHang());
        mav.addObject("defaultProvince", DEFAULT_PROVINCE);
        showMessageBox(mav);
        mIsByPass = false;
        return mav;
    }

    // Checkout
    @PostMapping("")
    public String checkout(NguoiNhan nguoiNhan, boolean differentAddress) {
        GioHang gioHang;
        // check current account still valid
        if (!isValidAccount()) {
            gioHang = new GioHang();
        } else {
            var idKhacHang = mCurrentAccount.getId();
            gioHang = gioHangService.getGioHang(idKhacHang);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(idKhacHang);
                gioHangService.saveGioHang(gioHang);
            }
        }

        // DonHang donHang = new DonHang();

        // donHang.setNgayDat(new Date());
        // donHang.setTongGioHang(gioHang.getTongGioHang());
        // donHang.setChiPhiVanChuyen(
        //         tinhThanhService.getTinhThanh(mCurrentAccount.getIdTinhThanh()).getChiPhiVanChuyen());
        // donHang.setTongDonHang(gioHang.getTongGioHang() - gioHang.getGiamGia()
        //         + tinhThanhService.getTinhThanh(mCurrentAccount.getIdTinhThanh()).getChiPhiVanChuyen());
        // donHang.setPhuongThucThanhToan(phuongThucThanhToan);
        // donHang.setTrangThai("Đang giao");
        // donHang.setGiamGia(gioHang.getGiamGia());
        // donHang.setIdKhachHang(mCurrentAccount.getId());
        // donHang.setIdNguoiNhan(idnguoinhan);

        // DonHang donHang2 = donHangService.saveDonHang(donHang);
        // mMsg = "Đặt hàng thành công!";

        // List<ChiTietGioHang> listHangs = gioHang.getDsChiTietGioHang();
        // for (ChiTietGioHang chiTietGioHang : listHangs) {
        //     ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
        //     chiTietDonHang.setDonHang(donHang2);
        //     chiTietDonHang.setSanPham(chiTietGioHang.getSanPham());
        //     chiTietDonHang.setSoLuongSanPhan(chiTietGioHang.getSoLuongSanPhan());
        //     chiTietDonHang.setGiaBanSanPham(chiTietGioHang.getGiaBanSanPham());
        //     chiTietDonHang.setTongTienSanPham(chiTietGioHang.getTongTienSanPham());

        //     chiTietDonHangService.saveChiTietDonHang(chiTietDonHang);
        // }
        return REDIRECT_PREFIX + HISTORY_VIEW;
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
