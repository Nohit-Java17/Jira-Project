package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nohit.jira_project.model.KhachHang;
import com.nohit.jira_project.model.NhanXet;
import com.nohit.jira_project.service.NhanXetService;

import static com.nohit.jira_project.constant.AttributeConstant.REDIRECT_PREFIX;
import static com.nohit.jira_project.constant.ViewConstant.*;

@Controller
@RequestMapping(RATE_VIEW)
public class NhanXetController {
	@Autowired
	NhanXetService nhanXetService;
	 // Fields
    private KhachHang mCurrentAccount;
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;
    
    @GetMapping("")
    public String index() {
    	return null;
    }
    
	@PostMapping("")
    public String nhanXet(NhanXet nhanXet) {
        mIsMsgShow = true;
        mIsByPass = true;
        
        nhanXetService.saveNhanXet(nhanXet);
        mMsg = "Nhận xét sản phẩm thành công!";
        return REDIRECT_PREFIX + DETAIL_VIEW + "/?id="+nhanXet.getIdSanPham();
    }
}
