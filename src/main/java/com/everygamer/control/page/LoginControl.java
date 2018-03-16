package com.everygamer.control.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginControl {
    @RequestMapping(path = "/login/getLogin")
    public String login() {
        return "/login/login.html";
    }
}
