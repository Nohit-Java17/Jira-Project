package com.nohit.jira_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nohit.jira_project.model.ChiTietDonHang;
import com.nohit.jira_project.model.ChiTietGioHang;
import com.nohit.jira_project.model.DonHang;
import com.nohit.jira_project.model.GioHang;
import com.nohit.jira_project.model.KhachHang;
import com.nohit.jira_project.model.NguoiNhan;
import com.nohit.jira_project.service.ChiTietDonHangService;
import com.nohit.jira_project.service.ChiTietGioHangService;
import com.nohit.jira_project.service.DonHangService;
import com.nohit.jira_project.service.GioHangService;
import com.nohit.jira_project.service.NguoiNhanService;
import com.nohit.jira_project.service.SanPhamService;
import com.nohit.jira_project.service.TinhThanhService;
import com.nohit.jira_project.util.AuthenticationUtil;

import static com.nohit.jira_project.constant.ApplicationConstant.*;
import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;

import java.util.Date;
import java.util.List;




@Controller
@RequestMapping(CHECKOUT_VIEW)
public class ThanhToanController {
	
	@Autowired
    private AuthenticationUtil authenticationUtil;
	
	@Autowired
    private SanPhamService productService;

    @Autowired
    private GioHangService gioHangService;
    
    @Autowired
    private TinhThanhService thanhService;
    
    @Autowired
    private ChiTietGioHangService chiTietGioHangService;
    
    @Autowired
    private NguoiNhanService nguoiNhanService;
    
    @Autowired
    private ChiTietDonHangService chiTietDonHangService;
    
    @Autowired
    DonHangService donHangService;

    // Fields
    private KhachHang mCurrentAccount;
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;
    
 // Load checkout
    @GetMapping("")
    public ModelAndView index() {
        var mav = new ModelAndView(CHECKOUT_TEMP);
        GioHang gioHang;
        // check current account still valid
        if (!isValidAccount()) {
            gioHang = new GioHang();
        } else {
            var id = mCurrentAccount.getId();
            gioHang = gioHangService.getGioHang(id);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(id);
                gioHangService.saveGioHang(gioHang);
            }
            //System.out.println(gioHang.getId());
        }

        mav.addObject("client", mCurrentAccount);
        mav.addObject("dsTinhThanh", thanhService.getDsTinhThanh());
        mav.addObject("cart", gioHang);
        mav.addObject("newProducts", productService.getDsSanPhamNewest());
        mav.addObject("some_products", productService.getDsSanPhamAscendingPrice().subList(0, 3));
        mav.addObject("some_newProducts", productService.getDsSanPhamNewest().subList(0, 3));
        mav.addObject("phiVanChuyen", thanhService.getTinhThanh(mCurrentAccount.getIdTinhThanh()));
        mav.addObject("login", mCurrentAccount != null);
        mav.addObject("dsHang", gioHang.getDsChiTietGioHang());
        
        showMessageBox(mav);
        mIsByPass = false;
        return mav;
    }
    
    @PostMapping("")
    public String checkout(NguoiNhan nguoiNhan, @RequestParam(value = "idNguoiNhan", required = false) Integer idNguoiNhan, @RequestParam(value = "phuongThucThanhToan") String phuongThucThanhToan) {
    	int idnguoinhan;
    	if(idNguoiNhan == 1) {
    		NguoiNhan nguoiNhan2 = nguoiNhanService.saveNguoiNhan(nguoiNhan);
    		idnguoinhan = nguoiNhan2.getId();
    	}else {
    		idnguoinhan = mCurrentAccount.getId();
    	}
    	
    	GioHang gioHang;
        // check current account still valid
        if (!isValidAccount()) {
            gioHang = new GioHang();
        } else {
            var id = mCurrentAccount.getId();
            gioHang = gioHangService.getGioHang(id);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(id);
                gioHangService.saveGioHang(gioHang);
            }
        }
        
		DonHang donHang = new DonHang();
		
		donHang.setNgayDat(new Date());
		donHang.setTongGioHang(gioHang.getTongGioHang());
		donHang.setChiPhiVanChuyen(thanhService.getTinhThanh(mCurrentAccount.getIdTinhThanh()).getChiPhiVanChuyen());
		donHang.setTongDonHang(gioHang.getTongGioHang() - gioHang.getGiamGia() + thanhService.getTinhThanh(mCurrentAccount.getIdTinhThanh()).getChiPhiVanChuyen());
		donHang.setPhuongThucThanhToan(phuongThucThanhToan);
		donHang.setTrangThai("Đang giao");
		donHang.setGiamGia(gioHang.getGiamGia());
		donHang.setIdKhachHang(mCurrentAccount.getId());
		donHang.setIdNguoiNhan(idnguoinhan); 
		
		DonHang donHang2 = donHangService.saveDonHang(donHang);
		mMsg = "Đặt hàng thành công!";
		
		List<ChiTietGioHang> listHangs = gioHang.getDsChiTietGioHang();
		for (ChiTietGioHang chiTietGioHang : listHangs) {
			ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
			chiTietDonHang.setDonHang(donHang2);
			chiTietDonHang.setSanPham(chiTietGioHang.getSanPham());
			chiTietDonHang.setSoLuongSanPhan(chiTietGioHang.getSoLuongSanPhan());
			chiTietDonHang.setGiaBanSanPham(chiTietGioHang.getGiaBanSanPham());
			chiTietDonHang.setTongTienSanPham(chiTietGioHang.getTongTienSanPham());
			
			chiTietDonHangService.saveChiTietDonHang(chiTietDonHang);
		}
		return REDIRECT_PREFIX + HISTORY_VIEW;
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
