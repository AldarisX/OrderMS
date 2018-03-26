package com.everygamer.dao.security;

import com.everygamer.bean.security.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    AdminUser findByUsername(String username);

    List<AdminUser> findByUsernameLike(String userName);
}
