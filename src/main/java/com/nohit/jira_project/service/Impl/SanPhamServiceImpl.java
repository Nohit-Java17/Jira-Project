package com.nohit.jira_project.service.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;
import com.nohit.jira_project.repository.*;
import com.nohit.jira_project.service.*;

import lombok.extern.slf4j.*;

import static com.nohit.jira_project.constant.ApplicationConstant.Category.*;
import static java.util.stream.Collectors.*;

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
        var dsSanPham = sanPhamRepository.findAll();
        dsSanPham.sort((firstProduct, secondProduct) -> {
            return secondProduct.getTonKho() < firstProduct.getTonKho() ? 1 : -1;
        });
        log.info("Fetching san_pham with top sale");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamAscendingPriceOrder() {
        var dsSanPham = sanPhamRepository.findAll();
        dsSanPham.sort((firstProduct, secondProduct) -> {
            return secondProduct.getKhuyenMai() < firstProduct.getKhuyenMai() ? 1 : -1;
        });
        log.info("Fetching san_pham with ascending price order");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamDescendingPriceOrder() {
        var dsSanPham = sanPhamRepository.findAll();
        dsSanPham.sort((firstProduct, secondProduct) -> {
            return secondProduct.getKhuyenMai() > firstProduct.getKhuyenMai() ? 1 : -1;
        });
        log.info("Fetching san_pham with descending price order");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamNewestOrder() {
        var dsSanPham = sanPhamRepository.findAll();
        dsSanPham.sort((firstProduct, secondProduct) -> {
            return secondProduct.getNgayNhap().compareTo(firstProduct.getNgayNhap());
        });
        log.info("Fetching san_pham with newest order");
        return dsSanPham;
    }

    @Override
    public List<SanPham> getDsSanPhamLaptop() {
        log.info("Fetching san_pham with category {}", LAPTOP);
        return sanPhamRepository.findAll().stream().filter(sanPham -> sanPham.getPhanLoai().equals(LAPTOP))
                .collect(toList());
    }

    @Override
    public List<SanPham> getDsSanPhamComputer() {
        log.info("Fetching san_pham with category {}", COMPUTER);
        return sanPhamRepository.findAll().stream().filter(sanPham -> sanPham.getPhanLoai().equals(COMPUTER))
                .collect(toList());
    }

    @Override
    public List<SanPham> getDsSanPhamSmartPhone() {
        log.info("Fetching san_pham with category {}", SMART_PHONE);
        return sanPhamRepository.findAll().stream().filter(sanPham -> sanPham.getPhanLoai().equals(SMART_PHONE))
                .collect(toList());
    }

    @Override
    public List<SanPham> getDsSanPhamTablet() {
        log.info("Fetching san_pham with category {}", TABLET);
        return sanPhamRepository.findAll().stream().filter(sanPham -> sanPham.getPhanLoai().equals(TABLET))
                .collect(toList());
    }

    @Override
    public List<SanPham> getDsSanPhamDevices() {
        log.info("Fetching san_pham with category {}", DEVICES);
        return sanPhamRepository.findAll().stream().filter(sanPham -> sanPham.getPhanLoai().equals(DEVICES))
                .collect(toList());
    }
}
