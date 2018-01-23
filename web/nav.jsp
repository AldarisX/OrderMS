<%@ page import="cn.misakanet.tool.JSONHelper" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    JSONArray urls = JSONArray.fromObject(JSONHelper.getJson("urlList.json"));

    String uri = request.getRequestURI();
    uri = uri.substring(uri.lastIndexOf("/") + 1);
%>
<div class="head">
    <ul id="head_nav">
        <%
            for (int i = 0; i < urls.size(); i++) {
                JSONObject urlObj = urls.getJSONObject(i);
                if (urlObj.getBoolean("isHeadNav")) {
                    String tarUrl = urlObj.getString("mapper");
                    String linkCss = "";
                    if (uri.equals(urlObj.getString("url"))) {
                        linkCss = "selected";
                    }
        %>
        <li><a class="<%=linkCss%>" href="<%=tarUrl%>"><%=urlObj.getString("dpName")%>
        </a></li>
        <%
                }
            }
        %>
    </ul>
    <div class="clearfix"></div>
</div>