package org.springframework.web.context;

import javax.servlet.ServletContext;

public class SpringInit extends ContextLoader {
    private static SpringInit springInit;

    private SpringInit() {

    }

    public static SpringInit getInstance() {
        if (springInit == null) {
            springInit = new SpringInit();
        }
        return springInit;
    }

    public void initSpring(ServletContext context) {
        initWebApplicationContext(context);
    }

    public void cleanSpring(ServletContext context) {
        closeWebApplicationContext(context);
        ContextCleanupListener.cleanupAttributes(context);
    }
}
