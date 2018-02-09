package com.everygamer.service;

import com.everygamer.bean.User;
import com.everygamer.orm.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserService")
public class UserService {
    @Autowired
    @Qualifier("UserDao")
    private UserDao userDao;

    @Transactional
    public int addUser(String uname, String passwd) {
        User u = userDao.isExist(uname);
        if (u == null) {
            userDao.addUser(uname, passwd);
            return 1;
        } else {
            return -1;
        }
    }

    public int delUser(int id) {
        return userDao.delUser(id);
    }


    @Transactional
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

    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Transactional
    public int setPasswd(int id, String passwd) {
        return userDao.setPasswd(id, passwd);
    }

    @Transactional
    public int setPasswd(String uname, String passwd) {
        User u = userDao.isExist(uname);
        if (u != null) {
            userDao.setPasswd(u.getId(), passwd);
            return 1;
        } else {
            return 0;
        }
    }

    public List<User> searchUser(String kw) {
        return userDao.searchUser(kw);
    }
}
