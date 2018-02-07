<%@ page import="cn.misakanet.tool.JSONHelper" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    JSONArray urls = JSONArray.fromObject(JSONHelper.getJson("urlList.json"));

    String uri = request.getRequestURI();
    uri = uri.substring(uri.lastIndexOf("/") + 1);

    JSONArray subHeads = null;
%>
<div class="head">
    <ul id="head_nav" class="headNav">
        <%
            for (int i = 0; i < urls.size(); i++) {
                JSONObject urlObj = urls.getJSONObject(i);
                if (urlObj.getBoolean("showInHead")) {
                    String dpName = urlObj.getString("dpName");
                    String tarUrl = urlObj.getString("mapper");
                    //时候需要“选中”
                    String linkCss = "";
                    //如果当前页面正好是遍历到的页面
                    if (uri.equals(urlObj.getString("url"))) {
                        linkCss = "selected";
                        if (urlObj.containsKey("subHead"))
                            subHeads = urlObj.getJSONArray("subHead");
                    } else if (urlObj.containsKey("subHead")) {
                        //subHeads是子页面用的 这里只能使用其他变量
                        JSONArray tSubHeads = urlObj.getJSONArray("subHead");
                        //遍历子页面 看看当前页面是不是子页面
                        for (int j = 0; j < tSubHeads.size(); j++) {
                            JSONObject subHead = tSubHeads.getJSONObject(j);
                            //如果当前页面是子页面
                            if (uri.equals(subHead.getString("url"))) {
                                linkCss = "selected";
                                subHeads = urlObj.getJSONArray("subHead");
                            }
                        }
                    }
        %>
        <li><a class="<%=linkCss%>" href="<%=tarUrl%>"><%=dpName%>
        </a></li>
        <%
                }
            }
        %>
        <li><a onclick="logout()" href="">退出</a></li>
    </ul>
    <ul id="sub_nav" class="headNav">
        <%
            if (subHeads != null) {
                for (int i = 0; i < subHeads.size(); i++) {
                    JSONObject subHead = subHeads.getJSONObject(i);
                    String dpName = subHead.getString("dpName");
                    String tarUrl = subHead.getString("mapper");
                    String linkCss = "";
                    if (uri.equals(subHead.getString("url"))) {
                        linkCss = "selected";
                    }
        %>
        <li><a class="<%=linkCss%>" href="<%=tarUrl%>"><%=dpName%>
        </a></li>
        <%
                }
                subHeads = null;
            }
        %>
    </ul>
    <div class="clearfix"></div>
</div>