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
        mav.addObject("cart", applicationUtil.getOrDefaultGioHang(khachHang));
        mav.addObject("login", khachHang != null);
        showMessageBox(mav);
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
