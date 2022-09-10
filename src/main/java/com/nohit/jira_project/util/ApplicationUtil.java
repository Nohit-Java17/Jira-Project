package com.nohit.jira_project.util;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.servlet.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;

import static com.nohit.jira_project.constant.AttributeConstant.*;

@Component
public class ApplicationUtil {
    @Autowired
    private GioHangService gioHangService;

    // Get gio_hang by account or create new
    public GioHang getOrDefaultGioHang(KhachHang khachHang) {
        return khachHang == null ? new GioHang() : gioHangService.getGioHang(khachHang.getId());
    }

    // Show message
    public boolean showMessageBox(ModelAndView mav, boolean flag, String msg) {
        // check flag
        if (flag) {
            mav.addObject(FLAG_MSG_PARAM, true);
            mav.addObject(MSG_PARAM, msg);
        }
        return false;
    }
}
