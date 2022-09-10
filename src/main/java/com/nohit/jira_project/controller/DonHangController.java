package com.nohit.jira_project.controller;

import static com.nohit.jira_project.constant.AttributeConstant.REDIRECT_PREFIX;
import static com.nohit.jira_project.constant.TemplateConstant.ORDER_TEMP;
import static com.nohit.jira_project.constant.ViewConstant.LOGOUT_VIEW;
import static com.nohit.jira_project.constant.ViewConstant.ORDER_VIEW;
import static com.nohit.jira_project.constant.ViewConstant.HISTORY_VIEW;
import static com.nohit.jira_project.constant.ViewConstant.VIEW_VIEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nohit.jira_project.model.ChiTietDonHangId;
import com.nohit.jira_project.model.GioHang;
import com.nohit.jira_project.service.ChiTietDonHangService;
import com.nohit.jira_project.service.GioHangService;
import com.nohit.jira_project.util.AuthenticationUtil;

@Controller
@RequestMapping(ORDER_VIEW)
public class DonHangController {
	@Autowired
    private GioHangService gioHangService;

    @Autowired
    private AuthenticationUtil authenticationUtil;
    
    @Autowired
    private ChiTietDonHangService chiTietDonHangService;
    
    @GetMapping("")
    public String order() {
        return REDIRECT_PREFIX + HISTORY_VIEW;
    }
    // Load order
    @GetMapping(VIEW_VIEW)
    public ModelAndView orderFind(int id) {
        var khachHang = authenticationUtil.getAccount();
        // check current account still valid
        if (khachHang == null) {
            return new ModelAndView(REDIRECT_PREFIX + LOGOUT_VIEW);
        } else {
            var mav = new ModelAndView(ORDER_TEMP);
            // var idKH = khachHang.getId();
            // var gioHang = gioHangService.getGioHang(idKH);
            // // check gio_hang exist
            // if (gioHang == null) {
            //     gioHang = new GioHang();
            //     gioHang.setId(idKH);
            //     gioHangService.saveGioHang(gioHang);
            // }
            
            // mav.addObject("order", chiTietDonHangService.getChiTietDonHang(id));
            // mav.addObject("client", khachHang);
            // mav.addObject("cart", gioHang);
            // mav.addObject("login", khachHang != null);
            return mav;
        }
    }
}
