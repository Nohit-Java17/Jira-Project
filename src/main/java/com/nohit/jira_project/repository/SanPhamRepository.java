package com.nohit.jira_project.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    public List<SanPham> findByPhanLoai(String phanLoai);

    public SanPham findByTen(String name);
}
