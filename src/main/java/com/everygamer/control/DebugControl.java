package com.everygamer.control;

import com.everygamer.logger.SystemLog;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/debug.json", produces = "application/json;charset=UTF-8")
public class DebugControl {
    @SystemLog(description = "DEBUG:退出OrderMS")
    @RequestMapping(params = "action=sysExit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void sysExit() {
        System.exit(0);
    }
}
