<%@ page import="cn.misakanet.site.SiteConfig" %>
<%@ page import="cn.misakanet.tool.ConstPageWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mapper = request.getParameter("mapper");
    String dpName = "登陆";
    if (mapper != null) {
        dpName = request.getParameter("dpName");
        String path = SiteConfig.getInstance().getWarLoc() + mapper;
        out = new ConstPageWriter(path, response.getWriter());
    }
%>
<html>
<head>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="<%=dpName%>"/>
    </jsp:include>
    <script>
        function doLogin() {
            let uname = $(":text[name=uname]").val();
            let passwd = $(":password[name=passwd]").val();
            $.post("/api/user.json?action=login", {
                uname: uname,
                passwd: passwd
            }, function (data) {
                if (data.result) {
                    location.href = data.url;
                } else {
                    alert(data.msg);
                }
            })
        }
    </script>
</head>
<body>
<div>
    <label>用户名:<input type="text" name="uname"/></label><br/>
    <label>密码:<input type="password" name="passwd"/></label>
    <button onclick="doLogin()">登陆</button>
</div>
</body>
</html>
<%
    out.close();
%>