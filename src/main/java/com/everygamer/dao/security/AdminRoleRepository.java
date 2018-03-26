package com.everygamer.dao.security;

import com.everygamer.bean.security.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {
    AdminRole findById(int id);

    AdminRole findByName(String name);
}
