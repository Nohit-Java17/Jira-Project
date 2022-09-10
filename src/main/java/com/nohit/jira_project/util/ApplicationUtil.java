package com.nohit.jira_project.util;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;

@Component
public class ApplicationUtil {
    @Autowired
    private GioHangService gioHangService;

    // Get gio_hang by account or create new
    public GioHang getOrDefaultGioHang(KhachHang khachHang) {
        return khachHang == null ? new GioHang() : gioHangService.getGioHang(khachHang.getId());
    }
}
