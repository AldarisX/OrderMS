package com.everygamer.spring.shell.system;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.Map;

@Endpoint(id = "reload", enableByDefault = false)
public class ReloadEndpoint implements ApplicationContextAware {
    private static final Map<String, String> NO_CONTEXT_MESSAGE = Collections
            .unmodifiableMap(
                    Collections.singletonMap("message", "No context to reload."));

    private static final Map<String, String> RELOAD_MESSAGE = Collections
            .unmodifiableMap(
                    Collections.singletonMap("message", "Reloading..."));

    private ConfigurableApplicationContext context;

    @WriteOperation
    public Map<String, String> reload() {
        if (this.context == null) {
            return NO_CONTEXT_MESSAGE;
        }
        try {
            return RELOAD_MESSAGE;
        } finally {
            Thread thread = new Thread(this::performReload);
            thread.setContextClassLoader(getClass().getClassLoader());
            thread.start();
        }
    }

    private void performReload() {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        this.context.stop();
        this.context.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (context instanceof ConfigurableApplicationContext) {
            this.context = (ConfigurableApplicationContext) context;
        }
    }
}
