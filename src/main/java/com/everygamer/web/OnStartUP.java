package com.everygamer.web;

import cn.misakanet.site.SiteConfig;
import cn.misakanet.tool.MakeHTML;
import cn.misakanet.web.acl.servlet.ACLFilter;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.URLDecoder;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

//import cn.misakanet.web.ACLFilter;

@WebServlet(name = "OnStartUP")
public class OnStartUP extends HttpServlet {
    private Logger logger = Logger.getLogger(OnStartUP.class);

    public OnStartUP() {
        super();
    }

    public void init(ServletConfig config) {
        logger.info("开始获取获取项目路径");
        SiteConfig wConfig = SiteConfig.getInstance();
        try {
            String warLoc = config.getServletContext().getRealPath("/");
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase().contains("windows")) {
                osName = "Windows";
            } else if (osName.toLowerCase().contains("linux")) {
                osName = "Linux";
            }
            switch (osName) {
                case "Linux":
                    wConfig.setWarLoc(URLDecoder.decode(warLoc.replace("file:", ""), "utf-8"));
                    break;
                case "Windows":
                    wConfig.setWarLoc(URLDecoder.decode(warLoc.replace("file:/", ""), "utf-8"));
                    break;
            }
            logger.info("获取项目路径成功:" + wConfig.getWarLoc());

            UUID token = UUID.randomUUID();
            wConfig.addData("lweb-acl", token.toString());
            logger.info("加载ACL");
            ACLFilter.getInstance().load();

        } catch (UnsupportedEncodingException e) {
            logger.error("获取项目路径失败");
            e.printStackTrace();
        }

        try {
            logger.info("尝试连接数据库");

            wConfig.setInstall(true);
            logger.info("数据库连接完成");
        } catch (Exception e) {
            wConfig.setInstall(false);
            logger.warn("数据库连接失败\t进入安装");
        }

        //探测时候在debug状态
        List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
        boolean isDebug = false;
        for (String arg : args) {
            if (arg.startsWith("-Xrunjdwp") || arg.startsWith("-agentlib:jdwp")) {
                isDebug = true;
                break;
            }
        }
        wConfig.addData("serverHost", "http://127.0.0.1:8080/");
        wConfig.addData("isDebug", isDebug);

        //如过是调试模式就启动静态页面处理器
        if (isDebug)
            staticPageMaker();
    }

    private void staticPageMaker() {
        Timer makeHtmlTimer = new Timer();
        makeHtmlTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    new MakeHTML().start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 5000, 60000);
    }
}
