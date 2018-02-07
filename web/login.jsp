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
            let vcode = $(":text[name=vCode]").val();
            $.post("/api/user.json?action=login", {
                uname: uname,
                passwd: passwd,
                vcode: vcode
            }, function (data) {
                if (data.result) {
                    location.href = data.url;
                } else {
                    alert(data.msg);
                }
            });
            return false;
        }

        function reVCode() {
            $("#vCode").attr("src", "");
            $("#vCode").attr("src", "vCode.jpg?id=" + new Date());
        }
    </script>
</head>
<body>
<form onsubmit="return doLogin()">
    <label>用户名:<input type="text" name="uname"/></label><br/>
    <label>密码:<input type="password" name="passwd"/></label><br>
    <label>验证码:<input type="text" name="vCode"/></label><br/>
    <label><img id="vCode" src="/vCode.jpg" onclick="reVCode()"/></label><br/>
    <input type="submit" value="登录"/>
</form>
</body>
</html>
<%
    out.close();
%>