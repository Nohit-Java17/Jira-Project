package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

@Service
@Slf4j
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<SanPham> getDsSanPham() {
        log.info("Fetching all san_pham");
        return sanPhamRepository.findAll();
    }

    @Override
    public SanPham getSanPham(int id) {
        log.info("Fetching san_pham with id {}", id);
        return sanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public void saveSanPham(SanPham sanPham) {
        log.info("Saving san_pham with name: {}", sanPham.getTen());
        sanPhamRepository.save(sanPham);
    }

    @Override
    public void deleteSanPham(int id) {
        log.info("Deleting san_pham with id: {}", id);
        sanPhamRepository.deleteById(id);
    }

    @Override
    public List<SanPham> getDsSanPhamTopSale() {
        // TODO Auto-generated method stub
        List<SanPham> listProductTemp = sanPhamRepository.findAll();
        listProductTemp.sort((firstProduct, secondProduct) -> {
            return firstProduct.getTonKho() < secondProduct.getTonKho() ? 1 : -1;});

        return listProductTemp;
    }

    @Override
    public List<SanPham> getDsSanPhamDescendingPriceOrder() {
        // TODO Auto-generated method stub
        List<SanPham> listProductTemp = sanPhamRepository.findAll();
        listProductTemp.sort((firstProduct, secondProduct) -> {
            return firstProduct.getKhuyenMai() < secondProduct.getKhuyenMai() ? 1 : -1;});

        return listProductTemp;
    }

    @Override
    public List<SanPham> getDsSanPhamAscendingPriceOrder() {
        // TODO Auto-generated method stub
        List<SanPham> listProductTemp = sanPhamRepository.findAll();
        listProductTemp.sort((firstProduct, secondProduct) -> {
            return firstProduct.getKhuyenMai() > secondProduct.getKhuyenMai() ? 1 : -1;});

        return listProductTemp;
    }

    @Override
    public List<SanPham> getDsSanPhamNewestOrder() {
        // TODO Auto-generated method stub
        List<SanPham> listProductTemp = sanPhamRepository.findAll();
    
        listProductTemp.sort((firstProduct, secondProduct) -> { 
            return firstProduct.getNgayNhap().compareTo(secondProduct.getNgayNhap());
        });

        return listProductTemp;
    }

    @Override
    public List<SanPham> getDsSanPhamLaptop() {
        // TODO Auto-generated method stub
        List<SanPham> listProductTemp = sanPhamRepository.findAll();
        List<SanPham> listProductFinal = new ArrayList<SanPham>();
    
        for(SanPham element : listProductTemp){
            if(element.getPhanLoai().equals("Máy tính xách tay")){
                listProductFinal.add(element);
            }
        }

        return listProductFinal;
    }

    @Override
    public List<SanPham> getDsSanPhamComputer() {
        // TODO Auto-generated method stub
        List<SanPham> listProductTemp = sanPhamRepository.findAll();
        List<SanPham> listProductFinal = new ArrayList<SanPham>();
    
        for(SanPham element : listProductTemp){
            if(element.getPhanLoai().equals("Máy tính để bàn")){
                listProductFinal.add(element);
            }
        }

        return listProductFinal;
    }

    @Override
    public List<SanPham> getDsSanPhamSmartPhone() {
        // TODO Auto-generated method stub
        List<SanPham> listProductTemp = sanPhamRepository.findAll();
        List<SanPham> listProductFinal = new ArrayList<SanPham>();
    
        for(SanPham element : listProductTemp){
            if(element.getPhanLoai().equals("Điện thoại di động")){
                listProductFinal.add(element);
            }
        }

        return listProductFinal;
    }

    @Override
    public List<SanPham> getDsSanPhamTablet() {
        // TODO Auto-generated method stub
        List<SanPham> listProductTemp = sanPhamRepository.findAll();
        List<SanPham> listProductFinal = new ArrayList<SanPham>();
    
        for(SanPham element : listProductTemp){
            if(element.getPhanLoai().equals("Máy tính bảng")){
                listProductFinal.add(element);
            }
        }

        return listProductFinal;
    }

    @Override
    public List<SanPham> getDsSanPhamDevices() {
        // TODO Auto-generated method stub
        List<SanPham> listProductTemp = sanPhamRepository.findAll();
        List<SanPham> listProductFinal = new ArrayList<SanPham>();
    
        for(SanPham element : listProductTemp){
            if(element.getPhanLoai().equals("Thiết bị ngoại vi")){
                listProductFinal.add(element);
            }
        }

        return listProductFinal;
    }
}
