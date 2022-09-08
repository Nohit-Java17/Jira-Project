package com.nohit.jira_project.controller;

import java.lang.reflect.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.google.gson.*;
import com.google.gson.reflect.*;
import com.nohit.jira_project.model.*;
import com.nohit.jira_project.service.*;
import com.nohit.jira_project.util.*;

import static com.nohit.jira_project.constant.AttributeConstant.*;
import static com.nohit.jira_project.constant.TemplateConstant.*;
import static com.nohit.jira_project.constant.ViewConstant.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping(value = { CART_VIEW, "/giohang" })
public class GioHangController {

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    StringUtil stringUtil;

    @Autowired
    KhachHangService khachHangService;

    @Autowired
    private SanPhamService productService;

    @Autowired
    private ChiTietGioHangService chiTietGioHangService;

    @Autowired
    private GioHangService gioHangService;

    // Fields
    private KhachHang mCurrentAccount;
    private ChiTietGioHang mChoosenOne;
    private SanPham mChoosenOneSP;
    private String mMsg;
    private boolean mIsByPass;
    private boolean mIsMsgShow;

    @GetMapping(value = { "" })
    public ModelAndView cart() {
        // All can go to pages: homepage/product/details/about/contact
        // User must login fisrt to go to pages cart and checkout
        var mav = new ModelAndView(CART_TEMP);
        GioHang gioHang;

        // Check current account still valid
        if (!isValidAccount()) {
            return new ModelAndView(LOGIN_TEMP);
        } else {
            var id = mCurrentAccount.getId();
            gioHang = gioHangService.getGioHang(id);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(id);
                gioHangService.saveGioHang(gioHang);
            }

            if (gioHang.getTongGioHang() < 0) {
                gioHang.setTongGioHang(0);
            }

            mIsByPass = false;

            mav.addObject("khachHang", mCurrentAccount);
            mav.addObject("gioHang", gioHang);
            mav.addObject("listChiTietGioHang", gioHang.getDsChiTietGioHang());
            // mav.addObject("dsSanPham", gioHang.getDsChiTietGioHang());
            mav.addObject("login", mCurrentAccount != null);
            mav.addObject("some_products", productService.getDsSanPhamAscendingPriceOrder().subList(0, 2));
            mav.addObject("some_newProducts", productService.getDsSanPhamNewestOrder().subList(0, 2));
            mav.addObject("some_topsaleProducts", productService.getDsSanPhamTopSale().subList(0, 2));
            showMessageBox(mav);
            return mav;
        }

    }

    // Delete San Pham
    @RequestMapping(value = "/delete", method = { GET, DELETE })
    public String sanPhamDelete(int id) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {

            var idAccount = mCurrentAccount.getId();
            GioHang gioHang = gioHangService.getGioHang(idAccount);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(idAccount);
                gioHangService.saveGioHang(gioHang);
            }

            // delete
            ChiTietGioHangId selectedChiTietGioHang = new ChiTietGioHangId(mCurrentAccount.getId(), id);

            // update attribute of current gioHang before deleting
            gioHang.setTongGioHang(gioHang.getTongGioHang()
                    - chiTietGioHangService.getChiTietGioHang(selectedChiTietGioHang).getTongTienSanPham());
            gioHang.setTongSoLuong(gioHang.getTongSoLuong()
                    - chiTietGioHangService.getChiTietGioHang(selectedChiTietGioHang).getSoLuongSanPhan());
            gioHangService.saveGioHang(gioHang);
            chiTietGioHangService.deleteChiTietGioHang(selectedChiTietGioHang);

            mIsMsgShow = true;
            mMsg = "Xóa sản phẩm thành công!";
            return REDIRECT_PREFIX + CART_VIEW;
        }
    }

    // // Add new task
    // @GetMapping("/saveCart")
    // public String saveCart(List<ChiTietGioHang> listChiTietGioHang) {
    // // check current account still valid
    // if (!isValidAccount()) {
    // return REDIRECT_PREFIX + LOGOUT_VIEW;
    // } else {
    // var idAccount = mCurrentAccount.getId();
    // GioHang gioHang = gioHangService.getGioHang(idAccount);
    // // check gio_hang exist
    // if (gioHang == null) {
    // gioHang = new GioHang();
    // gioHang.setId(idAccount);
    // gioHangService.saveGioHang(gioHang);
    // }
    // // chiTietGioHangNew = (List<ChiTietGioHang>)
    // chiTietGioHangService.getDsChiTietGioHang();
    // listChiTietGioHang.forEach(chiTiet ->
    // chiTietGioHangService.saveChiTietGioHang(chiTiet));
    // gioHang.setDsChiTietGioHang(listChiTietGioHang);

    // return REDIRECT_PREFIX + CART_VIEW;
    // }
    // }

    // Add new task
    @RequestMapping(value = "/saveCart", method = RequestMethod.POST)
    public String saveCart(@RequestBody String json) {
        // check current account still valid
        if (!isValidAccount()) {
            return REDIRECT_PREFIX + LOGOUT_VIEW;
        } else {
            var idAccount = mCurrentAccount.getId();
            GioHang gioHang = gioHangService.getGioHang(idAccount);
            // check gio_hang exist
            if (gioHang == null) {
                gioHang = new GioHang();
                gioHang.setId(idAccount);
                gioHangService.saveGioHang(gioHang);
            }

            // Process data products of json
            List<ProductSimple> products;
            Type listType = new TypeToken<List<ProductSimple>>() {}.getType();
            products = new Gson().fromJson(json, listType);

            // Update every chiTietGioHang in gioHang
            List<ChiTietGioHang> chiTietGioHang = (List<ChiTietGioHang>) chiTietGioHangService.getDsChiTietGioHang();
            
            int numberProductsInCart = 0;
            int priceProductsInCart = 0;
            ChiTietGioHangId selectedChiTietGioHang;
            int flag = 0;
            for(ChiTietGioHang element : chiTietGioHang){
                selectedChiTietGioHang = new ChiTietGioHangId(gioHang.getId(), element.getSanPham().getId());
                
                element.setSoLuongSanPhan(Integer.parseInt(products.get(flag).getAmount()));
                element.setTongTienSanPham(element.getSoLuongSanPhan() * element.getGiaBanSanPham());
                chiTietGioHangService.getChiTietGioHang(selectedChiTietGioHang).setTongTienSanPham(element.getTongTienSanPham());
                chiTietGioHangService.getChiTietGioHang(selectedChiTietGioHang).setSoLuongSanPhan(element.getSoLuongSanPhan());
                chiTietGioHangService.saveChiTietGioHang(chiTietGioHangService.getChiTietGioHang(selectedChiTietGioHang));
                
                priceProductsInCart += element.getTongTienSanPham();
                numberProductsInCart += element.getSoLuongSanPhan();
                flag++;
            }

            // Update gioHang
            gioHang.setTongSoLuong(numberProductsInCart);
            gioHang.setTongGioHang(priceProductsInCart);
            gioHangService.saveGioHang(gioHang);

            mIsMsgShow = true;
            mMsg = "Cập nhật giỏ hàng thành công!";

            return REDIRECT_PREFIX + CART_VIEW;
        }
    }


    // Check valid account
    private boolean isValidAccount() {
        // check bypass
        if (mIsByPass) {
            return true;
        } else {
            mCurrentAccount = authenticationUtil.getAccount();
            // System.out.print(mCurrentAccount.getHoTen());
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
