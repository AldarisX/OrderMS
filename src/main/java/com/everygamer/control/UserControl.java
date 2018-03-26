package com.everygamer.control;

import com.everygamer.service.UserService;
import com.everygamer.util.RSAUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/api/user.json", produces = "application/json;charset=UTF-8")
public class UserControl {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(params = "action=getRSA")
    public String getRSA(HttpSession session) {
        JSONObject result = new JSONObject();
        RSAUtils.generateRSA();
        session.setAttribute("priKey", RSAUtils.getPriKey());
        result.accumulate("result", true);
        result.accumulate("puk", RSAUtils.getPubKeyString());
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=logout")
    public void logout(HttpSession session) {
        session.setAttribute("isLogin", false);
        session.invalidate();
    }

//    @ResponseBody
//    @RequestMapping(params = "action=addUser")
//    public String addUser(String uname, String passwd, HttpSession session) {
//        PrivateKey priKey = (PrivateKey) session.getAttribute("priKey");
//        passwd = RSAUtils.decryptBase64(priKey, passwd);
//        passwd = MD5Tool.StringToMd5(passwd);
//        JSONObject result = new JSONObject();
//        int cRows = userService.addUser(uname, passwd);
//        if (cRows == 1) {
//            result.accumulate("result", true);
//        } else if (cRows == -1) {
//            result.accumulate("result", false);
//            result.accumulate("msg", "已存在用户 " + uname);
//        } else {
//            result.accumulate("result", false);
//            result.accumulate("msg", "添加用户失败");
//        }
//
//        return result.toString();
//    }

    @ResponseBody
    @RequestMapping(params = "action=search")
    public String search(String kw) {
        JSONObject result = new JSONObject();

        result.accumulate("data", userService.searchUser(kw));
        result.accumulate("result", true);

        return result.toString();
    }

//    @ResponseBody
//    @RequestMapping(params = "action=getUser")
//    public String getUser(int id) {
//        JSONObject result = new JSONObject();
//
//        AdminUser u = userService.getUser(id);
//        if (u != null) {
//            result.accumulate("result", true);
//            result.accumulate("data", u);
//        } else {
//            result.accumulate("result", false);
//            result.accumulate("msg", "不存在此用户");
//        }
//
//        return result.toString();
//    }
//
//    @ResponseBody
//    @RequestMapping(params = "action=setPasswd")
//    public String setPasswd(int id, String passwd, HttpSession session) {
//        PrivateKey priKey = (PrivateKey) session.getAttribute("priKey");
//        passwd = RSAUtils.decryptBase64(priKey, passwd);
//        passwd = MD5Tool.StringToMd5(passwd);
//
//        JSONObject result = new JSONObject();
//
//        userService.setPasswd(id, passwd);
//        result.accumulate("result", true);
//
//        return result.toString();
//    }
//
//    @ResponseBody
//    @RequestMapping(params = "action=delUser")
//    public String delUser(int id) {
//        JSONObject result = new JSONObject();
//
//        userService.delUser(id);
//        result.accumulate("result", true);
//
//        return result.toString();
//    }
}
