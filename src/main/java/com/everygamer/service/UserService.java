package com.everygamer.service;

import com.everygamer.bean.User;
import com.everygamer.orm.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserService {
    @Autowired
    @Qualifier("UserDao")
    private UserDao userDao;

    public int addUser(String uname, String passwd) {
        User u = userDao.isExist(uname);
        if (u == null) {
            userDao.addUser(uname, passwd);
            return 1;
        } else {
            return -1;
        }
    }

    public int delUser(String uname) {
        User u = userDao.isExist(uname);
        if (u != null) {
            userDao.delUser(u.getId());
            return 1;
        }
        return 0;
    }

    public User getLogin(String uname, String passwd) {
        User u = userDao.getLogin(uname, passwd);
        if (u != null) {
            userDao.updateLoginTime(u.getId());
        }
        return u;
    }

    public void setPasswd(String uname, String passwd) {
        userDao.setPasswd(uname, passwd);
    }

    public List<User> searchUser(String kw) {
        return userDao.searchUser(kw);
    }
}
