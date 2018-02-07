package cn.misakanet.web.acl;

public class ACLFactory {
    private static ACLConfig config = ACLConfig.getInstance();

    public static ACL getWhite(String url) {
        return new ACL().setUrl(url).setState(ACL.State.White);
    }

    public static ACL getBlack(String url, int code) {
        return new ACL().setUrl(url).setCode(code).setState(ACL.State.Black);
    }

    public static ACL getRedirect(String url, String redirect) {
        return new ACL().setUrl(url).setRedirect(redirect).setState(ACL.State.Redirect);
    }
}
