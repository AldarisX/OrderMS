package com.everygamer.control.servlet;

import com.everygamer.bean.User;
import com.everygamer.service.UserService;
import com.everygamer.web.MD5Tool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user.json")
public class UserControl {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(params = "action=login")
    public String doLogin(String uname, String passwd, String vcode, HttpSession session) {
        passwd = MD5Tool.StringToMd5(passwd);
        JSONObject result = new JSONObject();

        String vCode = (String) session.getAttribute("vCode");
        if (vcode.toLowerCase().equals(vCode.toLowerCase())) {
            User u = userService.getLogin(uname, passwd);
            if (u != null) {
                result.accumulate("result", true);
                result.accumulate("url", "/index.html");
                session.setAttribute("isLogin", true);
                session.setAttribute("level", u.getLevel());
                session.setAttribute("user", u);
            } else {
                result.accumulate("result", false);
                result.accumulate("msg", "用户名或密码错误");
            }
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "验证码错误");
        }
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @ResponseBody
    @RequestMapping(params = "action=addUser")
    public String addUser(String uname, String passwd) {
        passwd = MD5Tool.StringToMd5(passwd);
        JSONObject result = new JSONObject();
        int cRows = userService.addUser(uname, passwd);
        if (cRows == 1) {
            result.accumulate("result", true);
        } else if (cRows == -1) {
            result.accumulate("result", false);
            result.accumulate("msg", "已存在用户 " + uname);
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "添加用户失败");
        }

        return result.toString();
    }
}
