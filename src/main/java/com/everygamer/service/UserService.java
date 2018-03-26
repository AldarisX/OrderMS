package com.everygamer.service;

import com.everygamer.bean.security.AdminUser;
import com.everygamer.dao.security.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserService {
    @Autowired
    AdminUserRepository userRepository;

//    @Transactional
//    public int addUser(String uname, String passwd) {
//        AdminUser u = userDao.isExist(uname);
//        if (u == null) {
//            userDao.addUser(uname, passwd);
//            return 1;
//        } else {
//            return -1;
//        }
//    }

//    public int delUser(int id) {
//        return userDao.delUser(id);
//    }


//    @Transactional
//    public int delUser(String uname) {
//        AdminUser u = userDao.isExist(uname);
//        if (u != null) {
//            userDao.delUser(u.getId());
//            return 1;
//        }
//        return 0;
//    }

//
//    public AdminUser getUser(int id) {
//        return userDao.getUser(id);
//    }

//    @Transactional
//    public int setPasswd(int id, String passwd) {
//        return userDao.setPasswd(id, passwd);
//    }
//
//    @Transactional
//    public int setPasswd(String uname, String passwd) {
//        AdminUser u = userDao.isExist(uname);
//        if (u != null) {
//            userDao.setPasswd(u.getId(), passwd);
//            return 1;
//        } else {
//            return 0;
//        }
//    }

    public List<AdminUser> searchUser(String kw) {
        kw = kw.replaceAll(" ", "%");
        kw = "%" + kw + "%";
        return userRepository.findByUsernameLike(kw);
    }
}
