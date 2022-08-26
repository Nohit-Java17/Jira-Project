package com.nohit.jira_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nohit.jira_project.model.PhiVanChuyen;

@Repository
public interface PhiVanChuyenRepository extends JpaRepository<PhiVanChuyen, Integer>{

}
