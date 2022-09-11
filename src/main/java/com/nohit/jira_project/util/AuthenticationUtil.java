package com.nohit.jira_project.util;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;

import static org.springframework.security.core.context.SecurityContextHolder.*;

@Component
public class AuthenticationUtil {
    @Autowired
    private KhachHangService khachHangService;

    // Get current user account from SecurityContextHolder
    public KhachHang getAccount() {
        var authentication = getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken ? null
                : khachHangService.getKhachHang(authentication.getName());
    }
}
