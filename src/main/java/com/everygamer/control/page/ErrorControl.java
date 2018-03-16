package com.everygamer.control.page;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ErrorControl extends AbstractErrorController {
    private final static String errorPath = "/error";

    public ErrorControl(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }


    @RequestMapping(path = errorPath)
    public String error(Model model, HttpServletRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, true);
        Integer status = (Integer) errorAttributes.get("status");
        String path = (String) errorAttributes.get("path");
        String message = (String) errorAttributes.get("message");
        model.addAttribute("status", status);
        model.addAttribute("path", path);
        model.addAttribute("message", message);

        return "/error/error.html";
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }
}
