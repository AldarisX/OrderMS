package cn.misakanet.web.acl;

import cn.misakanet.site.SiteConfig;
import cn.misakanet.tool.file.type.XMLHelper;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * ACL的配置类
 */
public class ACLConfig {
    public static boolean isUseable = false;
    private static ACLConfig aclConfig;
    XMLHelper xmlHelper;
    private SiteConfig config = SiteConfig.getInstance();
    /**
     * 白名单列表
     */
//    private ArrayList<String> whiteList = new ArrayList<>();
    private ArrayList<ACL> whiteList = new ArrayList<>();
    /**
     * 重定向列表
     */
    private ArrayList<ACL> redirectList = new ArrayList<>();
    /**
     * 黑名单列表
     */
//    private LinkedHashMap<String, String> blackList = new LinkedHashMap<>();
    private ArrayList<ACL> blackList = new ArrayList<>();

    /**
     * 管理员的session判定条件
     */
    private String condition;
    /**
     * 默认状态码
     */
    private int defaultCode;
    /**
     * 默认的跳转页面
     */
    private String defaultRedirectUrl;
    /**
     * 默认等级
     */
    private int defaultLevel;

    private ACLConfig() {
        try {
            loadConfigFile();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public synchronized static ACLConfig getInstance() {
        if (aclConfig == null) {
            aclConfig = new ACLConfig();
        }
        return aclConfig;
    }

    /**
     * 加载配置文件
     *
     * @throws DocumentException
     */
    public synchronized void loadConfigFile() throws DocumentException {
        //为了安全,加载时直接不允许访问
        isUseable = false;
        //加载XML
        xmlHelper = new XMLHelper(config.getWarLoc() + "/WEB-INF/acl.xml");
        //加载默认属性
        condition = xmlHelper.getElementText("condition");
        defaultCode = Integer.parseInt(xmlHelper.getElementText("defaultCode"));
        defaultRedirectUrl = xmlHelper.getElementText("defaultRedirectUrl");
        defaultLevel = Integer.parseInt(xmlHelper.getElementText("defaultLevel"));
        //清空列表
        whiteList.clear();
        blackList.clear();

        //初始化白名单
        List<Element> whiteList = xmlHelper.getElements("white-list/url");
        for (Element el : whiteList) {
            String url = el.getText();
            int level = Integer.parseInt(el.attributeValue("level", defaultLevel + ""));
            this.whiteList.add(ACLFactory.getWhite(url).setLevel(level));
        }
        //初始化重定向列表
        List<Element> redirectList = xmlHelper.getElements("redirect/url");
        for (Element el : redirectList) {
            String url = el.getText();
            System.out.println(url);
            String redirect = el.attributeValue("redirect", defaultRedirectUrl);
            int level = Integer.parseInt(el.attributeValue("level", defaultLevel + ""));
            this.redirectList.add(ACLFactory.getRedirect(url, redirect).setLevel(level));
        }
        //初始化黑名单
        List<Element> blackList = xmlHelper.getElements("black-list/url");
        for (Element el : blackList) {
            String url = el.getText();
            int code = Integer.parseInt(el.attributeValue("code", defaultCode + ""));
            int level = Integer.parseInt(el.attributeValue("level", defaultLevel + ""));
            this.blackList.add(ACLFactory.getBlack(url, code).setLevel(level));
        }

        // 加载完成后就设置为可用
        isUseable = true;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getDefaultCode() {
        return defaultCode;
    }

    public void setDefaultCode(int defaultCode) {
        this.defaultCode = defaultCode;
    }

    public String getDefaultRedirectUrl() {
        return defaultRedirectUrl;
    }

    public void setDefaultRedirectUrl(String defaultRedirectUrl) {
        this.defaultRedirectUrl = defaultRedirectUrl;
    }

    public int getDefaultLevel() {
        return defaultLevel;
    }

    public void setDefaultLevel(int defaultLevel) {
        this.defaultLevel = defaultLevel;
    }

    public ArrayList<ACL> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(ArrayList<ACL> whiteList) {
        this.whiteList = whiteList;
    }

    public ArrayList<ACL> getRedirectList() {
        return redirectList;
    }

    public void setRedirectList(ArrayList<ACL> redirectList) {
        this.redirectList = redirectList;
    }

    public ArrayList<ACL> getBlackList() {
        return blackList;
    }

    public void setBlackList(ArrayList<ACL> blackList) {
        this.blackList = blackList;
    }
}
