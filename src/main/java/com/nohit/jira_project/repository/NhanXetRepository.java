package com.nohit.jira_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nohit.jira_project.model.NhanXet;

@Repository
public interface NhanXetRepository extends JpaRepository<NhanXet, Integer>{

}
