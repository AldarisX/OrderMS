package com.everygamer.orm.dao;

import com.everygamer.bean.User;

public interface UserDao extends BaseDao {
    int addUser(String uname, String passwd);

    int updateLoginTime(int uid);

    int setPasswd(String uname, String passwd);

    int delUser(int uid);

    User isExist(String uname);

    User getLogin(String uname, String passwd);
}
