package cn.misakanet.web.acl;

import java.util.HashMap;

/**
 * 代表权限控制
 */
public class ACL {
    private State state;
    private String url;
    private String redirect;
    private int code;
    private int level;
    private HashMap<String, String> param = new HashMap<>();

    public State getState() {
        return state;
    }

    public ACL setState(State state) {
        this.state = state;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ACL setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getRedirect() {
        return redirect;
    }

    public ACL setRedirect(String redirect) {
        this.redirect = redirect;
        return this;
    }

    public HashMap<String, String> getParam() {
        return param;
    }

    public void setParam(HashMap<String, String> param) {
        this.param = param;
    }

    public int getCode() {
        return code;
    }

    public ACL setCode(int code) {
        this.code = code;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public ACL setLevel(int level) {
        this.level = level;
        return this;
    }

    public String getParam(String key) {
        return param.get(key);
    }

    public ACL addParam(String key, String val) {
        param.put(key, val);
        return this;
    }

    //ACl是白还是黑
    public enum State {
        Redirect, White, Black, Denied
    }
}
