package cn.misakanet.web.acl;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * ACL控制
 */
public class ACLArbiter {
    private static ACLArbiter acl;
    private ACLConfig config;

    private ACLArbiter() {
        config = ACLConfig.getInstance();
    }

    public static ACLArbiter getInstance() {
        if (acl == null) {
            acl = new ACLArbiter();
        }
        return acl;
    }

    /**
     * 判断是否允许访问
     *
     * @param session 当前的session
     * @param url     当前的地址
     * @return 时候允许访问
     */
    public synchronized ACL doArbite(HttpSession session, String url) {
        //判断是不是在白名单内
        ArrayList<ACL> whiteList = config.getWhiteList();
        ACL whiteACL = matchACL(whiteList, url);
        if (whiteACL != null) {
            return whiteACL;
        }

        //判断是否在黑名单内
        ArrayList<ACL> blackList = config.getBlackList();
        ACL blackACL = matchACL(blackList, url);
        //如果不在黑名单
        if (blackACL == null) {
            //如果不在重定向列表里就返回null,否则就返回ACL
//            return marchRedirect(url);
            return null;
        }
        //如果在黑名单的话
        //取得初始条件
        boolean condition = session.getAttribute(config.getCondition()) != null && (boolean) session.getAttribute(config.getCondition());
        //如果满足初始条件
        if (condition) {
            //取得等级
            int level = (int) session.getAttribute("level");
            //如果权限不足
            if (level > blackACL.getLevel()) {
                //设置为权限不足
                return blackACL.setState(ACL.State.Denied);
            }
            return null;
        }
        //判断是不是在重定向名单内
        ArrayList<ACL> redirectList = config.getRedirectList();
        ACL redirectACL = matchACL(redirectList, url);
        if (redirectACL != null) {
            return redirectACL.setState(ACL.State.Redirect);
        }

        //以上条件都不满足就返回默认的错误码
        return new ACL().setState(ACL.State.Black).setCode(config.getDefaultCode());
    }

    private ACL matchACL(ArrayList<ACL> list, String url) {
        for (ACL acl : list) {
            if (Pattern.compile(acl.getUrl()).matcher(url).find()) {
                return acl;
            }
        }
        return null;
    }
}