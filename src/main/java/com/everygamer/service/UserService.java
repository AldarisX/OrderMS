package com.everygamer.service;

import com.everygamer.bean.security.AdminRole;
import com.everygamer.bean.security.AdminUser;
import com.everygamer.dao.security.AdminRoleRepository;
import com.everygamer.dao.security.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("UserService")
public class UserService {
    @Autowired
    @Qualifier("AdminUserRepository")
    AdminUserRepository userRepository;
    @Autowired
    AdminRoleRepository roleRepository;

    @Transactional
    public int addUser(String uname, String passwd) {
        AdminUser user = userRepository.findByUsername(uname);
        if (user == null) {
            AdminUser newUser = new AdminUser();
            newUser.setUsername(uname);
            newUser.setPassword(passwd);

            List<AdminRole> roles = new ArrayList<>();
            AdminRole role = roleRepository.findByName("ROLE_USER");
            roles.add(role);

            newUser.setRoles(roles);

            userRepository.save(newUser);
            return 1;
        } else {
            return -1;
        }
    }

    @Transactional
    public int delUser(int id) {
        AdminUser user = userRepository.findById(id);
        if (user != null) {
            return userRepository.deleteById(id);
        }
        return 0;
    }


    @Transactional
    public int delUser(String uname) {
        AdminUser user = userRepository.findByUsername(uname);
        if (user != null) {
            return userRepository.deleteById(user.getId());
        }
        return 0;
    }


    public AdminUser getUser(int id) {
        return userRepository.findById(id);
    }

    @Transactional
    public int setPasswd(int id, String passwd) {
        AdminUser user = userRepository.findById(id);
        if (user != null) {
            user.setPassword(passwd);
            userRepository.save(user);
            return 1;
        }
        return 0;
    }

    @Transactional
    public int setPasswd(String uname, String passwd) {
        AdminUser user = userRepository.findByUsername(uname);
        if (user != null) {
            user.setPassword(passwd);
            userRepository.save(user);
            return 1;
        } else {
            return 0;
        }
    }

    public List<AdminUser> searchUser(String kw) {
        kw = kw.replaceAll(" ", "%");
        kw = "%" + kw + "%";
        return userRepository.findByUsernameLike(kw);
    }
}
