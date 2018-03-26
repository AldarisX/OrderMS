package com.everygamer.dao.security;

import com.everygamer.bean.security.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserDao extends JpaRepository<AdminUser, Long> {

}
