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

    @Autowired
    private StringUtil stringUtil;

    // Fields
    private String mMsg;
    private boolean mIsMsgShow;

    // Load contact
    @GetMapping("")
    public ModelAndView contact() {
        var mav = new ModelAndView(CONTACT_TEMP);
        var khachHang = authenticationUtil.getAccount();
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
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
        var trueEmail = stringUtil.removeSpCharsBeginAndEnd(subcribe.getEmail()).toLowerCase();
        mIsMsgShow = true;
        // check email is already exist
        if (subcribeService.getSubcribe(trueEmail) != null) {
            mMsg = "Email này đã được đăng ký!";
        } else {
            subcribe.setEmail(trueEmail);
            subcribeService.saveSubcribe(subcribe);
            mMsg = "Đăng ký nhận thông báo thành công!";
        }
        return REDIRECT_PREFIX + CONTACT_VIEW;
    }
}
