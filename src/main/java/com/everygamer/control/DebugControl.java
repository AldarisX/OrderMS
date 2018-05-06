package com.everygamer.control;

import com.everygamer.logger.DBLogLevel;
import com.everygamer.logger.SystemLog;
import com.everygamer.logger.SystemLogOpType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "DEBUG")
@RestController
@RequestMapping(value = "/api/debug", produces = "application/json;charset=UTF-8")
public class DebugControl {
    @ApiOperation(value = "关闭管理系统", notes = "直接关闭管理系统")
    @SystemLog(description = "退出OrderMS", opType = SystemLogOpType.Before, logLevel = DBLogLevel.Warring)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/sysExit", method = RequestMethod.GET)
    public void sysExit() {
        System.exit(0);
    }
}
