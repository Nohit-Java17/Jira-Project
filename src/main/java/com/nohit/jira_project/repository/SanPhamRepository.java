package com.nohit.jira_project.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    public List<SanPham> findByPhanLoai(String phanLoai);

    public SanPham findByTen(String name);

    @Modifying
    @Query("UPDATE san_pham sp SET sp.tonKho = :tonKho WHERE sp.id = :id")
    public void saveTonKho(int id, int tonKho);

    @Modifying
    @Query("UPDATE san_pham sp SET sp.danhGia = :danhGia WHERE sp.id = :id")
    public void saveDanhGia(int id, int danhGia);
}
