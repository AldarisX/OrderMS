<%@ page import="com.everygamer.bean.BaseItem" %>
<%@ page import="com.everygamer.service.StoreService" %>
<%@ page import="com.github.pagehelper.PageInfo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer iPage = Integer.parseInt(request.getParameter("page"));

    StoreService storeService = new StoreService();
    PageInfo<BaseItem> itemsPage = storeService.getItemsByName("Rx480-6G", iPage);
    List<BaseItem> items = itemsPage.getList();
    for (BaseItem item : items) {
        out.println("ID:" + item.getId() + "<br>");
        out.println("Name:" + item.getName() + "<br>");
        out.println("Type:" + item.getItemType() + "<br>");
        out.println("Count:" + item.getCount() + "<br>");
        out.println("Price:" + item.getPrice() + "<br>");
        out.println("<br>");
        out.println("<br>");
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
