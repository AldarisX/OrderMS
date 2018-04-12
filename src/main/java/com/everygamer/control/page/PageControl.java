package com.everygamer.control.page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Properties;

@Controller
public class PageControl {
    @RequestMapping(path = {"/", "/index.html", "/index"})
    public String index() {
        return "/index.html";
    }

    @RequestMapping(path = "/store.html")
    public String store() {
        return "/store.html";
    }

    @RequestMapping(path = "/store_list.html")
    public String store_list() {
        return "/store_list.html";
    }

    @RequestMapping(path = "/item_out.html")
    public String item_out() {
        return "/item_out.html";
    }

    @RequestMapping(path = "/order.html")
    public String order() {
        return "/order.html";
    }

    @RequestMapping(path = "/order_list.html")
    public String order_list() {
        return "/order_list.html";
    }

    @RequestMapping(path = "/order/detail.html")
    public String orderDetail() {
        return "/order/detail.html";
    }

    @RequestMapping(path = {"/item_type.html"})
    public String item_type() {
        return "/item_type.html";
    }

    @RequestMapping(path = {"/manu_type.html"})
    public String manu_type() {
        return "/manu_type.html";
    }

    @RequestMapping(path = {"/logistics.html"})
    public String logistics() {
        return "/logistics.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = {"/edit_user.html"})
    public String edit_user() {
        return "/edit_user.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = {"/debug.html"})
    public String debug(Model model) {
        Properties prop = System.getProperties();
        model.addAttribute("osName", prop.get("os.name"));
        model.addAttribute("javaVer", prop.get("java.version"));
        model.addAttribute("jvmVendor", prop.get("java.vendor"));
        model.addAttribute("userName", prop.get("user.name"));
        model.addAttribute("userHome", prop.get("user.home"));
        model.addAttribute("userDir", prop.get("user.dir"));
        model.addAttribute("jvmName", prop.get("java.vm.name"));
        return "/debug.html";
    }
}
