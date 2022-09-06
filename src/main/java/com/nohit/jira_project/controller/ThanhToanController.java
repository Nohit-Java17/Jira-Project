package com.nohit.jira_project.controller;

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

    @Autowired
    private ChiTietGioHangService chiTietGioHangService;

    @Autowired
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
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;


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
        }
    }

    // Check valid account
    private boolean isValidAccount() {
        // check bypass
        if (mIsByPass) {
            return true;
        } else {
            mCurrentAccount = authenticationUtil.getAccount();
            // System.out.print(mCurrentAccount.getHoTen());
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
