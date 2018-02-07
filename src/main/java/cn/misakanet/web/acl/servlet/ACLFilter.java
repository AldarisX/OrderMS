package cn.misakanet.web.acl.servlet;

import cn.misakanet.site.SiteConfig;
import cn.misakanet.web.acl.ACL;
import cn.misakanet.web.acl.ACLArbiter;
import cn.misakanet.web.acl.ACLConfig;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 权限控制过滤器
 * <p>
 * doLog:是否记录拒绝信息
 * </p>
 */
public class ACLFilter implements Filter {
    private static ACLFilter aclFilter;
    private static ACLArbiter aclCtrl;
    private String token;
    private boolean doLog = true;
    private Logger logger = Logger.getLogger(ACLFilter.class);

    public static ACLFilter getInstance() {
        return aclFilter;
    }

    public void load() {
        aclCtrl = ACLArbiter.getInstance();
        token = SiteConfig.getInstance().getStringData("lweb-acl");
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (aclCtrl == null && !ACLConfig.isUseable) {
            return;
        }
        HttpServletRequest hreq = (HttpServletRequest) req;
        String token = hreq.getParameter("lweb-acl");
        if (token != null && token.equals(this.token)) {
            chain.doFilter(req, resp);
        } else {
            HttpSession session = hreq.getSession();
            String reqURI = hreq.getRequestURI();

            ACL acl = aclCtrl.doArbite(session, reqURI);
            if (acl == null || acl.getState() == ACL.State.White) {
                chain.doFilter(req, resp);
            } else {
                HttpServletResponse hresp = (HttpServletResponse) resp;
                switch (acl.getState()) {
                    case Black:
                        // 如果开启了记录
                        if (doLog)
                            logger.warn(hreq.getRemoteAddr() + "访问 " + reqURI + " 被拒绝");
                        hresp.sendError(acl.getCode());
                        break;
                    case Denied:
                        // 如果开启了记录
                        if (doLog)
                            logger.warn(hreq.getRemoteAddr() + "访问 " + reqURI + " 权限不足");
                        hresp.sendError(acl.getCode());
                        break;
                    case Redirect:
                        hresp.sendRedirect(acl.getRedirect());
                        break;
                }
            }
        }
    }

    public void init(FilterConfig config) {
        aclFilter = this;
        String doLog = config.getInitParameter("doLog");
        if (doLog != null) {
            this.doLog = Boolean.valueOf(doLog);
        }
    }

}