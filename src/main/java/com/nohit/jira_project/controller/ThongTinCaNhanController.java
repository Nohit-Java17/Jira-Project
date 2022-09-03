package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;
import static java.lang.String.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(PROFILE_VIEW)
public class ThongTinCaNhanController {
    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private TinhThanhService thanhService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Fields
    private KhachHang mCurrentAccount;
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;

    // Load profile
    @GetMapping("")
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
            mav.addObject("dsTinhThanh", thanhService.getDsTinhThanh());
            mav.addObject("khachHang", mCurrentAccount);
            mav.addObject("creditCard", creditCard);
            mav.addObject("gioHang", gioHang);
            mav.addObject("tongGioHang", format("%,d", gioHang.getTongGioHang()) + " ₫");
            showMessageBox(mav);
            mIsByPass = false;
            return mav;
        }
    }

    // Update info
    @RequestMapping(value = INFO_VIEW, method = { GET, PUT })
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
    @PostMapping(PASSWORD_VIEW)
    public String profilePassword(String oldPassword, String newPassword, String rePassword) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            mIsMsgShow = true;
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
    @PostMapping(CARD_VIEW)
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
