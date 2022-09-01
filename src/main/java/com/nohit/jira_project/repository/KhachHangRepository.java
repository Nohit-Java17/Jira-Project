package com.nohit.jira_project.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    public List<KhachHang> findAll();

    public KhachHang findById(String id);

    public KhachHang findByEmail(String email);
}
