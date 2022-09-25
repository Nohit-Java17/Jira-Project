package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.common.Bean.*;
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
    private ApplicationUtil applicationUtil;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    // Load contact
    @GetMapping("")
    public ModelAndView contact() {
        var mav = new ModelAndView(CONTACT_TEMP);
        var client = authenticationUtil.getAccount();
        mav.addObject(TITLE_PARAM, LIEN_HE);
        mav.addObject(CART_PARAM, applicationUtil.getOrDefaultGioHang(client));
        mav.addObject(LOGIN_PARAM, client != null);
        mav.addObject(CLIENT_PARAM, client);
        _isMsgShow = applicationUtil.showMessageBox(mav);
        return mav;
    }

    // Add thu_phan_hoi
    @PostMapping("")
    public String contact(ThuPhanHoi thuPhanHoi) {
        thuPhanHoiService.saveThuPhanHoi(thuPhanHoi);
        _isMsgShow = true;
        _msg = "Cảm ơn quý khách đã liên hệ với chúng tôi!";
        return REDIRECT_PREFIX + CONTACT_VIEW;
    }
}
