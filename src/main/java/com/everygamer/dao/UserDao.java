package com.everygamer.dao;

import com.everygamer.bean.security.AdminUser;

import java.util.List;

public interface UserDao extends BaseDao {
    int addUser(String uname, String passwd);

    int updateLoginTime(long uid);

    int setPasswd(long id, String passwd);

    int delUser(long uid);

    AdminUser isExist(String uname);

    AdminUser getUser(long id);

    List<AdminUser> searchUser(String kw);
}
