<%@ page import="cn.misakanet.site.SiteConfig" %>
<%@ page import="cn.misakanet.tool.ConstPageWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mapper = request.getParameter("mapper");
    String dpName = "出库";
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
        let selectedItem = [];
        let priceCount = 0;
        let itemCount = 0;
        $(document).ready(function () {
            $.get("/select_item.html", function (page) {
                $("#searchFrame").append(page);
                pageLoad();
            });
        });

        function selectItem(item, count, price) {
            item.price = price;
            item.count = count;
            selectedItem.push(item);
            priceCount += price * count;
            itemCount += count;
            $("#itemStatis").text(" 一共选择了" + itemCount + "个物品 共" + priceCount + "元");
            let exData = LwebJson.jsonToString(item.exData, nowExData, "</br>");
            $("#selectedItem table").append("<tr><td>" + item.name + "</td><td>" + item.itemType + "</td><td>" + exData + "</td><td>" + price + "</td><td>" + count + "</td><td><button onclick='removeItem(" + selectedItem.length + "," + count + "," + price + ")'>移除</button></td></tr>");
        }

        function removeItem(index, count, price) {
            $("#selectedItem table tr").get(index).remove();
            priceCount -= price;
            itemCount -= count;
            selectedItem.splice(index);
            $("#itemStatis").text(" 一共选择了" + itemCount + "个物品 共" + priceCount + "元");
        }

        function clearItem() {
            selectedItem = [];
            priceCount = 0;
            itemCount = 0;
            $("#itemStatis").text(" 一共选择了0个物品 共0元");
            $("#selectedItem table").empty();
            $("#selectedItem table").append("<tr><td>型号</td><td>类型</td><td>附加参数</td><td>出库价</td><td>数量</td><td>操作</td></tr>");
        }

        function itemOut() {
            $.post("/api/itemStore.json?action=itemOut", {
                items: JSON.stringify(selectedItem)
            }, function (data) {
                if (data.result) {

                } else {
                    alert(data.msg);
                }
            })
        }
    </script>
    <style>
        #selectedItem {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="nav.jsp"/>
<div>
    <div id="itemStatis">
        一共选择了0个物品 共0元
    </div>
    <div id="selectedItem">
        <table border="1">
            <tr>
                <td>型号</td>
                <td>类型</td>
                <td>附加参数</td>
                <td>出库价</td>
                <td>数量</td>
                <td>操作</td>
            </tr>
        </table>
    </div>
    <button onclick="clearItem()">清空选择的物品</button>
    <button onclick="itemOut()">出库</button>
</div>

<div id="searchFrame">

</div>
</body>
</html>
<%
    out.close();
%>