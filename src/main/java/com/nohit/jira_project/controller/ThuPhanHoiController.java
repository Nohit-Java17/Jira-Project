package com.nohit.jira_project.controller;

import static com.nohit.jira_project.constant.AttributeConstant.FLAG_MSG_PARAM;
import static com.nohit.jira_project.constant.AttributeConstant.MSG_PARAM;
import static com.nohit.jira_project.constant.AttributeConstant.REDIRECT_PREFIX;
import static com.nohit.jira_project.constant.ViewConstant.CONTACT_VIEW;
import static com.nohit.jira_project.constant.ViewConstant.LOGIN_VIEW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nohit.jira_project.model.KhachHang;
import com.nohit.jira_project.model.ThuPhanHoi;
import com.nohit.jira_project.service.ThuPhanHoiService;
import com.nohit.jira_project.util.AuthenticationUtil;

@Controller
@RequestMapping(CONTACT_VIEW)
public class ThuPhanHoiController {
	
	@Autowired
	ThuPhanHoiService thuPhanHoiService;
	
	@Autowired
    private AuthenticationUtil authenticationUtil;
	
	// Fields
    private KhachHang mCurrentAccount;
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;
	
	@PostMapping("")
	public String guiPhanHoi(@RequestParam("hoTen") String hoTen, @RequestParam("email") String email, @RequestParam("chuDe") String chuDe, @RequestParam("noiDung") String noiDung) {
		ThuPhanHoi thuPhanHoi = new ThuPhanHoi();
		
		thuPhanHoi.setHoTen(hoTen);
		thuPhanHoi.setEmail(email);
		thuPhanHoi.setChuDe(chuDe);
		thuPhanHoi.setNoiDung(noiDung);
		
		thuPhanHoiService.saveThuPhanHoi(thuPhanHoi);
		
		mMsg = "Cảm ơn quý khách đã liên hệ với chúng tôi!";
        return REDIRECT_PREFIX + CONTACT_VIEW;
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
