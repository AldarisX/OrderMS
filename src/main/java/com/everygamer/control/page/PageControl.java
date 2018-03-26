package com.everygamer.control.page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageControl {
    @RequestMapping(path = {"/", "/index.html", "/index"})
    public String index() {
        return "index.html";
    }

    @RequestMapping(path = "/store.html")
    public String store() {
        return "store.html";
    }

    @RequestMapping(path = "/store_list.html")
    public String store_list() {
        return "store_list.html";
    }

    @RequestMapping(path = "/item_out.html")
    public String item_out() {
        return "item_out.html";
    }

    @RequestMapping(path = {"/item_type.html"})
    public String item_type() {
        return "item_type.html";
    }

    @RequestMapping(path = {"/manu_type.html"})
    public String manu_type() {
        return "/manu_type.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = {"/edit_user.html"})
    public String edit_user() {
        return "edit_user.html";
    }
}
