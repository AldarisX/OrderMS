package com.everygamer.spring.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.PrintWriter;
import java.util.Scanner;

@ShellComponent(value = "OrderMS操作")
public class SpringShell {
    @Autowired
    ApplicationContext applicationContext;
    private Scanner sc = new Scanner(System.in);
    @Autowired
    @Lazy
    private Terminal terminal;

    @Bean
    public PromptProvider promptProvider() {
        return new PromptProvider() {
            @Override
            public AttributedString getPrompt() {
                return new AttributedString("OrderMS=>",
                        AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
            }
        };
    }

    @ShellMethod(key = "stop", value = "停止服务器")
    public void stop() {
        PrintWriter writer = terminal.writer();
        writer.println("确定退出? Y/N");
        terminal.flush();
        if (sc.next().toUpperCase().equals("Y")) {
            ShutdownEndpoint endpoint = new ShutdownEndpoint();
            endpoint.setApplicationContext(applicationContext);
            endpoint.shutdown();
        } else {
            writer.println("取消退出");
            terminal.flush();
        }
    }

    @ShellMethod(key = "reload", value = "重载服务器")
    public void reload() {
        ReloadEndpoint endpoint = new ReloadEndpoint();
        endpoint.setApplicationContext(applicationContext);
        endpoint.reload();
    }
}