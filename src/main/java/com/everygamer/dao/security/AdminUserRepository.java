package com.everygamer.dao.security;

import com.everygamer.bean.security.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AdminUserRepository")
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    AdminUser findById(int id);

    AdminUser findByUsername(String username);

    List<AdminUser> findByUsernameLike(String userName);

    int deleteById(int id);
}
