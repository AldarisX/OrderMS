package com.everygamer.control.servlet;

import cn.misakanet.tool.security.RSAUtils;
import com.everygamer.bean.User;
import com.everygamer.service.UserService;
import com.everygamer.web.MD5Tool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.PrivateKey;

@Controller
@RequestMapping(value = "/user.json")
public class UserControl {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(params = "action=login")
    public String doLogin(String uname, String passwd, String vcode, HttpSession session) {
        JSONObject result = new JSONObject();

        String vCode = (String) session.getAttribute("vCode");
        if (vcode.toLowerCase().equals(vCode.toLowerCase())) {
            PrivateKey priKey = (PrivateKey) session.getAttribute("priKey");
            passwd = RSAUtils.decryptBase64(priKey, passwd);
            passwd = MD5Tool.StringToMd5(passwd);
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
        //清理session
        session.removeAttribute("vCode");
        session.removeAttribute("priKey");
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=getRSA")
    public String getRSA(HttpSession session) {
        JSONObject result = new JSONObject();
        RSAUtils.generateRSA();
        session.setAttribute("priKey", RSAUtils.priKey);
        result.accumulate("result", true);
        result.accumulate("puk", RSAUtils.getPubKey());
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
