package com.everygamer.control.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageControl {
    @RequestMapping(path = {"/", "/index.html", "/index"})
    public String index() {
        return "index.html";
    }
}
