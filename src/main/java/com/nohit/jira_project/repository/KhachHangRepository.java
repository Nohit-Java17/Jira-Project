package com.nohit.jira_project.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import com.nohit.jira_project.model.*;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    public List<KhachHang> findAllByhoTen(String name);

    public KhachHang findByidKH(String id);

    public KhachHang findByemail(String email);
}
