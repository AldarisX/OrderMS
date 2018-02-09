<%@ page import="cn.misakanet.site.SiteConfig" %>
<%@ page import="cn.misakanet.tool.ConstPageWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mapper = request.getParameter("mapper");
    String dpName = "编辑用户";
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
    <script src="/js/jsencrypt.min.js"></script>
    <script src="/js/lweb-tool-format.js"></script>
    <script>
        function addUser() {
            layer.msg('请稍后', {
                icon: 16
                , shade: 0.01
            });
            let passwd = $(":password[name=passwd]").val();
            let passwd2 = $(":password[name=passwd2]").val();
            if (passwd !== passwd2) {
                layer.msg("两次密码不一致!!!", {icon: 5});
                return;
            }
            $.post("/api/user.json?action=getRSA", function (result) {
                if (result.result) {
                    //加密
                    let encrypt = new JSEncrypt();
                    encrypt.setPublicKey(result.puk);
                    passwd = encrypt.encrypt(passwd);
                    let uname = $(":text[name=uname]").val();
                    $.post("/api/user.json?action=addUser", {
                        uname: uname,
                        passwd: passwd,
                    }, function (data) {
                        if (data.result) {
                            layer.msg('添加成功', {icon: 6});
                        } else {
                            layer.msg(data.msg, {icon: 5});
                        }
                    }).fail(function (xhr, status, error) {
                        if (xhr.status === 401) {
                            layer.msg("你没有这个权限", {icon: 5});
                        }
                    });
                } else {
                    layer.msg('密码加密失败', {icon: 5});
                }
            }).fail(function () {
                layer.msg('请求RSA公钥失败', {icon: 5});
            });
        }

        function listUser() {
            let kw = $("#search_uname").val();
            $.post("/api/user.json?action=search", {
                kw: kw
            }, function (data) {
                if (data.result) {
                    $("#userList table").empty();
                    $("#userList table").append("<tr><td>ID</td><td>用户名</td><td>等级</td><td>最后登陆时间</td><td>操作</td></tr>");
                    for (let i = 0; i < data.data.length; i++) {
                        let user = data.data[i];
                        let lastLogin = user.lastLogin;
                        if (lastLogin === 0) {
                            lastLogin = "未登录";
                        } else {
                            lastLogin = formatUnixTime(lastLogin);
                        }
                        $("#userList table").append("<tr><td>" + user.id + "</td><td>" + user.uname + "</td><td>" + user.level + "</td><td>" + lastLogin + "</td><td>操作</td></tr>");
                    }
                } else {
                    layer.msg("检索时粗错", {icon: 5});
                }
            }).fail(function (xhr, status, error) {
                if (xhr.status === 401) {
                    layer.msg("你没有这个权限", {icon: 5});
                }
            });
        }
    </script>
</head>
<body>
<jsp:include page="nav.jsp"/>
<form onsubmit="return false">
    <h4>添加用户</h4>
    <label>用户名:<input type="text" name="uname"/></label><br/>
    <label>密码:<input type="password" name="passwd"/></label><br/>
    <label>确认密码:<input type="password" name="passwd2"/></label><br/>
    <button onclick="addUser()">添加</button>
</form>
<div id="search">
    <form onsubmit="return false">
        <h4>检索用户</h4>
        <label>关键字:<input type="text" id="search_uname"/></label>
        <button onclick="listUser()">检索</button>
    </form>
    <div id="userList">
        <table border="1"></table>
    </div>
</div>
</body>
</html>
<%
    out.close();
%>