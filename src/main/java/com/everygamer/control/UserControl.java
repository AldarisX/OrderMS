package com.everygamer.control;

import com.everygamer.bean.RSAList;
import com.everygamer.bean.security.AdminUser;
import com.everygamer.service.RSAService;
import com.everygamer.service.UserService;
import com.everygamer.util.MD5Tool;
import com.everygamer.util.RSAUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.PrivateKey;

@Api(description = "用户接口")
@RestController
@RequestMapping(value = "/api/user", produces = "application/json;charset=UTF-8")
public class UserControl {
    @Autowired
    private UserService userService;
    @Autowired
    RSAService rsaService;

    @ApiOperation(value = "取得RSA", notes = "查询库存最大的物品")
    @ResponseBody
    @RequestMapping(value = "/rsa", method = RequestMethod.GET)
    public String getRSA(HttpSession session) {
        JSONObject result = new JSONObject();
        RSAList rsa = rsaService.getRandRSA();
        session.setAttribute("rsa", rsa.getId());
        result.accumulate("result", true);
        result.accumulate("puk", rsa.getPubKeyString());
        return result.toString();
    }

    @ApiOperation(value = "添加用户", notes = "添加用户", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uname", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "passwd", value = "密码", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUser(String uname, String passwd, HttpSession session) {
        PrivateKey priKey = rsaService.getRSAById((Long) session.getAttribute("rsa")).getPriKey();
        passwd = RSAUtils.decryptBase64(priKey, passwd);
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

    @ApiOperation(value = "搜索用户", notes = "搜索用户", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kw", value = "用户名的关键字", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(String kw) {
        JSONObject result = new JSONObject();

        result.accumulate("data", userService.searchUser(kw));
        result.accumulate("result", true);

        return result.toString();
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", paramType = "path")
    })
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable int id) {
        JSONObject result = new JSONObject();

        AdminUser u = userService.getUser(id);
        if (u != null) {
            result.accumulate("result", true);
            result.accumulate("data", u);
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "不存在此用户");
        }

        return result.toString();
    }

    @ApiOperation(value = "设置用户密码", notes = "设置用户密码", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "passwd", value = "用户的新密码", required = true, dataType = "String")
    })
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}/passwd/", method = RequestMethod.POST)
    public String setPasswd(@PathVariable int id, String passwd, HttpSession session) {
        PrivateKey priKey = rsaService.getRSAById((Long) session.getAttribute("rsa")).getPriKey();
        passwd = RSAUtils.decryptBase64(priKey, passwd);
        passwd = MD5Tool.StringToMd5(passwd);

        JSONObject result = new JSONObject();

        userService.setPasswd(id, passwd);
        result.accumulate("result", true);

        return result.toString();
    }

    @ApiOperation(value = "删除用户", notes = "删除用户", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", paramType = "path")
    })
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}/del", method = RequestMethod.GET)
    public String delUser(@PathVariable int id) {
        JSONObject result = new JSONObject();

        userService.delUser(id);
        result.accumulate("result", true);

        return result.toString();
    }
}
