package com.everygamer.orm.dao;

import com.everygamer.bean.User;

import java.util.List;

public interface UserDao extends BaseDao {
    int addUser(String uname, String passwd);

    int updateLoginTime(int uid);

    int setPasswd(int id, String passwd);

    int delUser(int uid);

    User isExist(String uname);

    User getUser(int id);

    User getLogin(String uname, String passwd);

    List<User> searchUser(String kw);
}
