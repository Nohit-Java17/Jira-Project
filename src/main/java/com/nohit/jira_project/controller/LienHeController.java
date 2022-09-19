package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.ApplicationConstant.Menu.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(CONTACT_VIEW)
public class LienHeController {
    @Autowired
    private ThuPhanHoiService thuPhanHoiService;

    @Autowired
    private SubcribeService subcribeService;

    @Autowired
    private ApplicationUtil applicationUtil;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    // Fields
    private String mMsg;
    private boolean mIsMsgShow;

    // Load contact
    @GetMapping("")
    public ModelAndView contact() {
        var mav = new ModelAndView(CONTACT_TEMP);
        var khachHang = authenticationUtil.getAccount();
        mav.addObject(TITLE_PARAM, LIEN_HE);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject(LOGIN_PARAM, khachHang != null);
        mav.addObject(CLIENT_PARAM, khachHang);
        mIsMsgShow = applicationUtil.showMessageBox(mav, mIsMsgShow, mMsg);
        return mav;
    }

    // Add thu_phan_hoi
    @PostMapping("")
    public String contact(ThuPhanHoi thuPhanHoi) {
        thuPhanHoiService.saveThuPhanHoi(thuPhanHoi);
        mIsMsgShow = true;
        mMsg = "Cảm ơn quý khách đã liên hệ với chúng tôi!";
        return REDIRECT_PREFIX + CONTACT_VIEW;
    }

    // Add thu_phan_hoi
    @PostMapping(SUB_VIEW)
    public String subcribe(Subcribe subcribe) {
        var email = subcribe.getEmail();
        mIsMsgShow = true;
        // check email is already exist
        if (subcribeService.getSubcribe(email) != null) {
            mMsg = "Email này đã được đăng ký!";
        } else {
            subcribe.setEmail(email);
            subcribeService.saveSubcribe(subcribe);
            mMsg = "Đăng ký nhận thông báo thành công!";
        }
        return REDIRECT_PREFIX + CONTACT_VIEW;
    }
}
